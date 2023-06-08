package com.example.video7002.controller;

import com.example.commons.entity.Image;
import com.example.commons.entity.Video;
import com.example.commons.util.Result;
import com.example.video7002.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@Api("视频接口文档")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // 无类型上传视频
    // video.getVideoType() = "0"表示无类型上传
    @PostMapping(value = "/addVideo/{userId}/{videoType}")
    public Result addVideo(@RequestPart("file") MultipartFile file, @PathVariable("userId") Integer userId,
                           @PathVariable("videoType") String videoType){
        if (file.isEmpty()) {
            return Result.Fail("视频上传失败");
        }
        System.out.println("=========file"+file);
        // 实例视频Video对象
        Video video = new Video();
        //可以自己加一点校验 例如上传的是不是图片或者上传的文件是不是png格式等等 这里省略
        //获取原来的文件名和后缀
        String originalFilename = file.getOriginalFilename();
        // 文件后缀
        String ext = "."+ originalFilename.split("\\.")[1];
        //生成一个新的文件名（以防有重复的名字存在导致被覆盖）
        String newName = System.currentTimeMillis() + ext;
        String pre = "D:\\桌面\\资料\\2023-下\\springcloud\\resource_manage\\synthesize1\\video7002\\src\\main\\resources\\static\\videos\\";
        String path = pre + newName;
        try {
            file.transferTo(new File(path));
            String videoUrl = "http://localhost:7002/videos/" + newName;
            video.setVideoSize(file.getSize());
            video.setVideoUrl(videoUrl);
            if(videoType.equals("0")){
                video.setVideoType(" ");
            }else{
                video.setVideoType(videoType);
            }
            System.out.println("video======"+video);
            videoService.addVideo(video,userId);
            return Result.ok("视频上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoService.addVideo(video,userId);
    }
    // 删除视频
    @GetMapping("/deleteVideo/{videoId}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频ID", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result deleteVideo(@PathVariable("videoId") Integer videoId,@PathVariable("userId") Integer userId){
        return videoService.deleteVideo(videoId,userId);
    }

    // 移动视频
    @GetMapping("/moveVideo/{videoTypeName}/{videoId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoTypeName", value = "视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result moveVideo(@PathVariable("videoTypeName") String videoTypeName,@PathVariable("videoId") Integer videoId){
        return videoService.moveVideo(videoTypeName,videoId);
    }

    // 视频数量
    @GetMapping("/videoCount/{videoTypeName}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoTypeName", value = "视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result videoCount(@PathVariable("videoTypeName") String videoTypeName,@PathVariable("userId") Integer userId){
        if(videoTypeName.equals("0")){
            return videoService.videoCount(" ",userId);
        }
        return videoService.videoCount(videoTypeName,userId);
    }
    @GetMapping("/noTypeVideoCount/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result noTypeVideoCount(@PathVariable("userId") Integer userId){
        return videoService.noTypeVideoCount(userId);
    }

    // 单个视频详情
    @GetMapping("/videoDetail/{videoId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频ID", required = true)
    })
    public Result videoDetail(@PathVariable("videoId") Integer videoId){
        return videoService.videoDetail(videoId);
    }

    // 无分类查询视频渲染
    @GetMapping("/selectVideoList/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result selectVideoList(@PathVariable("userId") Integer userId){
        return videoService.selectVideoList(userId);
    }

    // 分类查询视频渲染
    @GetMapping("/selectVideoListWithType/{videoType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoTypeName", value = "视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result selectVideoListWithType(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId){
        return videoService.selectVideoListWithType(videoType,userId);
    }

    // 添加视频分类
    @GetMapping("/addVideoType/{videoType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoTypeName", value = "视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result addVideoType(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId){
        return videoService.addVideoType(videoType,userId);
    }

    // 删除视频分类
    @GetMapping("/delVideoType/{videoType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoTypeName", value = "视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result delVideoType(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId){
        return videoService.delVideoType(videoType,userId);
    }

    // 查询所有视频分类
    @GetMapping("/selectVideoType/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result selectVideoType(@PathVariable("userId") Integer userId){
        return videoService.selectVideoType(userId);
    }

    // 重命名视频分类
    @GetMapping("/renameVideo/{oldName}/{newName}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldName", value = "旧视频类型名", required = true),
            @ApiImplicitParam(name = "newName", value = "新视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result renameVideo(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                              @PathVariable("userId") Integer userId){
        return videoService.renameVideo(oldName, newName,userId);
    }

    // 清空文件
    @GetMapping("/removeDir/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result removeDir(@PathVariable("userId") Integer userId) {
        return videoService.removeDir(userId);
    }

    // 清空文件夹
    @GetMapping("/removeTypeDir/{videoType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoTypeName", value = "视频类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result removeTypeDir(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId) {
        return videoService.removeTypeDir(videoType,userId);
    }

    // 从回收站恢复视频
    @GetMapping("/recoverRecycle_bin/{userId}/{videoId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "videoId", value = "视频ID", required = true)
    })
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("videoId") Integer videoId) {
        return videoService.recoverRecycle_bin(userId,videoId);
    }

    // 从回收站删除视频
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId", value = "视频ID", required = true)
    })
    @GetMapping("/delRecycle_bin/{videoId}")
    public Result delRecycle_bin(@PathVariable("videoId") Integer videoId) {
        return videoService.delRecycle_bin(videoId);
    }

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
    })
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId) {
        return videoService.queryRecycle_bin(userId);
    }
}
