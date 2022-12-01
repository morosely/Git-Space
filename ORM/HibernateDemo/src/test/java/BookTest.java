import cn.itcast.orm.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;


// 测试类
public class BookTest {

    private  SessionFactory factory;

    @Before
    public void init(){
        //1. 创建一个Configuration对象，解析hibernate的核心配置文件
        Configuration cfg=new Configuration().configure();
        //2. 创建SessinFactory对象，解析映射信息并生成基本的sql
        factory=cfg.buildSessionFactory();
    }

    @Test
    public void testSave(){
        //3. 得到Session对象，该对象具有增删改查的方法
        Session session=factory.openSession();
        //4. 开启事务
        Transaction tx=session.beginTransaction();

        //5. 保存数据
        Book book=new Book();
        book.setName("java从入门到精通");
        book.setAuthor("传智播客");
        book.setPrice(9.9);
        session.save(book);

        //6. 提交事务
        tx.commit();

        //7. 释放资源
        session.close();
    }

    @Test
    public void testGet(){
        //3. 得到Session对象，该对象具有增删改查的方法
        Session session=factory.openSession();

        //4. 通过Session对象进行查询
        Book book=session.get(Book.class,2);
        System.out.println(book);

        //5. 释放资源
        session.close();
    }

    @Test
    public void testDelete(){
        //3. 得到Session对象，该对象具有增删改查的方法
        Session session=factory.openSession();

        //4. 开启事务管理
        Transaction tx=session.beginTransaction();

        //5. 删除数据
        Book book=new Book();
        book.setId(2);
        session.delete(book);

        //6. 提交事务
        tx.commit();;

        //7. 释放资源
        session.close();
    }

}
