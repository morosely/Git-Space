package com.shiji.transi18n.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;


@Data
public class School implements TransPojo {

    @TableId
    private Integer id;

    @TableField("school_name")
    private String schoolName;

    @TableField("remark")
    private String schoolRemark;

    @TableField("address")
    private String address;
}
