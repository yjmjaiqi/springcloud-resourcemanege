package com.example.user8004.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.User;
import com.example.commons.util.Result;

import java.util.Objects;

/**
* @author 易佳明
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-02 16:22:40
*/
public interface UserService extends IService<User> {

    // 添加用户
    public Result register(User user);

    // 查询用户
    public Result login(User user);

    // 修改账户
    public Result updateUser(User user);

    // 上传头像
    Result upload(String imgUrl,Integer userId);

    // 查询用户信息
    Result userDetail(Integer userId);
}

