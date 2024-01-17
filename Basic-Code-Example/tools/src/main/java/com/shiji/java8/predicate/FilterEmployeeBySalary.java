package com.shiji.java8.predicate;

public class FilterEmployeeBySalary implements MyPredicate<Employee>{
    @Override
    public boolean filter(Employee employee) {
        return employee.getSalary() >= 5000;
    }
}