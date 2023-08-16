package com.fhs.test.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fhs.core.trans.vo.VO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements VO {

    /**
     * 下面2个注解都可以，mybatis plus的注解或者jpa的标准注解都支持
     */
    @TableId
    private String teacherId;

    private String name;

    private Integer age;

}
