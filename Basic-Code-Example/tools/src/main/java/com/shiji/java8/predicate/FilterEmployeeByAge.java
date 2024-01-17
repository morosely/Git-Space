package com.shiji.java8.predicate;

public class FilterEmployeeByAge implements MyPredicate<Employee>{
    @Override
    public boolean filter(Employee employee) {
        return employee.getAge() >= 30;
    }
}
