package com.fhs.test.service;

import com.fhs.core.trans.anno.AutoTrans;
import com.fhs.test.pojo.Role;
import com.fhs.trans.service.AutoTransable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AutoTrans(namespace = "role", useCache = false, useRedis = false, fields = "name", defaultAlias = "parent")
public class RoleService implements AutoTransable<Role> {

    @Override
    public List<Role> findByIds(List<?> ids) {
        List<Role> result = new ArrayList<>();
        result.add(selectById(null));
        return result;
    }

    @Override
    public List<Role> select() {
        return null;
    }

    @Override
    public Role selectById(Object primaryValue) {
        Role role = new Role();
        role.setName("role1");
        role.setId("1");
        return role;
    }
}
