package com.shiji;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JMain {

    private final static Logger log = LoggerFactory.getLogger(SLF4JMain.class);

    public static void main(String[] args) {
        String name = "abc";
        log.info("----------> name = {}",name);
    }
}
