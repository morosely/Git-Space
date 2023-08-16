package com.fhs.test.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements TransPojo {

    private Map<String,String> transMap = new HashMap<>();

    private static final Long version = 1L;

    private final Long xx = 1l;

    @TableId
    private String id;

    @Trans(type = TransType.DICTIONARY,  key = "disable",ref="disableName")
    private Boolean disable = true;

    private String disableName;


   /* @Trans(type = TransType.SIMPLE,  target = UserMp.class, alias = "friendUser",uniqueField = "idNo")
    private String friendUserId;
*/
    @Trans(type = TransType.SIMPLE,  target = UserMp.class, alias = "friendUser",uniqueField = "idNo")
    private String friendUserIdNo;

    private String studentName;

    @Trans(type = TransType.SIMPLE,  target = UserMp.class, fields = "name",ref = "user1Name")
    private String userId1;

    private String user1Name;

    @Trans(type = TransType.SIMPLE,  target = UserMp.class, fields = "age",ref = "user2Age")
    private String userId2;

    private Integer user2Age;


    //这个设置为integer 模拟看下trans的时候是否tostring了
    @Trans(type = TransType.AUTO_TRANS, key = "teacher")
    private Integer teacherId;
    /**
     * 自动翻译，不推荐使用，如果项目没使用mybatis plus或者spring data jpa 才考虑用这个
     */
    @Trans(type = TransType.AUTO_TRANS, key = "teacher#english")
    private String englishteacherId;


    //字典翻译，需要将字典表数据注入到dictionaryTransService
    @Trans(type = TransType.DICTIONARY, key = "sex")
    private Integer sex;

    //字典翻译，需要将字典表数据注入到dictionaryTransService
    @Trans(type = TransType.DICTIONARY, key = "sex")
    private List<Integer> sexs;


    //简单翻译，使用其他的表当作数据源，只需要一个注解
    @Trans(type = TransType.SIMPLE, target = School.class, fields = {"schoolName", "schoolRemark","address"},ref = "elementarySchoolVO",refs = {"elementarySchoolName","elementarySchoolRemark","elementarySchoolAddress"},dataSource="meta")
    private Integer elementarySchoolId;

    private String elementarySchoolName;

    private String elementarySchoolRemark;

    @Trans(type=TransType.DICTIONARY,key = "address")
    private String elementarySchoolAddress;

    private School elementarySchoolVO;

    //远程翻译，使用其他的微服务当作数据源进行翻译
    @Trans(type = TransType.RPC, targetClassName = "com.fhs.test.pojo.School", fields = {"schoolName"}, serviceName = "easyTrans", alias = "middle")
    private String middleSchoolId;

    @Trans(type=TransType.ENUM,key = "desc")
    private  StudentType studentType = StudentType.ARTS;

    private Student subStu;

    public static enum StudentType{

        ARTS("文科"),
        SCIENCES("理科");

        private String desc;
        StudentType(String desc){
            this.desc = desc;
        }
    }
}
