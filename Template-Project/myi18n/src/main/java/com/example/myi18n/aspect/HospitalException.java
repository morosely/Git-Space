package com.example.myi18n.aspect;

import com.example.myi18n.common.component.I18nTemplateContainer;
import com.example.myi18n.common.base.ResultVO;
import com.example.myi18n.common.base.RunCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * @name: HospitalException
 * @description: 全局异常处理
 * @type: JAVA
 * @since: 2020/11/5 20:24
 * @author: DuanLinPeng
 */
@RestControllerAdvice
@Slf4j
public class HospitalException extends Exception {
    @Resource
    private I18nTemplateContainer i18nTemplateContainer;

    @ExceptionHandler(RunCustomException.class)
    public ResultVO<String> APIExceptionHandler(RuntimeException e) {
        String msg = msgTranslation(e.getMessage());
        e.printStackTrace();
        // 注意哦，这里返回类型是自定义响应体
        return ResultVO.failure(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String msg = msgTranslation(e.getMessage());
        // 注意哦，这里返回类型是自定义响应体
        e.printStackTrace();
        return ResultVO.failure(msg);
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<String> ExceptionDAY(Exception e){
        String msg = msgTranslation(e.getMessage());
        e.printStackTrace();
        return ResultVO.failure(msg);
    }

    private String msgTranslation(String e){
        String msg = i18nTemplateContainer.getValue(e);
        msg = msg == null ? e: msg;
        log.error("异常捕捉到国际化翻译后内容：{}",msg);
        return msg;
    }

}
