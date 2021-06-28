package com.easipass.util.service.impl;

import com.easipass.util.service.RunnerService;
import com.zj0724.common.util.ThreadUtil;
import org.springframework.stereotype.Service;

@Service
public final class RunnerServiceImpl implements RunnerService {

    @Override
    public void gc() {
        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "开启");
                System.gc();
                ThreadUtil.sleep(1000 * 60 * 60);
            }
        }).start();
    }

}