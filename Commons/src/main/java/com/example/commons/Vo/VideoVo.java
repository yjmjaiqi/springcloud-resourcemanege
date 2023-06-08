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
 * @TableName video
 */
@TableName(value ="video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 视频大小
     */
    private long videoSize;

    /**
     * 视频类型
     */
    private Double videoType;

    /**
     * 逻辑删除(0-未删除，1-删除)
     */
//    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}