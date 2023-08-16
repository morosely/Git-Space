package com.fhs.test.pojo;

import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;

@Data
public class Role implements TransPojo {

    private String id;
    private String name;
}
