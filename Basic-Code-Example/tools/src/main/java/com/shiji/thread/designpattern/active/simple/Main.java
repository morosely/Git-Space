package com.shiji.thread.designpattern.active.simple;

import com.shiji.thread.designpattern.active.simple.activeobject.ActiveObject;
import com.shiji.thread.designpattern.active.simple.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice", activeObject).start();
        new MakerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }
}
