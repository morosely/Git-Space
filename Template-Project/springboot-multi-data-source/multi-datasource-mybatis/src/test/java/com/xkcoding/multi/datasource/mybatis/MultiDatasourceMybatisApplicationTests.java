package com.xkcoding.multi.datasource.mybatis;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkcoding.multi.datasource.mybatis.model.User;
import com.xkcoding.multi.datasource.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MultiDatasourceMybatisApplicationTests {

    @Autowired
    private UserService userService;

    /**
     * 主从库添加
     */
    @Test
    public void addUser() {
        User userMaster = User.builder().name("主库添加").age(20).build();
        userService.addUser(userMaster);

        User userSlave = User.builder().name("从库添加").age(20).build();
        userService.save(userSlave);
    }

    /**
     * 从库查询
     */
    @Test
    public void testListUser() {
        List<User> list = userService.list(new QueryWrapper<>());
        log.info("【list】= {}", JSONUtil.toJsonStr(list));
    }
}

