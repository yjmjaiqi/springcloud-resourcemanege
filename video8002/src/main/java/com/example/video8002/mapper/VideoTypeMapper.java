package com.example.video8002.mapper;

import com.example.commons.entity.VideoType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoTypeMapper {
    // 添加视频分类
    @Insert("insert into video_type(videoTypeName,userId) values(#{videoTypeName},#{userId})")
    boolean addVideoType(@Param("videoTypeName") String videoTypeName,@Param("userId") Integer userId);

    // 删除分类
    @Delete("delete from video_type where videoTypeName = #{videoTypeName} and userId = #{userId}")
    boolean deleteVideoType(@Param("videoTypeName") String videoTypeName,@Param("userId") Integer userId);

    // 查询视频所有分类
    @Select("select * from video_type where userId = #{userId}")
    List<VideoType> selectVideoType(Integer userId);

    // 重命名视频分类
    @Update("update video_type set videoTypeName = #{newName} where videoTypeName = #{oldName} and userId = #{userId}")
    boolean renameVideo(@Param("oldName") String oldName,@Param("newName") String newName,@Param("userId") Integer userId);
}
