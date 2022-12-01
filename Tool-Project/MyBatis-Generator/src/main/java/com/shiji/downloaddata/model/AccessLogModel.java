package com.shiji.downloaddata.model;

import lombok.Data;

import java.util.Date;

/**
 * 日志
 * access_log
 * @author Administrator
 * @date 2022-12-01 12:07:22
 */
@Data
public class AccessLogModel {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 请求链接
     */
    private String requestUrl;

    /**
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}