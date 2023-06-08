package com.example.video8002.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Video;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author 易佳明
* @description 针对表【video】的数据库操作Mapper
* @createDate 2023-05-02 16:00:19
* @Entity com.example.video8002.entity.Video
*/
@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    // 添加视频
    @Insert("insert into video(videoUrl,videoSize,videoType,isDel,createTime) values(#{videoUrl},#{videoSize},#{videoType},0,CURRENT_TIMESTAMP)")
    boolean addVideo(Video video);

    // 删除视频
    @Update("update video set isDel = 1 where id = #{id}")
    boolean deleteVideo(@Param("id") Integer id);
    // 添加到回收站
    @Insert("insert into recycle_bin(videoId,userId) values(#{id},#{userId})")
    boolean addRecycle_bin(@Param("id") Integer id,@Param("userId") Integer userId);

    // 移动视频
    @Update("update video set videoType = #{videoTypeName}  where id = #{videoId}" )
    boolean moveVideo(@Param("videoTypeName") String videoTypeName,@Param("videoId") Integer videoId);

    // 恢复视频
    @Update("update video set isDel = 0 where id = #{videoId}")
    boolean recoverVideo(@Param("videoId") Integer videoId);
    // 从回收站删除视频
    @Delete("delete from recycle_bin where videoId = #{videoId}")
    boolean recoverRecycle_bin(@Param("videoId") Integer videoId);

    // 逻辑删除分类中所属视频
    @Update("update video set isDel = 1 where videoType = #{videoTypeName}")
    boolean deleteTypeVideo(@Param("videoTypeName") String videoTypeName);

    // 查询视频
    @Select("select videoId from u_video where userId = #{userId} order by videoId desc")
    List<Integer> videoId(@Param("userId") Integer userId);

    // 重命名视频分类
    @Update("update video set videoType = #{newName} where videoType = #{oldName}")
    boolean renameVideo(@Param("oldName") String oldName,@Param("newName") String newName);

    // 清空文件
    @Update("update video set isDel = 1 where id = #{videoId}")
    boolean removeDir(@Param("videoId") Integer videoId);
    @Delete("delete from u_video where videoId = #{videoId}")
    boolean delAllVideo(@Param("videoId") Integer videoId);
    @Select("select videoId from u_video where userId = #{userId}")
    List<Integer> videoIds(@Param("userId") Integer userId);

    // 清空文件夹
    @Delete("delete from u_video where videoId = #{videoId}")
    boolean removeTypeDir(@Param("videoId") Integer videoId);
    @Update("update video set isDel = 1 where videoType = #{videoType}")
    boolean delTypeDirVideo(@Param("videoType") String videoType);
    @Select("select id from video where videoType = #{videoType}")
    List<Integer> videosType(@Param("videoType") String videoType);

    // 回收站视频渲染
    @Select("select videoId from recycle_bin where videoId != 0 and userId = #{userId}")
    List<Integer> queryRecycle_bin(@Param("userId") Integer userId);



}




