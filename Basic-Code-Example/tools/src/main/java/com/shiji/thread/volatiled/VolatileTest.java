package com.shiji.thread.volatiled;

public class VolatileTest {

    public static void main(String[] args) throws InterruptedException {
        Student student = new Student();
        student.start();
        for(int i = 0; i < 10 ; i++){
            Thread.sleep(1000);
            System.out.println("1.主线程输出...student.falg:"+student.isFlag());
            //synchronized (student){
                if (student.isFlag()){
                    System.out.println("2.主线程输出...student.falg:"+student.isFlag());
                }
            //}
        }
    }
}

class Student extends Thread {
    private  boolean flag = false;
    public boolean isFlag( ) {
        return flag;
    }
    @Override
    public void run() {
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag ===> "+flag);
    }
}