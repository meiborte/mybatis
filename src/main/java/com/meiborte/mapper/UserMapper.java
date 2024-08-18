package com.meiborte.mapper;

import com.meiborte.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    /**
     * Mybatis 参数封装
     * 单个参数
     * 1. POJO类型
     * 2. Map集合
     * 3.Collection
     * 4.List
     * 5.Array
     * 6.其他类型
     *多个参数: 封装为Map集合
     * map.put("arg0",参数值1);
     * map.put("param1",参数值1);
     * mapp.put("param2",参数值2);
     * map.put("agr1",参数值2);
     */

    User select(@Param(value = "name") String name, @Param("age") Integer age,@Param("email") String email);
}

