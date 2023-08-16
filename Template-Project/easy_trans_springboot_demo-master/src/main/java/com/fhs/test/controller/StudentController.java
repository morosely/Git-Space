package com.fhs.test.controller;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.fhs.cache.service.TransCacheManager;
import com.fhs.core.trans.anno.IgnoreTrans;
import com.fhs.core.trans.anno.TransCacheType;
import com.fhs.core.trans.anno.TransMethodResult;
import com.fhs.core.trans.anno.TransSett;
import com.fhs.test.pojo.*;
import com.fhs.trans.fi.LocaleGetter;
import com.fhs.trans.service.impl.DictionaryTransService;
import com.fhs.trans.service.impl.RpcTransService;
import com.fhs.trans.service.impl.SimpleTransService;
import com.fhs.trans.service.impl.TransService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StudentController implements InitializingBean {

    @Autowired
    private TransCacheManager transCacheManager;

    @Autowired
    private TransService transService;


    @GetMapping("/one")
    @TransMethodResult
    public HttpResult<Student> student(){
        Student stu = info();
        stu.setUserId1("1");
        stu.setUserId2("2");
      /*  stu.setSubStu(info());*/
        return new HttpResult(stu);
    }
    @GetMapping("/map")
    public Map<String,Object> map(){
        Map<String,Object> map = new HashMap<>();
        map.put("data",info());
        return map;
    }
    @GetMapping("/clear")
    public HttpResult<Boolean> clearCache(){
        transCacheManager.clearCache(UserMp.class,"1");
        transCacheManager.clearCache(School.class,"1");
        transCacheManager.clearCache(School.class,"2");
       /* transCacheManager.setRpcTransCache("com.fhs.test.pojo.School", SimpleTransService
                .TransCacheSett.builder().maxCache(100).cacheSeconds(20).build());*/

        Map<String,String> transMap = new HashMap<>();
        // 如果不要国际化则是  ransMap.put("0","男");  transMap.put("1","女");
       /* transMap.put("0","男");
        transMap.put("1","女");
        dictionaryTransService.refreshCacheAndNoticeOtherService("sex",transMap);*/
        dictionaryTransService.refreshDictItem("sex","1","gril");
        return new HttpResult(true);
    }

    @GetMapping("/info")
    @TransMethodResult
    public Student info(){
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setStudentName("张三");
        student.setTeacherId(1);
        student.setEnglishteacherId("2");
        student.setFriendUserIdNo("371481199201011111");
        student.setSex(1);
        List<Integer> sexs = new ArrayList<>();
        sexs.add(0);
        sexs.add(1);
        student.setSexs(sexs);
        student.setElementarySchoolId(2);
        student.setMiddleSchoolId("2");/*
        TableInfo tableInfo = null;
        tableInfo.getConfiguration();*/
        return student;
    }

    @GetMapping("/pager")
    public PageList<Student> pager(){
        return new PageList(students());
    }

    @GetMapping("/list")
    public List<Student> students(){
        Student student = new Student();
        student.setStudentName("张三");
        student.setTeacherId(1);
        student.setEnglishteacherId("2");
        student.setFriendUserIdNo("371481199201011111,371481199201011112");
        student.setSex(2);
        // student.setElementarySchoolId(2);
        student.setMiddleSchoolId("1");
        List list = new ArrayList<>();
       // return Arrays.asList(null);
        list.add(student);
        return list;
        //return new ArrayList<>();
    }

    @GetMapping("/advance")
    public HttpResult<Student> advance(){
        //最外层
        HttpResult big = new HttpResult();
        big.setDatas(students());
        big.setOneData(info());
        List<HttpResult> resultList = new ArrayList<>();
        big.setData(resultList);
        HttpResult litte = new HttpResult();
        litte.setDatas(students());
        litte.setOneData(info());
        resultList.add(litte);
        return big;
    }

    @GetMapping("/simple")
    public SimpleVO simple(){
        SimpleVO simpleVO = new SimpleVO();
        simpleVO.setFriendUserId("1,2");
        return simpleVO;
    }


    @GetMapping("/role")
    public RoleVO role(){
        RoleVO roleVO = new RoleVO();
        roleVO.setPid("1");
        return roleVO;
    }

    @Autowired
    private DictionaryTransService dictionaryTransService;

    @Autowired
    private RpcTransService rpcTransService;

    @Override
    public void afterPropertiesSet() throws Exception {
        //字典国际化支持
        Map<String,String> transMap = new HashMap<>();
        // 如果不要国际化则是  ransMap.put("0","男");  transMap.put("1","女");
        transMap.put("0","男");
        transMap.put("1","女");
        transMap.put("2","TS");
        dictionaryTransService.makeUseRedis();
        dictionaryTransService.refreshCache("sex",transMap);
        transMap = new HashMap<>();
        transMap.put("true","禁用");
        transMap.put("false","启用");
        dictionaryTransService.refreshCache("disable",transMap);
        transMap = new HashMap<>();
        // 如果不要国际化则是  ransMap.put("0","男");  transMap.put("1","女");
        transMap.put("610100","西安");
        dictionaryTransService.refreshCache("address",transMap);

        //配置contextpath
       // rpcTransService.addContextPath("easyTrans","/api");
    }
}
