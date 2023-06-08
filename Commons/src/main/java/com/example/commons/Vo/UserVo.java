package com.example.commons.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName user
 */
@TableName(value ="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
//    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * token
     */
    private String token;

    /**
     * 手机号
     */
//    private String phone;

    /**
     * 邮箱
     */
//    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}