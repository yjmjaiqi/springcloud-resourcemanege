package com.example.video8002.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserVideoMapper {
    // 添加视频
    @Insert("insert into u_video(userId,videoId) values(#{userId},#{videoId})")
    boolean addVideo(@Param("userId") Integer userId,@Param("videoId") Integer videoId);

    // 删除视频
    @Delete("delete from u_video where videoId = #{videoId}")
    boolean deleteU_Video(@Param("videoId") Integer videoId);


    // 恢复视频
    @Insert("insert into u_video(userId,videoId) values(#{userId},#{videoId})")
    boolean recoverVideo(@Param("userId") Integer userId,@Param("videoId") Integer videoId);
}
