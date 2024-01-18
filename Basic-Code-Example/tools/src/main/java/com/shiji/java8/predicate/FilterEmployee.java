package com.shiji.java8.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class FilterEmployee {

    protected static List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 60, 6666.66),
            new Employee("赵六", 16, 7777.77),
            new Employee("田七", 18, 3333.33)
    );

    //常规遍历集合的方式来查找年龄大于等于30的员工信息
    public List<Employee> filterEmployeesByAge(List<Employee> list){
        List<Employee> employees = new ArrayList<>();
        for(Employee e : list){
            if(e.getAge() >= 30){
                employees.add(e);
            }
        }
        return employees;
    }

    //获取当前公司中员工工资大于或者等于5000的员工信息
    public List<Employee> filterEmployeesBySalary(List<Employee> list){
        List<Employee> employees = new ArrayList<>();
        for(Employee e : list){
            if(e.getSalary() >= 5000){
                employees.add(e);
            }
        }
        return employees;
    }

    //优化方式一(设计模式:策略模式)
    public static List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> myPredicate){
        List<Employee> employees = new ArrayList<>();
        for(Employee e : list){
            if(myPredicate.filter(e)){
                employees.add(e);
            }
        }
        return employees;
    }

    public static void main(String[] args) {
        System.out.println("1.==============（设计模式:策略模式）================");
        List<Employee> employeeList = filterEmployee(employees, new FilterEmployeeByAge());
        for (Employee e : employeeList){
            System.out.println(e);
        }
        System.out.println("2.==============（设计模式:策略模式）================");
        employeeList = filterEmployee(employees, new FilterEmployeeBySalary());
        for (Employee e : employeeList){
            System.out.println(e);
        }

        System.out.println("3.=============（Lambda）=================");
        filterEmployee(employees, (e) -> e.getAge() >= 30).forEach(System.out::println);
        System.out.println("4.=============（Lambda）=================");
        filterEmployee(employees, (e) -> e.getSalary() >= 5000).forEach(System.out::println);

        System.out.println("5.=============（Lambda Stream API）=================");
        employees.stream().filter((e) -> e.getSalary() >= 5000).forEach(System.out::println);
        System.out.println("6.=============（Lambda Stream API）只获取两个员工信息=================");
        employees.stream().filter((e) -> e.getSalary() >= 5000).limit(2).forEach(System.out::println);

    }
}
