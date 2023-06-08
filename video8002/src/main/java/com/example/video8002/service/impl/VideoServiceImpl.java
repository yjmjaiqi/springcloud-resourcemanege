package com.example.video8002.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.Vo.VideoVo;
import com.example.commons.entity.Video;
import com.example.commons.entity.VideoType;
import com.example.commons.util.BeanUtil;
import com.example.commons.util.Result;
import com.example.video8002.mapper.UserVideoMapper;
import com.example.video8002.mapper.VideoTypeMapper;
import com.example.video8002.service.VideoService;
import com.example.video8002.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author 易佳明
* @description 针对表【video】的数据库操作Service实现
* @createDate 2023-05-02 16:00:19
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoTypeMapper videoTypeMapper;
    @Autowired
    private UserVideoMapper userVideoMapper;

    @Override
    @Transactional
    public Result addVideo(Video video, Integer userId) {
        boolean flag = videoMapper.addVideo(video);
        // 分类不为空
        if(flag && !video.getVideoType().isEmpty()){
            Video video1 = queryVideo(video.getVideoUrl());
            if(!Objects.isNull(video1)){
                // 插入数据到u_video
                userVideoMapper.addVideo(userId,video1.getId());
            }
            return Result.ok("视频上传成功成功");
        }else if(flag) {
            Video video1 = queryVideo(video.getVideoUrl());
            if(!Objects.isNull(video1)){
                userVideoMapper.addVideo(userId,video1.getId());
            }
            return Result.ok("视频上传成功成功");
        }


        return Result.Fail("视频上传成功失败");
    }

    @Override
    @Transactional
    public Result deleteVideo(Integer videoId,Integer userId) {
        boolean flag = videoMapper.deleteVideo(videoId);
        // 视频删除成功
        if(flag){
            userVideoMapper.deleteU_Video(videoId);
            videoMapper.addRecycle_bin(videoId,userId);
            return Result.ok("视频删除成功");
        }
        return Result.Fail("视频删除失败");
    }

    @Override
    public Result moveVideo(String videoTypeName,Integer videoId) {
        boolean b = videoMapper.moveVideo(videoTypeName,videoId);
        if(b){
            return Result.ok("移除成功");
        }
        return Result.Fail("移除失败");
    }

    @Override
    public Result videoCount(String videoTypeName,Integer userId) {
        List<Integer> list = videoMapper.videoId(userId);
        List<Video> count = new ArrayList<>();
        if(Objects.isNull(list)){
            return Result.ok("暂无视频");
        }
        for(Integer videoId : list){
            LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Video::getVideoType,videoTypeName);
            lambdaQueryWrapper.eq(Video::getIsDel,0);
            lambdaQueryWrapper.eq(Video::getId,videoId);
            Video video = videoMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(video)){
                continue;
            }
            count.add(video);
        }
        return Result.ok(count.size());
    }

    @Override
    public Result noTypeVideoCount(Integer userId) {
        List<Integer> list = videoMapper.videoId(userId);
        List<Video> count = new ArrayList<>();
        if(Objects.isNull(list)){
            return Result.ok("暂无视频");
        }
        for(Integer videoId : list){
            LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Video::getIsDel,0);
            lambdaQueryWrapper.eq(Video::getId,videoId);
            Video video = videoMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(video)){
                continue;
            }
            count.add(video);
        }
        return Result.ok(count.size());
    }

    @Override
    public Result recoverRecycle_bin(Integer userId,Integer videoId) {
        boolean b = videoMapper.recoverRecycle_bin(videoId);
        if(b){
            boolean b1 = videoMapper.recoverVideo(videoId);
            if(b1){
                userVideoMapper.recoverVideo(userId, videoId);
                return Result.ok("恢复成功");
            }
        }
        return Result.Fail("恢复失败");
    }

    @Override
    public Result delRecycle_bin(Integer videoId) {
        boolean b = videoMapper.recoverRecycle_bin(videoId);
        if(b){
            return Result.ok("删除成功");
        }
        return Result.Fail("删除失败");
    }

    @Override
    public Result selectOneVideo(Integer videoId) {
        Video video = videoMapper.selectById(videoId);
        if(Objects.isNull(video)){
            return Result.queryFail("查询失败");
        }
        VideoVo videoVo = BeanUtil.copy(video, VideoVo.class);
        return Result.ok(videoVo);
    }

    @Override
    public Result selectVideoList(Integer userId) {
        List<Video> videos = new ArrayList<>();
        List<Integer> videoIds = videoMapper.videoId(userId);
        if(Objects.isNull(videoIds)){
            return Result.queryFail("暂无数据");
        }
        for(Integer id : videoIds){
            LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Video::getId,id);
            lambdaQueryWrapper.eq(Video::getIsDel,0);
            Video video = videoMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(video)){
                continue;
            }
            videos.add(video);
        }
        List<VideoVo> videoVos = BeanUtil.copyList(videos, VideoVo.class);
        return Result.ok(videoVos);
    }

    @Override
    public Result selectVideoListWithType(String videoTypeName,Integer userId) {
        List<Video> videos = new ArrayList<>();
        List<Integer> videoIds = videoMapper.videoId(userId);
        if(Objects.isNull(videoIds)){
            return Result.queryFail("暂无数据");
        }
        for(Integer id : videoIds){
            LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Video::getVideoType,videoTypeName);
            lambdaQueryWrapper.eq(Video::getId,id);
            lambdaQueryWrapper.eq(Video::getIsDel,0);
            Video video = videoMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(video)){
                continue;
            }
            videos.add(video);
        }
        List<VideoVo> videoVos = BeanUtil.copyList(videos, VideoVo.class);
        return Result.ok(videoVos);
    }

    @Override
    public Result addVideoType(String videoTypeName,Integer userId) {
        boolean flag = videoTypeMapper.addVideoType(videoTypeName,userId);
        if(!flag){
            return Result.Fail("分类创建失败");
        }
        return Result.ok("分类创建成功");
    }

    @Override
    public Result delVideoType(String videoType,Integer userId) {
        boolean flag = videoTypeMapper.deleteVideoType(videoType,userId);
        if(flag){
            List<Video> videos = queryVideoList(videoType);
            if(!Objects.isNull(videos)){
                for(Video video : videos){
                    userVideoMapper.deleteU_Video(video.getId());
                }
                videoMapper.deleteTypeVideo(videoType);
            }
            return Result.ok("删除成功");
        }
        return Result.Fail("删除失败");
    }

    @Override
    public Result selectVideoType(Integer userId) {
        List<VideoType> videoTypes = videoTypeMapper.selectVideoType(userId);
        if(!Objects.isNull(videoTypes)){
            return Result.ok(videoTypes);
        }
        return Result.queryFail("查询失败");
    }

    @Override
    @Transactional
    public Result renameVideo(String oldName, String newName,Integer userId) {
        boolean b = videoTypeMapper.renameVideo(oldName, newName,userId);
        if(b){
            videoMapper.renameVideo(oldName, newName);
            return Result.ok("重命名成功");
        }
        return Result.Fail("重命名失败");
    }

    @Override
    public Result removeDir(Integer userId) {
        List<Integer> list = videoMapper.videoIds(userId);
        boolean flag = false;
        for(Integer videoId : list){
            boolean b2 = videoMapper.addRecycle_bin(videoId,userId);
            if(b2){
                videoMapper.removeDir(videoId);
                videoMapper.delAllVideo(videoId);
                flag = true;
            }else{
                flag = false;
            }
        }
        if(flag){
            return Result.ok("已清空");
        }
        return Result.Fail("清空失败");
    }

    @Override
    @Transactional
    public Result removeTypeDir(String videoType,Integer userId) {
        List<Integer> list = videoMapper.videosType(videoType);
        if(Objects.isNull(list)){
            return Result.Fail("清空失败");
        }
        for(Integer videoId : list){
            boolean b = videoMapper.removeTypeDir(videoId);
            if(b){
                boolean b1 = videoMapper.delTypeDirVideo(videoType);
                if(b1){
                    videoMapper.addRecycle_bin(videoId,userId);
                }
            }
        }
        return Result.ok("清空成功");
    }

    @Override
    public Result queryRecycle_bin(Integer userId) {
        List<Integer> videoIds = videoMapper.queryRecycle_bin(userId);
        List<VideoVo> videoVos = new ArrayList<>();
        if(Objects.isNull(videoIds)){
            return Result.queryFail("暂无数据");
        }
        for(Integer videoId : videoIds){
            LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Video::getId,videoId);
            Video video = videoMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(video)){
                continue;
            }
            VideoVo copy = BeanUtil.copy(video, VideoVo.class);
            videoVos.add(copy);
        }
        return Result.ok(videoVos);
    }

    // 查询单个视频
    public Video queryVideo(String videoUrl){
        LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Video::getVideoUrl,videoUrl);
        Video video = videoMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(video)){
            return null;
        }
        return video;
    }

    // 查询图片集合
    public List<Video> queryVideoList(String videoType){
        LambdaQueryWrapper<Video> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Video::getVideoType,videoType);
        List<Video> videos = videoMapper.selectList(lambdaQueryWrapper);
        if(Objects.isNull(videos)){
            return null;
        }
        return videos;
    }

}




