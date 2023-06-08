package com.example.commons.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName note
 */
@TableName(value ="note")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="笔记对象模型")
public class Note implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记标题
     */
    private String noteTitle;

    /**
     * 笔记内容
     */
    private String content;

    /**
     * 笔记类型
     */
    private String noteType;

    /**
     * 逻辑删除(0-未删除，1-删除)
     */
    private Integer isDel;

    /**
     * 发表时间
     */
    private Date createTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}