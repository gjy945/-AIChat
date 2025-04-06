package com.example.security.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.bean.entity.FileUpload;
import com.example.common.properties.FileProperties;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUtils {

    private final FileProperties fileProperties;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public FileUpload uploadFile(MultipartFile file) {
        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件名不包括后缀
        String mainName = FileUtil.mainName(originalFilename);
        // 获取文件后缀
        String extName = FileUtil.extName(originalFilename);

        File saveDir = new File(getFilePath(""));
        if (!saveDir.exists()) {
            // 递归创建目录（包括所有不存在的父目录）
            saveDir.mkdirs();
        }

        // 生成文件
        if (FileUtil.exist(getFilePath(originalFilename))) {
            //如果文件存在，则重新生成文件名
            originalFilename = System.currentTimeMillis() + StrUtil.UNDERLINE + mainName + StrUtil.DOT + extName;
        }

        //获取文件大小(单位: 字节)
        long size = file.getSize();

        try {
            File saveFile = new File(getFilePath(originalFilename));
            log.info("保存文件至: {}", saveFile.getAbsolutePath());
            file.transferTo(saveFile);
        } catch (IOException e) {
            log.error("文件保存失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件上传失败，请检查目录权限或磁盘空间");
        }

        // 文件访问路径 http://ip:port/file/upload/xxx.jpg
        String url = "http://" +
                fileProperties.getIp() +
                StrUtil.C_COLON +
                fileProperties.getPort() +
                StrUtil.SLASH +
                fileProperties.getPath() +
                StrUtil.SLASH +
                originalFilename;

        // 返回文件信息
        return FileUpload
                .builder()
                .oldName(file.getOriginalFilename())  // 上传前文件名
                .newName(originalFilename)  // 上传后文件名
                .path(fileProperties.getPath() + StrUtil.SLASH + originalFilename)    // 文件相对路径
                .size(size)  // 文件大小(字节)
                .url(url)    // 文件访问路径
                .build();
    }


    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    public void download(String fileName, HttpServletResponse response) {
        try {
            //附件下载
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String filePath = getFilePath(fileName);
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 文件预览
     *
     * @param fileName
     * @param response
     */
    public void preview(String fileName, HttpServletResponse response) {
        try {
            //附件预览
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String filePath = getFilePath(fileName);
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    private @NotNull String getFilePath(String fileName) {
//        return FileUtil.normalize(
//                fileProperties.getSavePath() +
//                        StrUtil.SLASH + fileProperties.getPath() +
//                        StrUtil.SLASH + fileName
//        );
//    }
    private @NotNull String getFilePath(String fileName) {
        // 校验文件名合法性（防止路径越权攻击）
        if (fileName.contains("..") || fileName.contains(File.separator)) {
            throw new IllegalArgumentException("非法文件名: " + fileName);
        }

        return Paths.get(fileProperties.getSavePath(), fileProperties.getPath(), fileName)
                .toAbsolutePath()
                .normalize()
                .toString();
    }
}
