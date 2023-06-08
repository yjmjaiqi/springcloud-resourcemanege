package com.example.note8003.mapper;

import com.example.commons.entity.Note;
import com.example.commons.util.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author 易佳明
* @description 针对表【note】的数据库操作Mapper
* @createDate 2023-05-02 15:41:41
* @Entity com.example.note8003.entity.Note
*/
@Mapper
public interface NoteMapper extends BaseMapper<Note> {
    // 添加笔记
    @Insert("insert into note(noteTitle,content,noteType,isDel,createTime) values(#{noteTitle},#{content},#{noteType},0,CURRENT_TIMESTAMP)")
    boolean addNote(Note note);

    // 删除笔记(逻辑删除)
    @Update("update note set isDel=1 where id = #{id}")
    int deleteNote(Integer id);


    // 修改笔记
    @Update("update note set noteTitle=#{noteTitle},content=#{content} where id = #{id}")
    boolean updateNote(Note note);

    // 分类重命名
    @Update("update note set noteType = #{newName} where noteType = #{oldName}")
    boolean renameNote(@Param("oldName") String oldName,@Param("newName") String newName);

    // 清空笔记
    @Update("update note set isDel = 1 where id = #{noteId}")
    boolean removeDir(@Param("noteId") Integer noteId);
    @Delete("delete from u_note where noteId = #{noteId}")
    boolean delAllNote(@Param("noteId") Integer noteId);
    @Select("select noteId from u_note where userId = #{userId}")
    List<Integer> noteIds(@Param("userId") Integer userId);

    // 添加到回收站
    @Insert("insert into recycle_bin(noteId,userId) values(#{noteId},#{userId})")
    boolean addRecycle_bin(@Param("noteId") Integer noteId,@Param("userId") Integer userId);

    // 清空笔记文件夹
    @Delete("delete from u_note where noteId = #{noteId}")
    boolean removeTypeDir(@Param("noteId") Integer noteId);
    @Update("update note set isDel = 1 where noteType = #{noteType}")
    boolean delDirNote(@Param("noteType") String noteType);
    @Select("select id from note where noteType = #{noteType}")
    List<Integer> noteIdsType(@Param("noteType") String noteType);


    // 恢复笔记
    @Update("update note set isDel = 0 where id = #{noteId}")
    boolean recoverNote(@Param("noteId") Integer noteId);
    // 从回收站删除笔记
    @Delete("delete from recycle_bin where noteId = #{noteId}")
    boolean recoverRecycle_bin(@Param("noteId") Integer noteId);

    // 回收站视频渲染
    @Select("select noteId from recycle_bin where noteId != 0 and userId = #{userId}")
    List<Integer> queryRecycle_bin(@Param("userId") Integer userId);
}




