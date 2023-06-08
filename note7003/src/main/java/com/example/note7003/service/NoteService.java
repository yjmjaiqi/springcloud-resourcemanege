package com.example.note7003.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commons.entity.Note;
import com.example.commons.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* @author 易佳明
* @description 针对表【note】的数据库操作Service
* @createDate 2023-05-02 15:41:41
*/
@Service
@FeignClient(name = "note-provider")
public interface NoteService{

    // 添加笔记
    @PostMapping("/addNote/{id}")
    public Result addNote(@RequestBody Note note,@PathVariable("id") Integer id);

    // 删除笔记
    @GetMapping("/deleteNote/{id}/{userId}")
    public Result deleteNote(@PathVariable("id") Integer id,@PathVariable("userId") Integer userId);
    // 修改笔记
    @PostMapping("/updateNote")
    public Result updateNote(@RequestBody Note note);

    // 笔记数量
    @GetMapping("/noteCount/{noteType}/{userId}")
    public Result noteCount(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId);
    // 无分类笔记数量
    @GetMapping("/noTypeCount/{userId}")
    public Result noTypeCount(@PathVariable("userId") Integer userId);

    // 笔记详情
    @GetMapping("/noteDetail/{id}")
    public Result noteDetail(@PathVariable("id") Integer id);

    // 无笔记列表渲染
    @GetMapping("/selectList/{userId}")
    public Result selectList(@PathVariable("userId") Integer userId);

    // 有笔记列表渲染
    @GetMapping("/selectListType/{noteType}/{userId}")
    public Result selectListType(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId);

    // 添加笔记类型
    @GetMapping("/addNoteType/{noteTypeName}/{userId}")
    public Result addNoteType(@PathVariable("noteTypeName") String noteTypeName,@PathVariable("userId") Integer userId);

    // 删除笔记类型
    @GetMapping("/delNoteType/{noteTypeName}/{userId}")
    public Result delNoteType(@PathVariable("noteTypeName") String noteTypeName,@PathVariable("userId") Integer userId);

    // 移动分类
    @GetMapping("/moveNoteType/{noteType}/{noteId}")
    public Result moveNoteType(@PathVariable("noteType") String noteType,@PathVariable("noteId") Integer noteId);

    // 获取笔记分类
    @GetMapping("/noteType/{userId}")
    public Result noteType(@PathVariable("userId") Integer userId);

    // 重命名分类
    @GetMapping("/renameNote/{oldName}/{newName}/{userId}")
    public Result renameNote(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                             @PathVariable("userId") Integer userId);

    // 清空笔记
    @GetMapping("/removeDir/{userId}")
    public Result removeDir(@PathVariable("userId") Integer userId);

    // 清空笔记文件夹
    @GetMapping("/removeTypeDir/{noteType}/{userId}")
    public Result removeTypeDir(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId);

    // 从回收站恢复笔记
    @GetMapping("/recoverRecycle_bin/{userId}/{noteId}")
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("noteId") Integer noteId);
    // 从回收站删除笔记
    @GetMapping("/delRecycle_bin/{noteId}")
    public Result delRecycle_bin(@PathVariable("noteId") Integer noteId);

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId);
}
