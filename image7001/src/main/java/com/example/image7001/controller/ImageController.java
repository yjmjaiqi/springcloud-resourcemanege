package com.example.image7001.controller;

import com.example.commons.entity.Image;
import com.example.commons.util.Result;
import com.example.image7001.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Api("图片接口测试")
public class ImageController {
    @Autowired
    private ImageService imageService;

    // 无类型上传图片
    // imageType = "0"表示无类型上传
    @PostMapping(value = "/addImg/{userId}/{imageType}")
    public Result addImg(@RequestPart("file") MultipartFile file, @PathVariable("userId") Integer userId,@PathVariable("imageType") String imageType){
        if (file.isEmpty()) {
            return Result.Fail("图片上传失败");
        }
        // 实例图片Image对象
        Image image = new Image();
        //可以自己加一点校验 例如上传的是不是图片或者上传的文件是不是png格式等等 这里省略
        //获取原来的文件名和后缀
        String originalFilename = file.getOriginalFilename();
        // 文件后缀
        String ext = "."+ originalFilename.split("\\.")[1];
        //生成一个新的文件名（以防有重复的名字存在导致被覆盖）
        String newName = System.currentTimeMillis() + ext;
        String pre = "D:\\桌面\\资料\\2023-下\\springcloud\\resource_manage\\synthesize1\\image7001\\src\\main\\resources\\static\\images\\";
        String path = pre + newName;
        try {
            file.transferTo(new File(path));
            String imgUrl = "http://localhost:7001/images/" + newName;
            image.setImgSize(file.getSize());
            image.setImgUrl(imgUrl);
            if(imageType.equals("0")){
                image.setImgType(" ");
            }else{
                image.setImgType(imageType);
            }
            System.out.println("image======"+image);
            imageService.addImg(image,userId);
            return Result.ok("图片上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageService.addImg(image,userId);
    }

    // 删除图片
    @GetMapping("/delImage/{imageId}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageId", value = "图片Id", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result delImage(@PathVariable("imageId") Integer imageId,@PathVariable("userId") Integer userId){
        return imageService.delImage(imageId,userId);
    }

    // 移动图片
    @GetMapping("/delImageType/{imgTypeName}/{imageId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgTypeName", value = "图片类型名", required = true),
            @ApiImplicitParam(name = "imageId", value = "图片ID", required = true)
    })
    public Result delImageType(@PathVariable("imgTypeName") String imgTypeName,@PathVariable("imageId") Integer imageId){
        return imageService.delImageType(imgTypeName,imageId);
    }

    // 图片数量
    @GetMapping("/imgCount/{imgTypeName}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgTypeName", value = "图片类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result imgCount(@PathVariable("imgTypeName") String imgTypeName,@PathVariable("userId") Integer userId){
        return imageService.imgCount(imgTypeName,userId);
    }
    @GetMapping("/noTypeImgCount/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result noTypeImgCount(@PathVariable("userId") Integer userId){
        return imageService.noTypeImgCount(userId);
    }

    // 单图详情
    @GetMapping("/imageDetail/{imageId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageId", value = "图片ID", required = true)
    })
    public Result imageDetail(@PathVariable("imageId") Integer imageId){
        return imageService.imageDetail(imageId);
    }

    // 无分类查询图片渲染
    @GetMapping("/selectImgList/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result selectImgList(@PathVariable("userId") Integer userId){
        return imageService.selectImgList(userId);
    }

    // 分类查询图片渲染
    @GetMapping("/selectImgListType/{imgType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgTypeName", value = "图片类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result selectImgListType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId){
        return imageService.selectImgListType(imgType,userId);
    }

    // 添加图片分类
    @GetMapping("/addImgType/{imgType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgTypeName", value = "图片类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result addImgType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId){
        return imageService.addImgType(imgType,userId);
    }

    // 删除图片分类
    @GetMapping("/delImgType/{imgType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgTypeName", value = "图片类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result delImgType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId){
        return imageService.delImgType(imgType,userId);
    }

    // 查询所有图片分类
    @GetMapping("/allImgType/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result allImgType(@PathVariable("userId") Integer userId){
        return imageService.allImgType(userId);
    }


    // 重命名图片分类
    @GetMapping("/renameType/{oldName}/{newName}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldName", value = "旧图片类型名", required = true),
            @ApiImplicitParam(name = "newName", value = "新图片类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result renameType(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                             @PathVariable("userId") Integer userId){
        return imageService.renameType(oldName, newName,userId);
    }

    // 清空文件
    @GetMapping("/removeDir/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result removeDir(@PathVariable("userId") Integer userId) {
        return imageService.removeDir(userId);
    }

    // 清空文件夹
    @GetMapping("/removeTypeDir/{imgType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgType", value = "图片类型名", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    })
    public Result removeTypeDir(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId) {
        return imageService.removeTypeDir(imgType,userId);
    }

    // 从回收站恢复图片
    @GetMapping("/recoverRecycle_bin/{userId}/{imageId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "imageId", value = "图片ID", required = true)
    })
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("imageId") Integer imageId) {
        return imageService.recoverRecycle_bin(userId,imageId);
    }

    // 从回收站删除图片
    @GetMapping("/delRecycle_bin/{imageId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageId", value = "图片ID", required = true)
    })
    public Result delRecycle_bin(@PathVariable("imageId") Integer imageId) {
        return imageService.delRecycle_bin(imageId);
    }

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
    })
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId) {
        return imageService.queryRecycle_bin(userId);
    }
}