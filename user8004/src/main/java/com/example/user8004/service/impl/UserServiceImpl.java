package com.example.user8004.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.Vo.UserVo;
import com.example.commons.entity.User;
import com.example.commons.util.BeanUtil;
import com.example.commons.util.JWTUtil;
import com.example.commons.util.RedisUtil;
import com.example.commons.util.Result;
import com.example.user8004.service.UserService;
import com.example.user8004.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author 易佳明
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-02 16:22:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 注册用户 手机号唯一
    public Result register(User user){
        User user1 = queryUser(user);
        if(!Objects.isNull(user1)){
            return Result.queryFail("用户名已存在");
        }
        User user2 = queryPhone(user.getPhone());
        if(!Objects.isNull(user2)){
            return Result.Fail("手机号已被注册");
        }
        boolean flag = save(user);
        if(!flag){
            return Result.regFail("用户注册失败");
        }
        return Result.ok("用户注册成功");
    }

    // 查询用户
    public Result login(User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        lambdaQueryWrapper.eq(User::getPassword, user.getPassword());
        User user1 = userMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(user1)){
            return Result.Fail("用户登陆失败");
        }
        UserVo userVo = BeanUtil.copy(user1, UserVo.class);
        String token = JWTUtil.token(user);
        userVo.setToken(token);
        // 将用户信息存入redis
        try {
            RedisUtil.SaveUser(stringRedisTemplate,userVo,token);
            UserVo user2 = RedisUtil.getUser(stringRedisTemplate, token);
            System.out.println("=========="+user2);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Result.ok(userVo);
    }

    @Override
    public Result updateUser(User user) {
//        User user1 = queryUser(user);
//        if(!Objects.isNull(user1)){
//            return Result.queryFail("用户名已存在");
//        }
        boolean flag = userMapper.updateUser(user);
        if(!flag){
            return Result.updateFail("找回密码失败");
        }
        return Result.ok("找回密码成功");
    }

    @Override
    public Result upload(String imgUrl,Integer userId) {
        boolean upload = userMapper.upload(imgUrl,userId);
        if(!upload){
            return Result.Fail("头像上传失败");
        }
        return Result.ok("头像上传成功");
    }

    // 查询用户信息
    @Override
    public Result userDetail(Integer userId) {
        User user = userMapper.selectById(userId);
        if(Objects.isNull(user)){
            return Result.queryFail("查询用户失败");
        }
        return Result.ok(user);
    }

    // 获取用户信息用户
    public User queryUser(User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User user1 = userMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(user1)){
            return null;
        }
        return user1;
    }


    // 通过手机号获取用户信息用户
    public User queryPhone(String phone){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone, phone);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(user)){
            return null;
        }
        return user;
    }
}




