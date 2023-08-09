package com.example.myi18n.common.base;


import com.example.myi18n.common.enums.ExceptionEnums;
import com.example.myi18n.common.enums.ResultCode;
import lombok.Data;

/**
 * @name: ResultVO
 * @description: XXX
 * @type: JAVA
 * @since: 2020/11/5 20:33
 * @author: DuanLinPeng
 */
@Data
public class ResultVO<T> {
    /**
     * 状态码，比如1000代表响应成功
     */
    private int code;
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;


    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }




    public ResultVO(String msg) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = msg;
        this.data = null;
    }

    public ResultVO(Integer code, String msg , T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(Integer code, ExceptionEnums msg , T data) {
        this.code = code;
        this.msg = msg.getSign();
        this.data = data;
    }

    public static ResultVO failure(Exception e){
        return new ResultVO(ResultCode.ERROR.getCode(),e.getMessage(),null);
    }
    public static ResultVO failure(String msg){
        return new ResultVO(ResultCode.ERROR.getCode(),msg,null);
    }

    public static ResultVO failure(){
        return new ResultVO(ResultCode.ERROR,null);
    }
    public static ResultVO success(Object data){
        return new ResultVO(ResultCode.SUCCESS,data);
    }

    public static ResultVO builder(Object data){
        return new ResultVO(ResultCode.SUCCESS,data);
    }

    public ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
