package com.fhs.test.service;

import com.fhs.core.trans.anno.AutoTrans;
import com.fhs.core.trans.vo.VO;
import com.fhs.test.pojo.Teacher;
import com.fhs.trans.service.AutoTransable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AutoTrans(namespace = "teacher",fields = {"name","age"},
        defaultAlias = "teacher",globalCache = true,cacheSeconds = 10,maxCache = 100)
public class TeacherService implements AutoTransable {
    @Override
    public List selectByIds(List ids) {
        List list = new ArrayList();
        Teacher t  = null;
        for (Object id : ids) {
            t  = new Teacher();
            t.setTeacherId(id.toString());
            t.setName("老师名字" + id);
            t.setAge(18);
            list.add(t);
        }
        System.out.println("teacher service的findByIds 进来了");
        return list;
    }

    @Override
    public List select() {
        System.out.println("teacher service的select 进来了");
        return selectByIds(Arrays.asList("1","2","3","4"));
    }

    @Override
    public VO selectById(Object primaryValue) {
        System.out.println("teacher service的selectById 进来了" + primaryValue);
        Teacher t  = new Teacher();
        t.setTeacherId(primaryValue.toString());
        t.setName("老师名字" + primaryValue);
        t.setAge(18);
        return t;
    }
}
