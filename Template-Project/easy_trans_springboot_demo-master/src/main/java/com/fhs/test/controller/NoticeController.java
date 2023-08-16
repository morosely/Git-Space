/*
package com.fhs.test.controller;

import com.fhs.test.pojo.HttpResult;
import com.fhs.test.pojo.Notice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("notice")
public class NoticeController {

    @GetMapping("one")
    public Notice one(){
        Notice notice = new Notice();
        notice.setNoticeId("1");
        notice.setUserId("1");
        return notice;
    }

    @GetMapping("many")
    public List<Notice> many(){
        return Arrays.asList(one());
    }
}
*/
