package com.fhs.test.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.TransDefaultSett;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;
import org.beetl.sql.annotation.entity.AssignID;


@Data
@TableName("user")
@org.beetl.sql.annotation.entity.Table(name="user")
@TransDefaultSett(defaultFields = "userId",isUseCache = true,cacheSeconds = 10)
public class UserMp implements TransPojo {

    @TableId("user_id")
    @AssignID
    private String userId;

    @TableField("name")
    private String name;

    @TableField("age")
    private Integer age;

    @TableField("id_no")
    private String idNo;
}
