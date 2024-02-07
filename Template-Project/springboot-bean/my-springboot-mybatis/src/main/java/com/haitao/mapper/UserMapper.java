package com.haitao.mapper;

import com.haitao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from account where aid = #{id}")
    public User findById(Integer id);

}
