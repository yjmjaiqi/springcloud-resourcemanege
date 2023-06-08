package com.example.note8003.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserNoteMapper {
    // 添加笔记后插入信息到用户笔记表
    @Insert("insert into u_note(userId,noteId) values(#{userId},#{noteId})")
    boolean insert_unote(@Param("userId") Integer userId,@Param("noteId") Integer noteId);

    // 从用户信息表查询笔记id
    @Select("select noteId from u_note where userId = #{userId} order by noteId desc")
    List<Integer> u_note_userIds(@Param("userId") Integer userId);

    // 删除笔记后删除记录
    @Delete("delete from u_note where noteId = #{noteId}")
    boolean del_unote(@Param("noteId") Integer noteId);
}
