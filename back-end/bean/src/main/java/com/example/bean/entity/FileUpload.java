package com.example.bean.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "文件上传实体类")
public class FileUpload {
    @Schema(name = "上传前文件名")
    private String oldName;
    @Schema(name = "上传后文件名")
    private String newName;
    @Schema(name = "文件相对路径")
    private String path;
    @Schema(name = "文件大小(字节)")
    private Long size;
    @Schema(name = "文件访问路径")
    private String url;
}
