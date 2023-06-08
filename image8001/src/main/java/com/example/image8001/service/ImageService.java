package com.example.image8001.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.Image;
import com.example.commons.util.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 易佳明
* @description 针对表【image】的数据库操作Service
* @createDate 2023-05-02 15:57:32
*/
public interface ImageService extends IService<Image> {
    // 上传图片
    Result addImg(Image image,Integer userId);

    // 删除图片
    Result deleteImg(Integer imageId,Integer userId);

    // 移动图片
    Result delImageType(String imgTypeName,Integer imageId);

    // 图片数量
    Result imgCount(String imgTypeName,Integer userId);
    Result noTypeImgCount(Integer userId);

    // 恢复图片
    Result recoverRecycle_bin(Integer userId,Integer imageId);

    // 删除图片
    Result delRecycle_bin(Integer imageId);

    // 查询单张图片详情
    Result selectOneImg(Integer imageId);

    // 查询图片
    Result selectImgList(Integer userId);

    // 根据图片分类查询图片
    Result selectImgListWithType(String imgTypeName,Integer userId);

    // 添加图片分类
    Result addImageType(String imgTypeName,Integer userId);

    // 删除图片分类
    Result delImgType(String imgType,Integer userId);

    // 查询图片所有分类
    Result selectImgType(Integer userId);

    // 分类重命名
    Result renameImg(String oldName,String newName,Integer userId);

    // 清空文件
    Result removeDir(Integer userId);

    // 清空文件夹
    Result removeTypeDir(String imgType,Integer userId);

    // 回收站图片渲染
    Result queryRecycle_bin(Integer userId);
}
