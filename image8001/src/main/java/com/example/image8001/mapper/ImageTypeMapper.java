package com.example.image8001.mapper;

import com.example.commons.entity.ImageType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImageTypeMapper {
    // 添加图片分类
    @Insert("insert into img_type(imgTypeName,userId) values(#{imgTypeName},#{userId})")
    boolean addImageType(@Param("imgTypeName") String imgTypeName,@Param("userId") Integer userId);

    // 删除分类
    @Delete("delete from img_type where imgTypeName = #{imgTypeName} and userId = #{userId}")
    boolean deleteImgType(@Param("imgTypeName") String imgTypeName,@Param("userId") Integer userId);

    // 查询图片所有分类
    @Select("select * from img_type where userId = #{userId}")
    List<ImageType> selectImgType(@Param("userId") Integer userId);

    // 重命名图片分类
    @Update("update img_type set imgTypeName = #{newName} where imgTypeName = #{oldName} and userId = #{userId}")
    boolean renameImg(@Param("oldName") String oldName,@Param("newName") String newName,@Param("userId") Integer userId);
}
