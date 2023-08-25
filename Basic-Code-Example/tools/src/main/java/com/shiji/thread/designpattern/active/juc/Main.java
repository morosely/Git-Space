package com.shiji.thread.designpattern.active.juc;

import com.shiji.thread.designpattern.active.juc.activeobject.ActiveObject;
import com.shiji.thread.designpattern.active.juc.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        try {
            new MakerClientThread("Alice", activeObject).start();
            new MakerClientThread("Bobby", activeObject).start();
            new DisplayClientThread("Chris", activeObject).start();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        } finally {
            System.out.println("*** shutdown ***");
            activeObject.shutdown();
        }
    }
}
