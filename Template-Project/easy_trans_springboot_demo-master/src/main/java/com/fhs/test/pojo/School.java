package com.fhs.test.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;
import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

@Data
@TableName(value = "school",autoResultMap = true)
@Table(name="school")
public class School implements TransPojo {

    @TableId("id")
    @AssignID
    private Integer id;

    @TableField("school_name")
    private String schoolName;

    @TableField("remark")
    private String schoolRemark;

    @TableField("address")
    private String address;
}
