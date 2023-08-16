/*
package com.fhs.test.service;

import com.fhs.common.utils.JsonUtils;
import com.fhs.test.pojo.Student;
import com.fhs.trans.fi.LocaleGetter;
import com.fhs.trans.service.impl.DictionaryTransService;
import com.fhs.trans.service.impl.TransService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherServiceTest {

    @Autowired
    private  DictionaryTransService dictionaryTransService;

    @Autowired
    private TransService transService;

    @Autowired
    private StudentService studentService;

    @Test
    public void transOne(){
        Student student = new Student();
        student.setStudentName("张三");
        student.setTeacherId(1);
        student.setEnglishteacherId("2");
        student.setFriendUserId("1,2");
        student.setSex(1);
        student.setElementarySchoolId(2);
        student.setMiddleSchoolId("1");
        System.out.println(JsonUtils.bean2json(student));
        transService.transOne(student);
        System.out.println(JsonUtils.bean2json(student));
    }

    @Test
    public void transMethodResult(){
        //如果要看怎么写的请到studentService去看 只是在方法上加了@TransMethodResult 注解而已
       */
/* System.out.println("翻译单个结果:");
        System.out.println(JsonUtils.bean2json(studentService.getStudent()));*//*

        System.out.println("翻译分页:");
        System.out.println(JsonUtils.bean2json(studentService.pager()));

       */
/* System.out.println("翻译多个结果:");
        System.out.println(JsonUtils.list2json(studentService.getStudents()));*//*

       */
/* System.out.println("翻译httpResult（泛型）多个结果:");
        System.out.println(JsonUtils.bean2json(studentService.gets()));
        System.out.println("翻译httpResult（泛型）单个结果:");
        System.out.println(JsonUtils.bean2json(studentService.get()));*//*

    }


    @Test
    public void transMore(){
        Student student = new Student();
        student.setStudentName("张三");
        student.setTeacherId(1);
        student.setEnglishteacherId("2");
        student.setSex(1);
        student.setElementarySchoolId(1);
        student.setMiddleSchoolId("2");
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        transService.transMore(studentList);
        System.out.println(JsonUtils.list2json(studentList));
    }

    @Before
    public void init(){
        //字典国际化支持
        Map<String,String> transMap = new HashMap<>();
        // 如果不要国际化则是  ransMap.put("0","男");  transMap.put("1","女");
        transMap.put("0_cn","男");
        transMap.put("1_cn","女");
        dictionaryTransService.refreshCache("sex",transMap);
        //开启国际化支持，不要国际化的可以去掉 getLanguageTag 可以在spring里取
        dictionaryTransService.openI18n(new LocaleGetter(){
            @Override
            public String getLanguageTag() {
                return "cn";
            }
        });
    }
}
*/
