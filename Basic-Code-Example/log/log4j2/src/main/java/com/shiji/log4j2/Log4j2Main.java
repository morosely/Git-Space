package com.shiji.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Main {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Log4j2Main.class);
        logger.info("info Msg");
        String name = "Hello World!";
        logger.info("info Msg : {}",name);
    }
}
