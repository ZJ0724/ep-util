package com.easipass.EpUtilServer.entity;

import org.apache.log4j.Logger;

public class Log {

    private final static Logger LOGGER = Logger.getLogger(Log.class);

    /**
     * info
     * */
    public static void info(String message) {
        LOGGER.info(message);
    }

}
