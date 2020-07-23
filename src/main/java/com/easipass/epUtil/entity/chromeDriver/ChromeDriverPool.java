package com.easipass.epUtil.entity.chromeDriver;

import com.easipass.epUtil.entity.ChromeDriver;
import com.easipass.epUtil.entity.Log;
import com.easipass.epUtil.exception.ErrorException;
import java.util.ArrayList;
import java.util.List;

/**
 * 谷歌驱动池
 *
 * @author ZJ
 * */
public final class ChromeDriverPool {

    /**
     * 驱动集合
     * */
    private final List<ChromeDriver> chromeDrivers = new ArrayList<>();

    /**
     * 驱动池最大数量
     * */
    private final int size = 2;

    /**
     * 是否已开启池
     * */
    private boolean isOpen = false;

    /**
     * 单例
     * */
    private static ChromeDriverPool chromeDriverPool;

    /**
     * 日志
     * */
    private static final Log LOG = Log.getLog();

    /**
     * 构造函数
     * */
    private ChromeDriverPool() {}

    /**
     * 获取单例
     * */
    public static ChromeDriverPool getChromeDriverPool() {
        if (chromeDriverPool == null) {
            chromeDriverPool = new ChromeDriverPool();
        }

        return chromeDriverPool;
    }

    /**
     * 开启驱动池
     * */
    public void open() {
        if (isOpen) {
            LOG.info("驱动池已开启");
            return;
        }

        // 检查驱动
        new ChromeDriver(true).close();

        this.isOpen = true;
        LOG.info("开启驱动池");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new ErrorException(e.getMessage());
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    throw new ErrorException(e.getMessage());
                }

                if (this.chromeDrivers.size() == this.size) {
                    continue;
                }

                LOG.info("添加驱动");
                this.chromeDrivers.add(new ChromeDriver(true));
            }
        }).start();
    }

    /**
     * 获取驱动
     *
     * @return 一个驱动
     * */
    public synchronized ChromeDriver get() {
        if (this.chromeDrivers.size() == 0) {
            LOG.info("驱动池无驱动，返回一个新的驱动");
            return new ChromeDriver(true);
        }

        LOG.info("获取一个驱动");
        ChromeDriver chromeDriver = this.chromeDrivers.get(0);
        this.chromeDrivers.remove(0);

        return chromeDriver;
    }

}