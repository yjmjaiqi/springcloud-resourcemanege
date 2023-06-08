package com.example.note8003.mapper;

import com.example.commons.entity.NoteType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteTypeMapper {
    // 添加分类
    @Insert("insert into note_type(noteTypeName,userId) values(#{noteTypeName},#{userId})")
    boolean addNoteType(@Param("noteTypeName") String noteTypeName,@Param("userId") Integer userId);

    // 删除分类
    @Delete("delete from note_type where noteTypeName = #{noteTypeName} and userId = #{userId}")
    boolean delNoteType(@Param("noteTypeName") String noteTypeName,@Param("userId") Integer userId);

    // 移动分类
    @Update("update note set noteType = #{noteType} where id = #{noteId}")
    boolean moveNoteType(@Param("noteType") String noteType,@Param("noteId") Integer noteId);

    // 获取笔记分类
    @Select("select noteTypeName from note_type where userId = #{userId}")
    List<NoteType> noteType(@Param("userId") Integer userId);

    // 分类重命名
    @Update("update note_type set noteTypeName = #{newName} where noteTypeName = #{oldName} and userId = #{userId}")
    boolean renameNote(@Param("oldName") String oldName,@Param("newName") String newName,@Param("userId") Integer userId);
}
