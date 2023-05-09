package com.shiji.transi18n.controller;

import com.shiji.transi18n.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @GetMapping("/list")
    public List<Student> students(){
        List list = new ArrayList<>();
        for(int i =0 ;i < 3; i++){
            Student student = new Student();
            student.setName("张三-"+i);
            student.setSex(i%2);
            student.setMiddleSchoolId("1");
            student.setSchoolId(String.valueOf(1));
            student.setId(String.valueOf(i));
            list.add(student);
        }
        return list;
    }

}
