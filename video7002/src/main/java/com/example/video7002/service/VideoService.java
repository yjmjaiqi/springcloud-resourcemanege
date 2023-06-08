package com.example.video7002.service;

import com.example.commons.entity.Video;
import com.example.commons.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
@FeignClient(name = "video-provider")
public interface VideoService {
    // 无类型上传视频
    // video.getVideoType() = "0"表示无类型上传
    @PostMapping(value = "/addVideo/{userId}")
    public Result addVideo(@RequestBody Video video, @PathVariable("userId") Integer userId);
    // 删除视频
    @GetMapping("/deleteVideo/{videoId}/{userId}")
    public Result deleteVideo(@PathVariable("videoId") Integer videoId,@PathVariable("userId") Integer userId);

    // 移动视频
    @GetMapping("/moveVideo/{videoTypeName}/{videoId}")
    public Result moveVideo(@PathVariable("videoTypeName") String videoTypeName,@PathVariable("videoId") Integer videoId);

    // 视频数量
    @GetMapping("/videoCount/{videoTypeName}/{userId}")
    public Result videoCount(@PathVariable("videoTypeName") String videoTypeName,@PathVariable("userId") Integer userId);
    @GetMapping("/noTypeVideoCount/{userId}")
    public Result noTypeVideoCount(@PathVariable("userId") Integer userId);

    // 单个视频详情
    @GetMapping("/videoDetail/{videoId}")
    public Result videoDetail(@PathVariable("videoId") Integer videoId);

    // 无分类查询视频渲染
    @GetMapping("/selectVideoList/{userId}")
    public Result selectVideoList(@PathVariable("userId") Integer userId);

    // 分类查询视频渲染
    @GetMapping("/selectVideoListWithType/{videoType}/{userId}")
    public Result selectVideoListWithType(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId);

    // 添加视频分类
    @GetMapping("/addVideoType/{videoType}/{userId}")
    public Result addVideoType(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId);

    // 删除视频分类
    @GetMapping("/delVideoType/{videoType}/{userId}")
    public Result delVideoType(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId);

    // 查询所有视频分类
    @GetMapping("/selectVideoType/{userId}")
    public Result selectVideoType(@PathVariable("userId") Integer userId);

    // 重命名视频分类
    @GetMapping("/renameVideo/{oldName}/{newName}/{userId}")
    public Result renameVideo(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                              @PathVariable("userId") Integer userId);

    // 清空文件
    @GetMapping("/removeDir/{userId}")
    public Result removeDir(@PathVariable("userId") Integer userId);

    // 清空文件夹
    @GetMapping("/removeTypeDir/{videoType}/{userId}")
    public Result removeTypeDir(@PathVariable("videoType") String videoType,@PathVariable("userId") Integer userId);

    // 从回收站恢复视频
    @GetMapping("/recoverRecycle_bin/{userId}/{videoId}")
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("videoId") Integer videoId);

    // 从回收站删除视频
    @GetMapping("/delRecycle_bin/{videoId}")
    public Result delRecycle_bin(@PathVariable("videoId") Integer videoId);

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId);
}
