package com.listen;
/**
3.1创建一个人类，人具有跑的方法　　（创建一个事件源）

3.2创建一个接口用来监视人的跑　　（事件监听器）

3.3创建一个事件对象，里面用来封装人(Person)这个对象　　（事件对象）

3.4创建一个测试类，用来测试Java监听机制
*/
public class TestListener {

	public static void main(String[] args) {
		Person person = new Person();
		person.registerListener(new MyPersonListener());
		person.run();
	}
}

// 实现监听器接口中的方法
class MyPersonListener implements PersonListener {

	@Override
    public void dorun(Event event) {
		Person person = event.getPerson();//拿到事件源
        System.out.println("人在跑之前执行的动作");
    }

}