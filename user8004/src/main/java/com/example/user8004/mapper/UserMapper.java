package com.example.user8004.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.commons.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
* @author 易佳明
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-05-02 16:22:40
* @Entity com.example.user8004.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set username = #{username},password = #{password} where phone = #{phone}")
    boolean updateUser(User user);

    @Update("update user set icon = #{imgUrl} where id = #{userId}")
    boolean upload(@Param("imgUrl") String imgUrl,@Param("userId") Integer userId);

}




