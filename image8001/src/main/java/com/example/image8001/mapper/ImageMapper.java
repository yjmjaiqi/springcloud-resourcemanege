package com.example.image8001.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.Image;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author 易佳明
* @description 针对表【image】的数据库操作Mapper
* @createDate 2023-05-02 15:57:32
* @Entity com.example.image8001.entity.Image
*/
@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    // 上传图片
    @Insert("insert into image(imgUrl,imgSize,imgType,isDel,createTime) values(#{imgUrl},#{imgSize},#{imgType},0,CURRENT_TIMESTAMP)")
    boolean addImg(Image image);

    // 逻辑删除图片
    @Update("update image set isDel = 1 where id = #{imageId}")
    boolean deleteImg(@Param("imageId") Integer imageId);

    // 移动图片
    @Update("update image set imgType = #{imgTypeName}  where id = #{imageId}" )
    boolean delImageType(@Param("imgTypeName") String imgTypeName,@Param("imageId") Integer imageId);

    // 恢复图片
    @Update("update image set isDel = 0 where id = #{imageId}")
    boolean recoverImg(@Param("imageId") Integer imageId);
    // 从回收站删除图片
    @Delete("delete from recycle_bin where imageId = #{imageId}")
    boolean recoverRecycle_bin(@Param("imageId") Integer imageId);

    // 逻辑删除分类中所属图片
    @Update("update image set isDel = 1 where imgType = #{imgTypeName}")
    boolean deleteImage(@Param("imgTypeName") String imgTypeName);

    // 查询图片
    @Select("select imageId from u_image where userId = #{userId} order by imageId desc")
    List<Integer> imagIds(@Param("userId") Integer userId);

    // 重命名图片分类
    @Update("update image set imgType = #{newName} where imgType = #{oldName}")
    boolean renameImg(@Param("oldName") String oldName,@Param("newName") String newName);

    // 清空文件
    @Update("update image set isDel = 1 where id = #{imageId}")
    boolean removeDir(@Param("imageId") Integer imageId);
    @Delete("delete from u_image where imageId = #{imageId}")
    boolean delAllImg(@Param("imageId") Integer imageId);
    @Select("select imageId from u_image where userId = #{userId}")
    List<Integer> imgIds(@Param("userId") Integer userId);

    @Insert("insert into recycle_bin(imageId,userId) values(#{imageId},#{userId})")
    boolean AddRecycle_bin(@Param("imageId") Integer imageId,@Param("userId") Integer userId);

    // 清空文件夹
    @Delete("delete from u_image where imageId = #{imageId}")
    boolean removeTypeDir(@Param("imageId") Integer imageId);
    @Update("update image set isDel = 1 where imgType = #{imgType}")
    boolean delDirImg(@Param("imgType") String imgType);
    @Select("select id from image where imgType = #{imgType}")
    List<Integer> imgIdsType(@Param("imgType") String imgType);

    // 回收站图片渲染
    @Select("select imageId from recycle_bin where imageId != 0 and userId = #{userId}")
    List<Integer> queryRecycle_bin(@Param("userId") Integer userId);
}




