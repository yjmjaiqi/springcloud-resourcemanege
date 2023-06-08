package com.example.user8004.controller;

import com.example.user8004.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.commons.util.Result;
import com.example.commons.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Api("用户接口测试")
public class userController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value="用户注册", notes="")
    public Result addUser(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    @ApiOperation(value="用户登录", notes="")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value="修改密码", notes="")
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @PostMapping("/upload/{userId}")
    public Result upload(@RequestParam("file") MultipartFile file, @PathVariable("userId") Integer userId){
        if (file.isEmpty()) {
            return Result.Fail("头像上传失败");
        }
        //可以自己加一点校验 例如上传的是不是图片或者上传的文件是不是png格式等等 这里省略
        //获取原来的文件名和后缀
        String originalFilename = file.getOriginalFilename();
        // 文件后缀
        String ext = "."+ originalFilename.split("\\.")[1];
        //生成一个新的文件名（以防有重复的名字存在导致被覆盖）
        String newName = System.currentTimeMillis() + ext;
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\images\\";
        String path = pre + newName;
        try {
            file.transferTo(new File(path));
            String avatarName = "http://localhost:8004/avatar/"+newName;
            userService.upload(avatarName,userId);
            return Result.ok("头像上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.Fail("头像上传失败");
    }

    @GetMapping("/userDetail/{userId}")
    @ApiOperation(value="查询用户信息", notes="")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    public Result userDetail(@PathVariable("userId") Integer userId){
        return userService.userDetail(userId);
    }
}
