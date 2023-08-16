package com.fhs.test.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;



@Data
public class Notice implements TransPojo {
    @TableId
    private String noticeId;

    @Trans(type= TransType.SIMPLE,target = UserMp.class,fields = "name",uniqueField = "userId")
    private String userId;
}
