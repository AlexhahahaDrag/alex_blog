package com.alex.blog.base.entity;

import com.alex.blog.base.enums.EStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     @TableId(type = IdType.AUTO)
     private Long id;

     @ApiModelProperty(value = "creator", name = "创建人")
     @TableField(value = "creator", fill = FieldFill.INSERT)
     private Long creator;

     @ApiModelProperty(value = "create_time", name = "创建时间")
     @TableField(value = "create_time", fill = FieldFill.INSERT, keepGlobalFormat=true)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;

     @ApiModelProperty(value = "updater", name = "修改人")
     @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
     private Long updater;

     @ApiModelProperty(value = "update_time", name = "更新时间")
     @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
     private LocalDateTime updateTime;

     @ApiModelProperty(value = "deleter", name = "删除人")
     @TableField(value = "deleter")
     private Long deleter;

     @ApiModelProperty(value = "delete_time", name = "删除时间")
     @TableField(value = "delete_time")
     private LocalDateTime deleteTime;

     @TableLogic
     @ApiModelProperty(value = "is_delete", name = "是否删除(0:否1：是)")
     // TODO: 2021/10/14 添加自动查询 
//     @TableField(value = "is_delete", fill = FieldFill.INSERT, keepGlobalFormat=true, whereStrategy = new FieldStrategy())
     @TableField(value = "is_delete", fill = FieldFill.INSERT, keepGlobalFormat=true)
     private Integer isDelete;

     @ApiModelProperty(value = "status", name="状态", example = "0：失效  1：生效")
     @TableField(value = "status", fill = FieldFill.INSERT)
     private Integer status;

     public BaseEntity() {
          LocalDateTime time = LocalDateTime.now();
          this.status = EStatus.ENABLE.getCode();
          this.createTime = time;
          this.updateTime = time;
     }
}
