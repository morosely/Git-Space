package cn.itcast.orm.mapper;

import cn.itcast.orm.entity.DeptEmp;

import java.util.List;

//Mapper接口  不用编写实现类
public interface DeptEmpMapper {

    List<DeptEmp> getEmpTotalByDept();

}
