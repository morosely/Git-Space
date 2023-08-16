package com.fhs.test.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import lombok.Data;


@Data
public class SimpleVO implements VO {

    @TableId
    @Trans(type = TransType.AUTO_TRANS, key = "usermp", target = UserMp.class, fields = "name", alias = "friendUser")
    private String friendUserId;
}
