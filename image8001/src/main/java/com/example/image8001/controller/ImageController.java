package com.example.image8001.controller;

import com.example.commons.Vo.ImageVo;
import com.example.commons.entity.Image;
import com.example.commons.util.BeanUtil;
import com.example.commons.util.Result;
import com.example.image8001.service.ImageService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    // 无类型上传图片
    // image.getImageType() = "0"表示无类型上传
    @PostMapping(value = "/addImg/{userId}")
    public Result addImg(@RequestBody Image image, @PathVariable("userId") Integer userId){
        if (Objects.isNull(image)) {
            return Result.Fail("图片上传失败");
        }
        imageService.addImg(image,userId);
        return Result.ok("图片上传成功");
    }
    // 删除图片
    @GetMapping("/delImage/{imageId}/{userId}")
    public Result delImage(@PathVariable("imageId") Integer imageId,@PathVariable("userId") Integer userId){
        return imageService.deleteImg(imageId,userId);
    }

    // 移动图片
    @GetMapping("/delImageType/{imgTypeName}/{imageId}")
    public Result delImageType(@PathVariable("imgTypeName") String imgTypeName,@PathVariable("imageId") Integer imageId){
        return imageService.delImageType(imgTypeName,imageId);
    }

    // 图片数量
    @GetMapping("/imgCount/{imgTypeName}/{userId}")
    public Result imgCount(@PathVariable("imgTypeName") String imgTypeName,@PathVariable("userId") Integer userId){
        if(imgTypeName.equals("0")){
            return imageService.imgCount(" ",userId);
        }
        return imageService.imgCount(imgTypeName,userId);
    }
    @GetMapping("/noTypeImgCount/{userId}")
    public Result noTypeImgCount(@PathVariable("userId") Integer userId){
        return imageService.noTypeImgCount(userId);
    }

    // 单图详情
    @GetMapping("/imageDetail/{imageId}")
    public Result imageDetail(@PathVariable("imageId") Integer imageId){
        return imageService.selectOneImg(imageId);
    }

    // 无分类查询图片渲染
    @GetMapping("/selectImgList/{userId}")
    public Result selectImgList(@PathVariable("userId") Integer userId){
        return imageService.selectImgList(userId);
    }

    // 分类查询图片渲染
    @GetMapping("/selectImgListType/{imgType}/{userId}")
    public Result selectImgListType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId){
        return imageService.selectImgListWithType(imgType,userId);
    }

    // 添加图片分类
    @GetMapping("/addImgType/{imgType}/{userId}")
    public Result addImgType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId){
        return imageService.addImageType(imgType,userId);
    }

    // 删除图片分类
    @GetMapping("/delImgType/{imgType}/{userId}")
    public Result delImgType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId){
        return imageService.delImgType(imgType,userId);
    }

    // 查询所有图片分类
    @GetMapping("/allImgType/{userId}")
    public Result allImgType(@PathVariable("userId") Integer userId){
        return imageService.selectImgType(userId);
    }

    // 重命名图片分类
    @GetMapping("/renameType/{oldName}/{newName}/{userId}")
    public Result renameType(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                             @PathVariable("userId") Integer userId){
        return imageService.renameImg(oldName, newName,userId);
    }

    // 清空文件
    @GetMapping("/removeDir/{userId}")
    public Result removeDir(@PathVariable("userId") Integer userId) {
        return imageService.removeDir(userId);
    }

    // 清空文件夹
    @GetMapping("/removeTypeDir/{imgType}/{userId}")
    public Result removeTypeDir(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId) {
        return imageService.removeTypeDir(imgType,userId);
    }

    // 从回收站恢复图片
    @GetMapping("/recoverRecycle_bin/{userId}/{imageId}")
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("imageId") Integer imageId) {
        return imageService.recoverRecycle_bin(userId,imageId);
    }

    // 从回收站删除图片
    @GetMapping("/delRecycle_bin/{imageId}")
    public Result delRecycle_bin(@PathVariable("imageId") Integer imageId) {
        return imageService.delRecycle_bin(imageId);
    }

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId) {
        return imageService.queryRecycle_bin(userId);
    }
}
