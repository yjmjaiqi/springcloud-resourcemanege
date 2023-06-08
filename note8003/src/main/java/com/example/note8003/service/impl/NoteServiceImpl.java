package com.example.note8003.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commons.entity.Note;
import com.example.commons.entity.NoteType;
import com.example.commons.entity.Page;
import com.example.commons.util.BeanUtil;
import com.example.commons.util.Result;
import com.example.commons.Vo.NoteVo;
import com.example.note8003.mapper.NoteTypeMapper;
import com.example.note8003.mapper.UserNoteMapper;
import com.example.note8003.service.NoteService;
import com.example.note8003.mapper.NoteMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author 易佳明
* @description 针对表【note】的数据库操作Service实现
* @createDate 2023-05-02 15:41:41
*/
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService{

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NoteTypeMapper noteTypeMapper;
    @Autowired
    private UserNoteMapper userNoteMapper;

    @Override
    @Transactional
    public Result addNote(Note note,Integer userId) {
        List<Note> list = queryNote(note);
        if(list.size() > 0){
            return Result.Fail("该笔记已存在");
        }
        if(note.getNoteType().equals("0")){
            note.setNoteType(" ");
        }
        boolean flag = noteMapper.addNote(note);
        if(!flag){
            return Result.Fail("笔记添加失败");
        }
        LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Note::getNoteTitle,note.getNoteTitle());
        lambdaQueryWrapper.eq(Note::getContent,note.getContent());
        Note note2 = noteMapper.selectOne(lambdaQueryWrapper);
        userNoteMapper.insert_unote(userId, note2.getId());
        return Result.ok("笔记添加成功");
    }

    public List<Note> queryNote(Note note) {
        LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Note::getNoteTitle,note.getNoteTitle());
        lambdaQueryWrapper.eq(Note::getContent,note.getContent());
        return noteMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    @Transactional
    public Result deleteNote(Integer id,Integer userId) {
        int i = noteMapper.deleteNote(id);
        if(i != 1){
            return Result.delIdErr("笔记删除失败");
        }
        userNoteMapper.del_unote(id);
        noteMapper.addRecycle_bin(id,userId);
        return Result.ok("笔记删除成功");
    }

    @Override
    public Result updateNote(Note note) {
        boolean flag = noteMapper.updateNote(note);
        if(!flag){
            return Result.updateFail("笔记修改失败");
        }
        return Result.ok("笔记修改成功");
    }

    @Override
    public Result addNoteType(String noteTypeName,Integer userId) {
        boolean b = noteTypeMapper.addNoteType(noteTypeName,userId);
        if(b){
            return Result.ok("添加笔记分类成功");
        }
        return Result.Fail("添加笔记分类失败");
    }

    @Override
    public Result delNoteType(String noteTypeName,Integer userId) {
        boolean b = noteTypeMapper.delNoteType(noteTypeName,userId);
        if(b){
            List<Note> list = queryListNote(noteTypeName);
            if(Objects.isNull(list)){
                return Result.Fail("笔记分类删除失败");
            }
            for(Note note : list){
                userNoteMapper.del_unote(note.getId());
                noteMapper.deleteNote(note.getId());
            }
            return Result.ok("笔记分类删除成功");
        }else {
            return  Result.Fail("笔记分类删除失败");
        }
    }

    @Override
    public Result moveNoteType(String noteType, Integer noteId) {
        boolean b = noteTypeMapper.moveNoteType(noteType, noteId);
        if(b){
            return Result.ok("笔记移动成功");
        }
        return Result.Fail("笔记移动失败");
    }

    @Override
    public Result noteType(Integer userId) {
        List<NoteType> list = noteTypeMapper.noteType(userId);
        if(Objects.isNull(list)){
            return Result.queryFail("笔记分类为空");
        }
        return Result.ok(list);
    }

    @Override
    @Transactional
    public Result renameNote(String oldName, String newName,Integer userId) {
        boolean b = noteTypeMapper.renameNote(oldName, newName,userId);
        if(b){
            noteMapper.renameNote(oldName, newName);
            return Result.ok("重命名成功");
        }
        return Result.updateFail("重命名失败");
    }

    @Override
    public Result removeDir(Integer userId) {
        List<Integer> list = noteMapper.noteIds(userId);
        boolean flag = false;
        for(Integer noteId : list){
            boolean b2 = noteMapper.addRecycle_bin(noteId,userId);
            if(b2){
                noteMapper.removeDir(noteId);
                noteMapper.delAllNote(noteId);
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
        List<Integer> list = noteMapper.noteIdsType(imgType);
        if(Objects.isNull(list)){
            return Result.Fail("清空失败");
        }
        for(Integer noteId : list){
            boolean b = noteMapper.removeTypeDir(noteId);
            if(b){
                boolean b1 = noteMapper.delDirNote(imgType);
                if(b1){
                    noteMapper.addRecycle_bin(noteId,userId);
                }
            }
        }

        return Result.Fail("清空失败");
    }

    @Override
    public Result recoverRecycle_bin(Integer userId,Integer noteId) {
        boolean b = noteMapper.recoverRecycle_bin(noteId);
        if(b){
            boolean b1 = noteMapper.recoverNote(noteId);
            if(b1){
                userNoteMapper.insert_unote(userId, noteId);
                return Result.ok("恢复成功");
            }
        }
        return Result.Fail("恢复失败");
    }

    @Override
    public Result delRecycle_bin(Integer noteId) {
        boolean b = noteMapper.recoverRecycle_bin(noteId);
        if(b){
            return Result.ok("删除成功");
        }
        return Result.Fail("删除失败");
    }

    @Override
    public Result queryRecycle_bin(Integer userId) {
        List<Integer> noteList = noteMapper.queryRecycle_bin(userId);
        List<NoteVo> noteVos = new ArrayList<>();
        if(Objects.isNull(noteList)){
            return Result.queryFail("暂无数据");
        }
        for(Integer noteId : noteList){
            LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Note::getId,noteId);
            Note note = noteMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(note)){
                continue;
            }
            NoteVo copy = BeanUtil.copy(note, NoteVo.class);
            noteVos.add(copy);
        }
        return Result.ok(noteVos);
    }

    @Override
    public Result noteDetail(Integer id) {
        LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Note::getId,id);
        lambdaQueryWrapper.eq(Note::getIsDel,0);
        Note note = noteMapper.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(note)){
            return Result.Fail("当前访问笔记不存在");
        }
        NoteVo noteVo = BeanUtil.copy(note, NoteVo.class);
        return Result.ok(noteVo);
    }

    @Override
    public Result noteCount(String noteType, Integer userId) {
        List<Integer> list = userNoteMapper.u_note_userIds(userId);
        System.out.println("-=================list"+list);
        List<Note> noteList = new ArrayList<>();
        if(list.size() == 0){
            return Result.Fail("数据为空");
        }
        for(Integer noteId : list){
            LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Note::getNoteType,noteType);
            lambdaQueryWrapper.eq(Note::getId,noteId);
            lambdaQueryWrapper.eq(Note::getIsDel,0);
            Note note = noteMapper.selectOne(lambdaQueryWrapper);
            System.out.println("-=================note"+note);
            if(Objects.isNull(note)){
                continue;
            }
            noteList.add(note);
        }

        System.out.println("noteList.size()"+noteList.size());
        return Result.ok(noteList.size());
    }
    @Override
    public Result noTypeCount(Integer userId) {
        List<Integer> list = userNoteMapper.u_note_userIds(userId);
        List<Note> noteList = new ArrayList<>();
        if(Objects.isNull(list)){
            return Result.Fail("数据为空");
        }
        for(Integer noteId : list){
            LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Note::getId,noteId);
            lambdaQueryWrapper.eq(Note::getIsDel,0);
            Note note = noteMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(note)){
                continue;
            }
            noteList.add(note);
        }
        return Result.ok(noteList.size());
    }

    @Override
    public Result selectList(Integer userId) {
        List<Note> noteList = new ArrayList<>();
        List<Integer> list = noteMapper.noteIds(userId);
        if(Objects.isNull(list)){
            return Result.queryFail("查询失败");
        }
        for(Integer noteId : list){
            LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Note::getId,noteId);
            lambdaQueryWrapper.eq(Note::getIsDel,0);
            Note note = noteMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(note)){
                continue;
            }
            noteList.add(note);
        }
        List<NoteVo> noteVoList = BeanUtil.copyList(noteList, NoteVo.class);
        return Result.ok(noteVoList);
    }

    @Override
    public Result selectListType(String noteType, Integer userId) {
        List<Note> noteList = new ArrayList<>();
        List<Integer> list = userNoteMapper.u_note_userIds(userId);
        if(Objects.isNull(list)){
            return Result.queryFail("查询失败");
        }
        for(Integer noteId : list){
            LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Note::getId,noteId);
            lambdaQueryWrapper.eq(Note::getIsDel,0);
            lambdaQueryWrapper.eq(Note::getNoteType,noteType);
            Note note = noteMapper.selectOne(lambdaQueryWrapper);
            if(Objects.isNull(note)){
                continue;
            }
            noteList.add(note);
        }
        List<NoteVo> noteVoList = BeanUtil.copyList(noteList, NoteVo.class);
        return Result.ok(noteVoList);
    }

    public List<Note> queryListNote(String noteType){
        LambdaQueryWrapper<Note> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Note::getNoteType,noteType);
        lambdaQueryWrapper.eq(Note::getIsDel,0);
        List<Note> list = noteMapper.selectList(lambdaQueryWrapper);
        if(Objects.isNull(list)){
            return null;
        }
        return list;
    }
}




