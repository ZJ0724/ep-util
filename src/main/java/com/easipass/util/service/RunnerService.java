package com.easipass.util.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public interface RunnerService extends ApplicationRunner {

    @Override
    default void run(ApplicationArguments args) {
        gc();
    }

    void gc();

}