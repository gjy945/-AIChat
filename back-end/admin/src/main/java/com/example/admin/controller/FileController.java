package com.example.admin.controller;

import com.example.bean.entity.FileUpload;
import com.example.common.response.ServerResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.security.common.util.FileUtils;
import java.io.IOException;

@RestController
@RequestMapping("/common/file")
@RequiredArgsConstructor
@Tag(name = "文件上传下载")
public class FileController {

    private final FileUtils fileUtils;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return FileUpload 对象
     * @throws IOException io异常
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public ServerResponseEntity<FileUpload> upload(@RequestParam("file") MultipartFile file) throws IOException {
        FileUpload uploadFileUpload = fileUtils.uploadFile(file);
        return ServerResponseEntity.success(uploadFileUpload);
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名
     * @param response response
     * @throws IOException io异常
     */
    @GetMapping("/download/{fileName}")
    @Operation(summary = "文件下载")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        fileUtils.download(fileName, response);
    }


    /**
     * 文件预览
     *
     * @param fileName 文件名
     * @param response response
     * @throws IOException io异常
     */
    @GetMapping("/preview/{fileName}")
    @Operation(summary = "文件预览")
    public void preview(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        fileUtils.preview(fileName, response);
    }
}
