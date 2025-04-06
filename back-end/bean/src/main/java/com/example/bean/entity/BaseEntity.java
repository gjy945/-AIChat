package com.example.bean.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="BaseEntity对象", description="公共字段, 其他实体类继承按需继承")
public class BaseEntity {

    // 创建时间
    @Schema(name = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

//    // 创建人
//    @Schema(name = "创建人")
//    @TableField(fill = FieldFill.INSERT)
//    private Long createUser;

    // 修改时间
    @Schema(name = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    // 修改人
//    @Schema(name = "修改人")
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private Long updateUser;
}
