package com.shiji.transi18n.controller;

import com.fhs.core.trans.anno.TransMethodResult;
import com.fhs.trans.advice.EasyTransResponseBodyAdvice;
import com.fhs.trans.aop.TransMethodResultAop;
import com.fhs.trans.service.impl.TransService;
import com.shiji.transi18n.model.Student;
import com.shiji.transi18n.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class StudentController {


    @Autowired
    private StudentService studentService;
    @Autowired
    private TransService transService;

    /**
     *easy-trans:
     *   #启用全局翻译(拦截所有responseBody进行自动翻译)，如果对于性能要求很高可关闭此配置
     *   is-enable-global: true
     * EasyTransResponseBodyAdvice
     */
    EasyTransResponseBodyAdvice easyTransResponseBodyAdvice;
    @GetMapping("/list")
    public List<Student> students(){
        /*List list = new ArrayList<>();
        for(int i =0 ;i < 2; i++){
            Student student = new Student();
            student.setName("张三-"+i);
            student.setSex(i%2);
            //student.setMiddleSchoolId("1");
            student.setSchoolId(String.valueOf(1));
            student.setId(String.valueOf(i));
            list.add(student);
        }
        return list;*/
        return studentService.list();
    }

    @GetMapping("/one/{id}")
    public Student one(@PathVariable("id")Integer id){
        Student student = studentService.one(id);
        log.info("【one】1.----> {}",student);
        transService.transOne(student);
        log.info("【one】2.----> {}",student);
        return student;
    }

    /**
     * 标记方法结果翻译
     * TransMethodResultAop
     */
    TransMethodResultAop aop;
    @TransMethodResult
    @GetMapping("/two/{id}")
    public Student two(@PathVariable("id")Integer id){
        Student student = studentService.one(id);
        log.info("【two】1.----> {}",student);
        return student;
    }
}
