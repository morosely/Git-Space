package com.shiji.transi18n.service;

import com.shiji.transi18n.mapper.StudentMapper;
import com.shiji.transi18n.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;
    public List<Student> list(){
        return studentMapper.selectList(null);
    }
}
