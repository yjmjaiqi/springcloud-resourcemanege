package com.example.note8003.controller;

import com.example.commons.Vo.NoteVo;
import com.example.commons.entity.Note;
import com.example.commons.util.BeanUtil;
import com.example.commons.util.Result;
import com.example.note8003.service.NoteService;
import org.apache.ibatis.annotations.Select;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    // 添加笔记
    @PostMapping("/addNote/{id}")
    public Result addNote(@RequestBody Note note,@PathVariable("id") Integer id){
        return noteService.addNote(note,id);
    }

    // 删除笔记
    @GetMapping("/deleteNote/{id}/{userId}")
    public Result deleteNote(@PathVariable("id") Integer id,@PathVariable("userId") Integer userId){
        return noteService.deleteNote(id,userId);
    }

    // 修改笔记
    @PostMapping("/updateNote")
    public Result updateNote(@RequestBody Note note){
        return noteService.updateNote(note);
    }

    // 笔记数量
    @GetMapping("/noteCount/{noteType}/{userId}")
    public Result noteCount(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId){
        if(noteType.equals("0")){
            return noteService.noteCount(" ",userId);
        }
        return noteService.noteCount(noteType,userId);
    }
    // 无分类笔记数量
    @GetMapping("/noTypeCount/{userId}")
    public Result noTypeCount(@PathVariable("userId") Integer userId){
        return noteService.noTypeCount(userId);
    }

    // 笔记详情
    @GetMapping("/noteDetail/{id}")
    public Result noteDetail(@PathVariable("id") Integer id){
        return noteService.noteDetail(id);
    }

    // 无笔记列表渲染
    @GetMapping("/selectList/{userId}")
    public Result selectList(@PathVariable("userId") Integer userId){
        return noteService.selectList(userId);
    }

    // 有笔记列表渲染
    @GetMapping("/selectListType/{noteType}/{userId}")
    public Result selectListType(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId){
        return noteService.selectListType(noteType,userId);
    }

    // 添加笔记类型
    @GetMapping("/addNoteType/{noteTypeName}/{userId}")
    public Result addNoteType(@PathVariable("noteTypeName") String noteTypeName,@PathVariable("userId") Integer userId){
        return noteService.addNoteType(noteTypeName,userId);
    }

    // 删除笔记类型
    @GetMapping("/delNoteType/{noteTypeName}/{userId}")
    public Result delNoteType(@PathVariable("noteTypeName") String noteTypeName,@PathVariable("userId") Integer userId){
        return noteService.delNoteType(noteTypeName,userId);
    }

    // 移动分类
    @GetMapping("/moveNoteType/{noteType}/{noteId}")
    public Result moveNoteType(@PathVariable("noteType") String noteType,@PathVariable("noteId") Integer noteId){
        return noteService.moveNoteType(noteType,noteId);
    }

    // 获取笔记分类
    @GetMapping("/noteType/{userId}")
    public Result noteType(@PathVariable("userId") Integer userId){
        return noteService.noteType(userId);
    }

    // 重命名分类
    @GetMapping("/renameNote/{oldName}/{newName}/{userId}")
    public Result renameNote(@PathVariable("oldName") String oldName,@PathVariable("newName") String newName,
                             @PathVariable("userId") Integer userId){
        return noteService.renameNote(oldName, newName,userId);
    }

    // 清空笔记
    @GetMapping("/removeDir/{userId}")
    public Result removeDir(@PathVariable("userId") Integer userId) {
        return noteService.removeDir(userId);
    }

    // 清空笔记文件夹
    @GetMapping("/removeTypeDir/{noteType}/{userId}")
    public Result removeTypeDir(@PathVariable("noteType") String noteType,@PathVariable("userId") Integer userId) {
        return noteService.removeTypeDir(noteType,userId);
    }

    // 从回收站恢复笔记
    @GetMapping("/recoverRecycle_bin/{userId}/{noteId}")
    public Result recoverRecycle_bin(@PathVariable("userId") Integer userId,@PathVariable("noteId") Integer noteId) {
        return noteService.recoverRecycle_bin(userId,noteId);
    }

    // 从回收站删除笔记
    @GetMapping("/delRecycle_bin/{noteId}")
    public Result delRecycle_bin(@PathVariable("noteId") Integer noteId) {
        return noteService.delRecycle_bin(noteId);
    }

    // 回收站视频渲染
    @GetMapping("/queryRecycle_bin/{userId}")
    public Result queryRecycle_bin(@PathVariable("userId") Integer userId) {
        return noteService.queryRecycle_bin(userId);
    }
}
