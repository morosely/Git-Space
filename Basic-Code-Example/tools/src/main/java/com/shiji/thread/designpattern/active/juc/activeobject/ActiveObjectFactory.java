package com.shiji.thread.designpattern.active.juc.activeobject;

public class ActiveObjectFactory {
    public static ActiveObject createActiveObject() {
        return new ActiveObjectImpl();
    }
}
