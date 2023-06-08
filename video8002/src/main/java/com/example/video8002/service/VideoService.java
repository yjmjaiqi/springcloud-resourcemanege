package com.example.video8002.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.Video;
import com.example.commons.util.Result;

/**
* @author 易佳明
* @description 针对表【video】的数据库操作Service
* @createDate 2023-05-02 16:00:19
*/
public interface VideoService extends IService<Video> {
    // 上传视频
    Result addVideo(Video video, Integer userId);

    // 删除视频
    Result deleteVideo(Integer videoId,Integer userId);

    // 移动视频
    Result moveVideo(String videoTypeName,Integer videoId);

    // 视频数量
    Result videoCount(String videoTypeName,Integer userId);
    Result noTypeVideoCount(Integer userId);

    // 恢复视频
    Result recoverRecycle_bin(Integer userId,Integer videoId);

    // 删除回收站视频
    Result delRecycle_bin(Integer videoId);

    // 查询单个视频详情
    Result selectOneVideo(Integer videoId);

    // 查询视频
    Result selectVideoList(Integer userId);

    // 根据视频分类查询视频
    Result selectVideoListWithType(String videoTypeName,Integer userId);

    // 添加视频分类
    Result addVideoType(String videoTypeName,Integer userId);

    // 删除视频分类
    Result delVideoType(String videoTypeName,Integer userId);

    // 查询视频所有分类
    Result selectVideoType(Integer userId);

    // 分类重命名
    Result renameVideo(String oldName,String newName,Integer userId);

    // 清空文件
    Result removeDir(Integer userId);

    // 清空文件夹
    Result removeTypeDir(String videoTypeName,Integer userId);

    // 回收站视频渲染
    Result queryRecycle_bin(Integer userId);

}
