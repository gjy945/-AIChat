package com.example.common.properties;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@ConfigurationProperties(prefix = "files")
@Data
public class FileProperties {

    // ip
    private String ip;

    // 端口
    private String port;

    // 路径 例如: localhost:8080/files/xxx.jpg
    private String path;

    // 服务器图片路径 /结尾 例如: /home/xxx/或者 D:/xxx/
    private String savePath;

    public String getUrl(String fileName) {
        validateConfig();
        try {
            String baseUri = buildBaseUri();
            URI fullUri = new URI(baseUri)
                    .resolve("/" + path.trim() + "/")
                    .resolve(fileName);
            return normalizeUri(fullUri);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("URL构建失败: " + e.getMessage());
        }
    }

    private String buildBaseUri() {
        String protocol = isHttps() ? "https" : "http";
        return protocol + "://" + ip.trim()
                + (isDefaultPort() ? "" : ":" + port.trim());
    }

    private boolean isDefaultPort() {
        return (isHttps() && "443".equals(port)) ||
                (!isHttps() && "80".equals(port));
    }

    private boolean isHttps() {
        return ip.trim().startsWith("https");
    }

    private String normalizeUri(URI uri) {
        return uri.normalize().toString()
                .replaceAll("/+", "/"); // 处理多余斜杠
    }

    private void validateConfig() {
        if (StrUtil.hasBlank(ip, port, path)) {
            throw new IllegalStateException("文件服务配置不完整");
        }
    }

}
