package com.fhs.test.service;

import com.fhs.core.trans.anno.TransMethodResult;
import com.fhs.test.pojo.HttpResult;
import com.fhs.test.pojo.PageList;
import com.fhs.test.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @TransMethodResult
    public List<Student> getStudents(){
        List<Student> studentList = new ArrayList<>();
        //this不会走aop 所以步用担心是getStudent()翻译了
        studentList.add(this.getStudent());
        return studentList;
    }

    @TransMethodResult
    public Student getStudent(){
        Student student = new Student();
        student.setStudentName("张三");
        student.setTeacherId(1);
        student.setFriendUserIdNo("371481199201011111");
        //student.setElementarySchoolId(1);
        student.setEnglishteacherId("2");
        student.setSex(1);
        return student;
    }
    @TransMethodResult
    public PageList<Student> pager(){
        PageList result = new PageList();
        result.setRows(getStudents());
        result.setTotal(100);
        return result;
    }

    @TransMethodResult
    public HttpResult<Student> get(){
        return new HttpResult<>(getStudent());
    }

    @TransMethodResult
    public HttpResult<List<Student>> gets(){
        List<Student> students = getStudents();
        HttpResult result = new HttpResult<>();
        result.setDatas(students);
        result.setOneData(getStudents().get(0));
        result.setData(getStudents());
        return result;
    }
}
