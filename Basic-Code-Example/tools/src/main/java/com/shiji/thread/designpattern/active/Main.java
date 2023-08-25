package com.shiji.thread.designpattern.active;

import com.shiji.thread.designpattern.active.activeobject.ActiveObject;
import com.shiji.thread.designpattern.active.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice", activeObject).start();
        new MakerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }
}
