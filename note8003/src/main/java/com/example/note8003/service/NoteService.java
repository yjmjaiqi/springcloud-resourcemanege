package com.example.note8003.service;

import com.example.commons.entity.Note;
import com.example.commons.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author 易佳明
* @description 针对表【note】的数据库操作Service
* @createDate 2023-05-02 15:41:41
*/
public interface NoteService extends IService<Note> {

    // 添加笔记
    Result addNote(Note note,Integer userId);

    // 删除笔记
    Result deleteNote(Integer id,Integer userId);


    // 笔记详情
    Result noteDetail(Integer id);

    // 笔记数量
    Result noteCount(String noteType,Integer userId);
    Result noTypeCount(Integer userId);

    // 笔记渲染
    Result selectList(Integer userId);

    // 根据比较类型渲染
    Result selectListType(String noteType,Integer userId);

    // 修改笔记
    Result updateNote(Note note);

    // 添加分类
    Result addNoteType(String noteTypeName,Integer userId);

    // 删除分类
    Result delNoteType(String noteTypeName,Integer userId);

    // 移动分类
    Result moveNoteType(String noteType,Integer noteId);

    // 获取笔记分类
    Result noteType(Integer userId);

    // 分类重命名
    Result renameNote(String oldName,String newName,Integer userId);

    // 清空笔记
    Result removeDir(Integer userId);

    // 清空笔记文件夹
    Result removeTypeDir(String noteType,Integer userId);

    // 从回收站恢复笔记
    Result recoverRecycle_bin(Integer userId,Integer noteId);

    // 从回收站删除笔记
    Result delRecycle_bin(Integer noteId);

    // 回收站笔记渲染
    Result queryRecycle_bin(Integer userId);

}
