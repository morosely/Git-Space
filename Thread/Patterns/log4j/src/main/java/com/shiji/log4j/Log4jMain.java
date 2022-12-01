package com.shiji.log4j;
import org.apache.log4j.Logger;

public class Log4jMain {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Log4jMain.class);
        logger.info("log4j info --- xxxxx");
        logger.debug("log4j debug --- xxxxx");
        logger.error("log4j error --- xxxxx");

        //java.util.logging.Logger JULLogger = java.util.logging.Logger.getLogger(Log4jMain.class.getName());
        //JULLogger.info("JUL info --- xxxxx");
    }
}
