import cn.itcast.orm.entity.DeptEmp;
import cn.itcast.orm.mapper.DeptEmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;


public class DeptEmpTest {

    @Test
    public void testGetEmpTotalByDept() throws  Exception{

        //1. 加载核心配置文件
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        InputStream inputStream=Resources.getResourceAsStream("SqlMapConfig.xml");

        //2. 解析核心配置文件并创建SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory=sqlSessionFactoryBuilder.build(inputStream);

        //3. 创建核心对象
        SqlSession sqlSession=sqlSessionFactory.openSession();

        //4. 得到Mapper代理对象
        DeptEmpMapper deptEmpMapper=sqlSession.getMapper(DeptEmpMapper.class);

        //5. 调用自定义的方法实现查询功能
        List<DeptEmp> list= deptEmpMapper.getEmpTotalByDept();
        for(DeptEmp de:list){
            System.out.println(de);
        }

        //6. 关闭sqlSession
        sqlSession.close();
    }

}
