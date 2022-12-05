package com.shiji;

import java.util.logging.Logger;

public class JULMain {

    static {
        //System.setProperty("java.util.logging.config.file",JULMain.class.getClassLoader().getResource("logging.properties").getPath());
        System.out.println(JULMain.class.getClassLoader().getResource("logging.properties").getPath());
    }

    public static void main(String[] args) {

        Logger log = Logger.getLogger(JULMain.class.getName());
        log.info("JUL info");
        log.fine("JUL fine");
        log.finer("JUL finer");
        log.finest("JUL finest");
    }


}
