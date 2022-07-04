package com.alex.blog.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 *description:  基础类
 *author:       alex
 *createDate:   2020/11/7 9:38
 *version:      1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "基础类", description = "基础类")
@Data
@AllArgsConstructor
public class BaseEntity<T extends Model<T>> extends Model<T> {

     @ApiModelProperty(value = "id", name="id")
     @TableId(type = IdType.ASSIGN_ID)
     private String id;

     @ApiModelProperty(value = "creator", name = "创建人")
     @TableField(value = "creator", fill = FieldFill.INSERT)
     private String creator;

     @ApiModelProperty(value = "createTime", name = "创建时间")
     @TableField(value = "create_time", fill = FieldFill.INSERT, keepGlobalFormat=true)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;

     @ApiModelProperty(value = "updater", name = "修改人")
     @TableField(value = "updater", fill = FieldFill.UPDATE)
     private String updater;

     @ApiModelProperty(value = "updateTime", name = "更新时间")
     @TableField(value = "update_time", fill = FieldFill.UPDATE)
     private LocalDateTime updateTime;

     @ApiModelProperty(value = "deleter", name = "删除人")
     @TableField(value = "deleter", fill = FieldFill.UPDATE)
     private String deleter;

     @ApiModelProperty(value = "deleteTime", name = "删除时间")
     @TableField(value = "delete_time", fill = FieldFill.UPDATE)
     private LocalDateTime deleteTime;

     @TableLogic
     @ApiModelProperty(value = "is_delete", name = "是否删除(0:否1：是)")
     @TableField(value = "is_delete", fill = FieldFill.INSERT, keepGlobalFormat = true, condition = "0")
     private Integer isDelete;

     @ApiModelProperty(value = "operator", name = "操作人")
     @TableField(value = "operator", fill = FieldFill.INSERT_UPDATE)
     private String operator;

     @ApiModelProperty(value = "operateTime", name = "操作时间")
     @TableField(value = "operate_time", fill = FieldFill.INSERT_UPDATE, updateStrategy = FieldStrategy.DEFAULT)
     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private LocalDateTime operateTime;

     @ApiModelProperty(value = "status", name="状态", example = "0：失效  1：生效")
     @TableField(value = "status", fill = FieldFill.INSERT)
     private Integer status;

     public BaseEntity() {
     }
}
