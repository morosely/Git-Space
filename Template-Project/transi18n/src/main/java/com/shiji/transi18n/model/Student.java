package com.shiji.transi18n.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//实现TransPojo  接口，代表这个类需要被翻译或者被当作翻译的数据源
public class Student implements TransPojo {
    // 字典翻译 ref为非必填
    @Trans(type = TransType.DICTIONARY,key = "sex")
    private Integer sex;

    @TableId
    private String id;

    private String name;

    //SIMPLE 翻译，用于关联其他的表进行翻译    schoolName 为 School 的一个字段
    //@Trans(type = TransType.SIMPLE,target = School.class,fields = "schoolName")
    private String schoolId;

    //远程翻译，调用其他微服务的数据源进行翻译
    //@Trans(type = TransType.RPC,targetClassName = "com.fhs.test.pojo.School",fields = "schoolName",serviceName = "easyTrans",alias = "middle")
    private String middleSchoolId;

    //枚举翻译，返回文科还是理科给前端
    @Trans(type=TransType.ENUM,key = "desc")
    private StudentType studentType = StudentType.ARTS;

    public static enum StudentType{
        ARTS("文科"),
        SCIENCES("理科");
        private String desc;
        StudentType(String desc){
            this.desc = desc;
        }
    }
}
