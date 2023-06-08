package com.example.commons.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName note
 */
@TableName(value ="note")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteVo implements Serializable {
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
//    private Integer isDel;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 用户id
     */
//    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}