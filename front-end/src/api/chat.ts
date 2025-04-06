import axios from 'axios'
// 不再需要导入fetchEventSource，使用浏览器原生的EventSource API

// 配置axios实例 - 用于非流式请求
const api = axios.create({
  baseURL: '/admin', // 使用代理前缀，通过Vite代理转发请求
  timeout: 30000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 这里可以添加认证token等
    // const token = localStorage.getItem('token')
    // if (token) {
    //   config.headers.Authorization = `Bearer ${token}`
    // }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    // 处理错误响应
    if (error.response) {
      // 服务器返回错误状态码
      console.error('API错误:', error.response.status, error.response.data)
    } else if (error.request) {
      // 请求发送但没有收到响应
      console.error('网络错误: 无法连接到服务器')
    } else {
      // 请求设置时发生错误
      console.error('请求错误:', error.message)
    }
    return Promise.reject(error)
  }
)

// 聊天相关API
export const chatApi = {
  // 发送消息 - 普通方式
  sendMessage(message: string) {
    return api.get('/chatAI', { params: { prompt: message } })
  },
  
  // 发送消息 - 流式响应方式（使用fetch API和ReadableStream）
  async sendMessageStream(message: string, onChunk: (chunk: string) => void, onComplete: () => void) {
    try {
      // 构建带有查询参数的URL
      const url = `/admin/chatAI?prompt=${encodeURIComponent(message)}`
      
      // 使用fetch API发送请求
      const response = await fetch(url)
      
      if (!response.ok) {
        console.error('请求失败:', response.status)
        onComplete()
        return null
      }
      
      // 获取响应的reader
      const reader = response.body?.getReader()
      if (!reader) {
        console.error('无法获取响应流的reader')
        onComplete()
        return null
      }
      
      // 创建TextDecoder用于解码数据
      const decoder = new TextDecoder()
      let accumulatedResponse = ''
      
      // 读取数据流
      const processStream = async () => {
        try {
          while (true) {
            const { done, value } = await reader.read()
            
            if (done) {
              console.log('流已结束')
              onComplete()
              break
            }
            
            // 解码数据块
            const chunk = decoder.decode(value, { stream: true })
            console.log('收到数据块:', chunk) // 添加日志以便调试
            
            // 检查是否为结束标记
            if (chunk.includes('[DONE]')) {
              console.log('检测到结束标记')
              onComplete()
              break
            }
            
            // 累积响应并发送数据块给回调函数
            accumulatedResponse += chunk
            // 确保回调函数被调用，并且数据块不为空
            if (chunk.trim()) {
              console.log('发送数据块到UI')
              onChunk(chunk)
            }
          }
        } catch (error) {
          console.error('读取流时发生错误:', error)
          onComplete()
        }
      }
      
      // 开始处理流
      processStream()
      
      // 返回一个取消函数，以便调用者可以在需要时手动中断
      return {
        cancel: () => {
          reader.cancel().catch(err => console.error('取消流时发生错误:', err))
          onComplete()
        }
      }
    } catch (error) {
      console.error('流式请求错误:', error)
      onComplete()
      return null
    }
  }
}