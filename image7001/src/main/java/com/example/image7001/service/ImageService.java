package com.example.image7001.service;

import com.example.commons.entity.Image;
import com.example.commons.util.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@FeignClient(name = "image-provider")
public interface ImageService {
    // 无类型上传图片
    @PostMapping(value = "/addImg/{userId}")
    public Result addImg(@RequestBody Image image, @PathVariable("userId") Integer userId);
    // 有图片类型上传图片
//    @PostMapping("/addImgHas/{userId}/{imgType}")
//    public Result addImgHas(@RequestBody Image image, @PathVariable("userId") Integer userId,
//                            @PathVariable("imgType") String imgType);

    // 删除图片
    @GetMapping("/delImage/{imageId}/{userId}")
    public Result delImage(@PathVariable("imageId") Integer imageId,@PathVariable("userId") Integer userId);

    // 移动图片
    @GetMapping("/delImageType/{imgTypeName}/{imageId}")
    public Result delImageType(@PathVariable("imgTypeName") String imgTypeName,@PathVariable("imageId") Integer imageId);

    // 图片数量
    @GetMapping("/imgCount/{imgTypeName}/{userId}")
    public Result imgCount(@PathVariable("imgTypeName") String imgTypeName,@PathVariable("userId") Integer userId);
    @GetMapping("/noTypeImgCount/{userId}")
    public Result noTypeImgCount(@PathVariable("userId") Integer userId);

    // 单图详情
    @GetMapping("/imageDetail/{imageId}")
    public Result imageDetail(@PathVariable("imageId") Integer imageId);

    // 无分类查询图片渲染
    @GetMapping("/selectImgList/{userId}")
    public Result selectImgList(@PathVariable("userId") Integer userId);

    // 分类查询图片渲染
    @GetMapping("/selectImgListType/{imgType}/{userId}")
    public Result selectImgListType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId);

    // 添加图片分类
    @GetMapping("/addImgType/{imgType}/{userId}")
    public Result addImgType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId);

    // 删除图片分类
    @GetMapping("/delImgType/{imgType}/{userId}")
    public Result delImgType(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId);

    // 查询所有图片分类
    @GetMapping("/allImgType/{userId}")
    public Result allImgType(@PathVariable("userId") Integer userId);


    // 重命名图片分类
    @GetMapping("/renameType/{oldName}/{newName}/{userId}")
    public Result renameType(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                             @PathVariable("userId") Integer userId);

    // 清空文件
    @GetMapping("/removeDir/{userId}")
    public Result removeDir(@PathVariable("userId") Integer userId);

    // 清空文件夹
    @GetMapping("/removeTypeDir/{imgType}/{userId}")
    public Result removeTypeDir(@PathVariable("imgType") String imgType,@PathVariable("userId") Integer userId);

    // 从回收站恢复图片
    @GetMapping("/recoverRecycle_bin/{userId}/{imageId}")
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("imageId") Integer imageId);
    // 从回收站删除图片
    @GetMapping("/delRecycle_bin/{imageId}")
    public Result delRecycle_bin(@PathVariable("imageId") Integer imageId);

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId);
}
