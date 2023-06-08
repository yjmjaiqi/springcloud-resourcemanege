package com.example.note7003.controller;


import com.example.commons.entity.Note;
import com.example.commons.util.Result;
import com.example.note7003.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("笔记接口测试")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Integer")
    })
    @PostMapping("/addNote/{id}/{noteType}")
    @ApiOperation(value="添加笔记", notes="")
    public Result addNote(@RequestBody Note note,@PathVariable("id") Integer id,@PathVariable("noteType") String noteType){
        note.setNoteType(noteType);
        return noteService.addNote(note,id);
    }

    // 删除笔记
    @GetMapping("/deleteNote/{id}/{userId}")
    @ApiOperation(value="删除笔记", notes="")
    @ApiImplicitParam(name = "id", value = "笔记ID", required = true)
    public Result deleteNote(@PathVariable("id") Integer id,@PathVariable("userId") Integer userId){
        return noteService.deleteNote(id,userId);
    }

    // 修改笔记
    @PostMapping("/updateNote/{id}")
    @ApiOperation(value="修改笔记", notes="")
    @ApiImplicitParam(name = "id", value = "笔记ID", required = true)
    public Result updateNote(@RequestBody Note note,@PathVariable("id") Integer id){
        note.setId(id);
        return noteService.updateNote(note);
    }

    // 笔记数量
    @GetMapping("/noteCount/{noteType}/{userId}")
    @ApiOperation(value="笔记数量", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "笔记类型", required = true),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    })
    public Result noteCount(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId){
        return noteService.noteCount(noteType,userId);
    }
    // 无分类笔记数量
    @ApiOperation(value="无分类笔记数量", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    })
    @GetMapping("/noTypeCount/{userId}")
    public Result noTypeCount(@PathVariable("userId") Integer userId){
        return noteService.noTypeCount(userId);
    }

    @GetMapping("/noteDetail/{id}")
    @ApiOperation(value="笔记详情", notes="")
    @ApiImplicitParam(name = "id", value = "笔记ID", required = true)
    public Result noteDetail(@PathVariable("id") Integer id){
        return noteService.noteDetail(id);
    }

    // 无笔记列表渲染
    @GetMapping("/selectList/{userId}")
    @ApiOperation(value="无类型笔记列表", notes="")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    public Result selectList(@PathVariable("userId") Integer userId){
        return noteService.selectList(userId);
    }

    // 有笔记列表渲染
    @GetMapping("/selectListType/{noteType}/{userId}")
    @ApiOperation(value="有类型笔记列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "笔记类型", required = true),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    })
    public Result selectListType(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId){
        return noteService.selectListType(noteType,userId);
    }

    // 添加笔记类型
    @ApiOperation(value="添加笔记类型", notes="")
    @ApiImplicitParam(name = "value", value = "笔记类型", required = true)
    @GetMapping("/addNoteType/{noteTypeName}/{userId}")
    public Result addNoteType(@PathVariable("noteTypeName") String noteTypeName,@PathVariable("userId") Integer userId){
        return noteService.addNoteType(noteTypeName,userId);
    }

    // 删除笔记类型
    @GetMapping("/delNoteType/{noteTypeName}/{userId}")
    @ApiOperation(value="删除笔记类型", notes="")
    @ApiImplicitParam(name = "value", value = "笔记类型", required = true)
    public Result delNoteType(@PathVariable("noteTypeName") String noteTypeName,@PathVariable("userId") Integer userId){
        return noteService.delNoteType(noteTypeName,userId);
    }

    // 移动分类
    @GetMapping("/moveNoteType/{noteType}/{noteId}")
    @ApiOperation(value="移动笔记类型", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "笔记类型", required = true),
            @ApiImplicitParam(name = "id", value = "笔记ID", required = true)
    })
    public Result moveNoteType(@PathVariable("noteType") String noteType,@PathVariable("noteId") Integer noteId){
        return noteService.moveNoteType(noteType,noteId);
    }

    // 获取笔记分类
    @GetMapping("/noteType/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "userID", required = true)
    })
    public Result noteType(@PathVariable("userId") Integer userId){
        return noteService.noteType(userId);
    }

    // 重命名分类
    @GetMapping("/renameNote/{oldName}/{newName}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldName", value = "旧分类名", required = true),
            @ApiImplicitParam(name = "newName", value = "新分类名", required = true),
            @ApiImplicitParam(name = "userId", value = "userId", required = true)
    })
    public Result renameNote(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                             @PathVariable("userId") Integer userId){
        return noteService.renameNote(oldName, newName,userId);
    }

    // 清空笔记
    @GetMapping("/removeDir/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "userID", required = true)
    })
    public Result removeDir(@PathVariable("userId") Integer userId) {
        return noteService.removeDir(userId);
    }

    // 清空笔记文件夹
    @GetMapping("/removeTypeDir/{noteType}/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noteType", value = "笔记类型", required = true),
            @ApiImplicitParam(name = "userId", value = "userId", required = true)
    })
    public Result removeTypeDir(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId) {
        return noteService.removeTypeDir(noteType,userId);
    }

    // 从回收站恢复笔记
    @GetMapping("/recoverRecycle_bin/{userId}/{noteId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "noteId", value = "笔记id", required = true)
    })
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("noteId") Integer noteId) {
        return noteService.recoverRecycle_bin(userId,noteId);
    }

    // 从回收站删除笔记
    @GetMapping("/delRecycle_bin/{noteId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noteId", value = "笔记id", required = true)
    })
    public Result delRecycle_bin(@PathVariable("noteId") Integer noteId) {
        return noteService.delRecycle_bin(noteId);
    }

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
    })
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId) {
        return noteService.queryRecycle_bin(userId);
    }
}

