package com.easipass.util.config;

import java.io.File;

public final class BaseConfig {

    public static final String PROJECT_NAME = "ep-util";

    public static final String ROOT_PATH = System.getProperty("user.dir");

    public static final String CACHE_PATH = System.getProperty("user.home") + "/." + PROJECT_NAME;

    public static final File DATABASE_FILE = new File(CACHE_PATH, "database.accdb");

}