package com.shiji.java8.function;

@FunctionalInterface
public interface MyFuncTR<T, R> {
    R getValue(T t1, T t2);
}
