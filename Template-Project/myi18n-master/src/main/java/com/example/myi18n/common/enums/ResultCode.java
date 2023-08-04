package com.example.myi18n.common.enums;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAILED(403, "响应失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    ERROR(500, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
