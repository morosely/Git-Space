package com.shiji;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackMain {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogBackMain.class);
        String msg = " 【slf4j info】";
        logger.info("this is a info message : {}", msg);
    }
}
