package com.fhs.test.pojo;

import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.VO;
import lombok.Data;

@Data
public class RoleVO implements VO {
    @Trans(type= TransType.AUTO_TRANS,key = "role")
    private String pid;
}
