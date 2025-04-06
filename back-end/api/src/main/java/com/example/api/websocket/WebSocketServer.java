package com.example.api.websocket;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.example.bean.entity.WebSocketMessage;
import com.example.common.exception.BaseException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//@Component
//访问服务端的url地址
@ServerEndpoint(value = "/ws/{id}")
@Tag(name = "websocket服务端")
public class WebSocketServer {
    private static int onlineCount = 0;
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static final Logger log = LogManager.getLogger(WebSocketServer.class);
    private String id = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        this.session = session;
        this.id = id;//接收到发送消息的人员编号
        webSocketSet.put(id, this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("用户{}加入！当前在线人数为{}", id, getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为{}", getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws BaseException {
        log.info("来自客户端的消息:{}", message);
        //解析json字符串为对象
        try {
            WebSocketMessage webSocketMessage = JSON.parseObject(message, WebSocketMessage.class);
            //判断消息聊天类型()
            switch (webSocketMessage.getChatType()) {
                //广播
                case BROADCAST_SEND:
                    broadcastSend(webSocketMessage);
                    break;
                //单聊
                case SINGLE_SEND:
                    singleSend(webSocketMessage);
                    break;
                //群发
                case GROUP_SEND:
                    groupSend(webSocketMessage);
                    break;
                default:
                    throw new BaseException("消息类型错误");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 群发消息
     *
     * @param webSocketMessage
     * @throws IOException
     */
    private void groupSend(WebSocketMessage webSocketMessage) throws IOException {
        for (String id : webSocketMessage.getReceiverId()) {
            //循环发送消息(批量)
            //如果文件为空,则循环发送消息(单条)
            if (webSocketMessage.getFilePath().isEmpty()) {
                webSocketMessage.getContent().forEach(content -> {
                    //设置消息内容 每次只发送一条消息
                    WebSocketMessage socketMessage = new WebSocketMessage();
                    BeanUtil.copyProperties(webSocketMessage, socketMessage);
                    socketMessage.setContent(List.of(content));
                    String jsonString = JSON.toJSONString(socketMessage);
                    try {
                        sendToUser(jsonString, id);
                    } catch (IOException e) {
                        throw new BaseException("消息发送失败");
                    }
                });
            } else {
                webSocketMessage.getFilePath().forEach(filePath -> {
                    //设置消息内容 每次只发送一条消息
                    WebSocketMessage socketMessage = new WebSocketMessage();
                    BeanUtil.copyProperties(webSocketMessage, socketMessage);
                    socketMessage.setFilePath(List.of(filePath));

                    //设置消息文件元数据
                    webSocketMessage.getMetadata().forEach(metadata -> {
                        if (Objects.equals(metadata.getUrl(), filePath)) {
                            socketMessage.setMetadata(List.of(metadata));
                        }
                    });

                    String jsonString = JSON.toJSONString(socketMessage);
                    try {
                        sendToUser(jsonString, id);
                    } catch (IOException e) {
                        throw new BaseException("消息发送失败");
                    }
                });
            }
        }
        log.info("群发消息成功");
    }


    /**
     * 单聊消息
     *
     * @param webSocketMessage
     * @throws IOException
     */
    private void singleSend(WebSocketMessage webSocketMessage) throws IOException {
        //循环发送消息(批量)
        //如果文件为空,则循环发送消息(单条)
        if (webSocketMessage.getFilePath().isEmpty()) {
            webSocketMessage.getContent().forEach(content -> {
                //设置消息内容 每次只发送一条消息
                webSocketMessage.setContent(List.of(content));
                String jsonString = JSON.toJSONString(webSocketMessage);
                try {
                    sendToUser(jsonString, webSocketMessage.getReceiverId().get(0));
                } catch (IOException e) {
                    throw new BaseException("消息发送失败");
                }
            });
        } else {
            webSocketMessage.getFilePath().forEach(filePath -> {
                //设置消息内容 每次只发送一条消息
                WebSocketMessage socketMessage = new WebSocketMessage();
                BeanUtil.copyProperties(webSocketMessage, socketMessage);
                socketMessage.setFilePath(List.of(filePath));

                //设置消息文件元数据
                webSocketMessage.getMetadata().forEach(metadata -> {
                    if (Objects.equals(metadata.getUrl(), filePath)) {
                        socketMessage.setMetadata(List.of(metadata));
                    }
                });

                String jsonString = JSON.toJSONString(socketMessage);
                try {
                    sendToUser(jsonString, webSocketMessage.getReceiverId().get(0));
                } catch (IOException e) {
                    throw new BaseException("消息发送失败");
                }
            });
        }
        log.info("消息单发发送成功");
    }


    /**
     * 广播消息
     *
     * @param webSocketMessage
     * @throws BaseException
     */
    private void broadcastSend(WebSocketMessage webSocketMessage) throws BaseException {
        //循环发送消息(批量)
        if (webSocketMessage.getFilePath().isEmpty()) {
            webSocketMessage.getContent().forEach(content -> {
                //设置消息内容 每次只发送一条消息
                WebSocketMessage socketMessage = new WebSocketMessage();
                BeanUtil.copyProperties(webSocketMessage, socketMessage);
                socketMessage.setContent(List.of(content));
                String jsonString = JSON.toJSONString(socketMessage);
                sendToAllClient(jsonString);
            });
        } else {
            webSocketMessage.getFilePath().forEach(filePath -> {
                //设置消息内容 每次只发送一条消息
                WebSocketMessage socketMessage = new WebSocketMessage();
                BeanUtil.copyProperties(webSocketMessage, socketMessage);
                socketMessage.setFilePath(List.of(filePath));

                //设置消息文件元数据
                webSocketMessage.getMetadata().forEach(metadata -> {
                    if (Objects.equals(metadata.getUrl(), filePath)) {
                        socketMessage.setMetadata(List.of(metadata));
                    }
                });

                String jsonString = JSON.toJSONString(socketMessage);
                sendToAllClient(jsonString);
            });
        }
        log.info("广播消息成功");
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     *
     * @param message
     * @param sendUserId
     * @throws IOException
     */
    public void sendToUser(String message, String sendUserId) throws IOException {
        if (webSocketSet.get(sendUserId) != null) {
            if (!id.equals(sendUserId))  //判断发送消息的用户是否是发消息者自己
                webSocketSet.get(sendUserId).sendMessage("用户" + id + "发来消息：" + " <br/> " + message);
            else
                webSocketSet.get(sendUserId).sendMessage(message);
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendToUser("当前用户不在线", id);
        }
    }

    /**
     * 发送信息给所有人
     *
     * @param message
     * @throws IOException
     */
    public void sendToAllClient(String message) throws BaseException {
        for (String key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                throw new BaseException("消息发送失败");
            }
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
 