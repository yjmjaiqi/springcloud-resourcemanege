package com.example.image8001.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserImageMapper {
    // 上传图片
    @Insert("insert into u_image(userId,imageId) values(#{userId},#{imageId})")
    boolean u_image(@Param("userId") Integer userId,@Param("imageId") Integer imageId);

    // 删除图片
    @Delete("delete from u_image where imageId = #{imageId}")
    boolean deleteU_Image(@Param("imageId") Integer imageId);


    // 恢复图片
    @Insert("insert into u_image(userId,imageId) values(#{userId},#{imageId})")
    boolean recoverImg(@Param("userId") Integer userId,@Param("imageId") Integer imageId);

}


