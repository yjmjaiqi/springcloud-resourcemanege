package com.example.image8001.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.Vo.ImageVo;
import com.example.commons.entity.Image;
import com.example.commons.entity.ImageType;
import com.example.commons.util.BeanUtil;
import com.example.commons.util.Result;
import com.example.image8001.mapper.ImageTypeMapper;
import com.example.image8001.mapper.UserImageMapper;
import com.example.image8001.service.ImageService;
import com.example.image8001.mapper.ImageMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author 易佳明
* @description 针对表【image】的数据库操作Service实现
* @createDate 2023-05-02 15:57:32
*/
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>
    implements ImageService{

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ImageTypeMapper imageTypeMapper;
    @Autowired
    private UserImageMapper userImageMapper;

    @Override
    @Transactional
    public Result addImg(Image image,Integer userId) {
        boolean flag = imageMapper.addImg(image);
        // 分类不为空
        if(flag && !image.getImgType().isEmpty()){
            Image image1 = queryImg(image.getImgUrl());
            if(!Objects.isNull(image1)){
                // 插入数据到u_image
                userImageMapper.u_image(userId,image1.getId());
            }
            return Result.ok("图片上传成功成功");
        }else if(flag) {
            Image image1 = queryImg(image.getImgUrl());
            if(!Objects.isNull(image1)){
                userImageMapper.u_image(userId,image1.getId());
            }
            return Result.ok("图片上传成功成功");
        }


        return Result.Fail("图片上传成功失败");
    }

    @Override
    @Transactional
    public Result deleteImg(Integer imageId,Integer userId) {
        boolean flag = imageMapper.deleteImg(imageId);
        // 图片删除成功
        if(flag){
            userImageMapper.deleteU_Image(imageId);
            imageMapper.AddRecycle_bin(imageId,userId);
            return Result.ok("图片删除成功");
        }
        return Result.Fail("图片删除失败");
    }

    @Override
    public Result delImageType(String imgTypeName,Integer imageId) {
        boolean b = imageMapper.delImageType(imgTypeName,imageId);
        if(b){
            return Result.ok("移除成功");
        }
        return Result.Fail("移除失败");
    }

    @Override
    public Result imgCount(String imgTypeName,Integer userId) {
        List<Integer> list = imageMapper.imagIds(userId);
        List<Image> count = new ArrayList<>();
        if(Objects.isNull(list)){
            return Result.ok("暂无图片");
        }
        for(Integer imgId : list){
            LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Image::getImgType,imgTypeName);
            lambdaQueryWrapper.eq(Image::getIsDel,0);
            lambdaQueryWrapper.eq(Image::getId,imgId);
            Image image = imageMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(image)){
                continue;
            }
            count.add(image);
        }
        return Result.ok(count.size());
    }

    @Override
    public Result noTypeImgCount(Integer userId) {
        List<Integer> list = imageMapper.imagIds(userId);
        List<Image> count = new ArrayList<>();
        if(Objects.isNull(list)){
            return Result.ok("暂无图片");
        }
        for(Integer imgId : list){
            LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Image::getIsDel,0);
            lambdaQueryWrapper.eq(Image::getId,imgId);
            Image image = imageMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(image)){
                continue;
            }
            count.add(image);
        }
        return Result.ok(count.size());
    }

    @Override
    public Result recoverRecycle_bin(Integer userId,Integer imageId) {
        boolean b = imageMapper.recoverRecycle_bin(imageId);
        if(b){
            boolean b1 = imageMapper.recoverImg(imageId);
            if(b1){
                userImageMapper.recoverImg(userId, imageId);
                return Result.ok("恢复成功");
            }
        }
        return Result.Fail("恢复失败");
    }

    @Override
    public Result delRecycle_bin(Integer imageId) {
        boolean b = imageMapper.recoverRecycle_bin(imageId);
        if(b){
            return Result.ok("删除成功");
        }
        return Result.Fail("删除失败");
    }

    @Override
    public Result selectOneImg(Integer imageId) {
        Image image = imageMapper.selectById(imageId);
        if(Objects.isNull(image)){
            return Result.queryFail("查询失败");
        }
        ImageVo imageVo = BeanUtil.copy(image, ImageVo.class);
        return Result.ok(imageVo);
    }

    @Override
    public Result selectImgList(Integer userId) {
        List<Image> images = new ArrayList<>();
        List<Integer> imgIds = imageMapper.imagIds(userId);
        if(Objects.isNull(imgIds)){
            return Result.queryFail("暂无数据");
        }
        for(Integer id : imgIds){
            LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Image::getId,id);
            lambdaQueryWrapper.eq(Image::getIsDel,0);
            Image image = imageMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(image)){
                continue;
            }
            images.add(image);
        }
        List<ImageVo> imageVos = BeanUtil.copyList(images, ImageVo.class);
        return Result.ok(imageVos);
    }

    @Override
    public Result selectImgListWithType(String imgTypeName,Integer userId) {
        List<Image> images = new ArrayList<>();
        List<Integer> imgIds = imageMapper.imagIds(userId);
        if(Objects.isNull(imgIds)){
            return Result.queryFail("暂无数据");
        }
        for(Integer id : imgIds){
            LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Image::getImgType,imgTypeName);
            lambdaQueryWrapper.eq(Image::getId,id);
            lambdaQueryWrapper.eq(Image::getIsDel,0);
            Image image = imageMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(image)){
                continue;
            }
            images.add(image);
        }
        List<ImageVo> imageVos = BeanUtil.copyList(images, ImageVo.class);
        return Result.ok(imageVos);
    }

    @Override
    public Result addImageType(String imgTypeName,Integer userId) {
        boolean flag = imageTypeMapper.addImageType(imgTypeName,userId);
        if(!flag){
            return Result.Fail("分类创建失败");
        }
        return Result.ok("分类创建成功");
    }

    @Override
    public Result delImgType(String imgType,Integer userId) {
        boolean flag = imageTypeMapper.deleteImgType(imgType,userId);
        if(flag){
            List<Image> images = queryImgList(imgType);
            if(!Objects.isNull(images)){
                for(Image image : images){
                    userImageMapper.deleteU_Image(image.getId());
                }
                imageMapper.deleteImage(imgType);
            }
            return Result.ok("删除成功");
        }
        return Result.Fail("删除失败");
    }

    @Override
    public Result selectImgType(Integer userId) {
        List<ImageType> imageTypes = imageTypeMapper.selectImgType(userId);
        if(!Objects.isNull(imageTypes)){
            return Result.ok(imageTypes);
        }
        return Result.queryFail("查询失败");
    }

    @Override
    @Transactional
    public Result renameImg(String oldName, String newName,Integer userId) {
        boolean b = imageTypeMapper.renameImg(oldName, newName,userId);
        if(b){
            imageMapper.renameImg(oldName, newName);
            return Result.ok("重命名成功");
        }
        return Result.Fail("重命名失败");
    }

    @Override
    @Transactional
    public Result removeDir(Integer userId) {
        List<Integer> list = imageMapper.imgIds(userId);
        boolean flag = false;
        for(Integer imageId : list){
            boolean b2 = imageMapper.AddRecycle_bin(imageId,userId);
            if(b2){
                imageMapper.delAllImg(imageId);
                imageMapper.removeDir(imageId);
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
    public Result removeTypeDir(String imgType,Integer userId) {
        List<Integer> list = imageMapper.imgIdsType(imgType);
        if(Objects.isNull(list)){
            return Result.Fail("清空失败");
        }
        for(Integer imageId : list){
            boolean b = imageMapper.removeTypeDir(imageId);
            if(b){
                boolean b1 = imageMapper.delDirImg(imgType);
                if(b1){
                    imageMapper.AddRecycle_bin(imageId,userId);
                }
            }
        }

        return Result.Fail("清空失败");
    }

    @Override
    public Result queryRecycle_bin(Integer userId) {
        List<Integer> imageIds = imageMapper.queryRecycle_bin(userId);
        List<ImageVo> imageVos = new ArrayList<>();
        if(Objects.isNull(imageIds)){
            return Result.queryFail("暂无数据");
        }
        for(Integer imgId : imageIds){
            LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Image::getId,imgId);
            Image image = imageMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(image)){
                continue;
            }
            ImageVo copy = BeanUtil.copy(image, ImageVo.class);
            imageVos.add(copy);
        }
        return Result.ok(imageVos);
    }

    // 查询单张图片
    public Image queryImg(String imgUrl){
        LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Image::getImgUrl,imgUrl);
        Image image = imageMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(image)){
            return null;
        }
        return image;
    }

    // 查询图片集合
    public List<Image> queryImgList(String imgType){
        LambdaQueryWrapper<Image> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Image::getImgType,imgType);
        List<Image> images = imageMapper.selectList(lambdaQueryWrapper);
        if(Objects.isNull(images)){
            return null;
        }
        return images;
    }

//    public String dateFormat(Date date){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String format = simpleDateFormat.format(date);
//        return format;
//    }
}




