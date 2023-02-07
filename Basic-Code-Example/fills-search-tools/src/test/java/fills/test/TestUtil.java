package fills.test;

import java.io.Serializable;


/**
 * @Author ysf
 * @Description   基础测试 //TODO
 * @Param
 * @Date 2020/12/14 17:12
 * @return 
 **/
public class TestUtil {
	
    public static void main(String[] args) throws Exception {
    	User user = new User("fills", "31", "男");
    	Class.forName("fills.test.User");
	}	
    }
   

class User implements Serializable{
	
	private String name ;
	
	private String age;
	
	private String sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(String name,String age,String sex) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.age =  age;
		this.sex = sex;
	}
	
}

