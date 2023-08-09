package com.example.myi18n.common.base;

import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected HttpSession session;

}
