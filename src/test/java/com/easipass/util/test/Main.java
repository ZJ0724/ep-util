package com.easipass.util.test;

import com.easipass.util.entity.cusresult.CustomsDeclarationCusResult;
import com.easipass.util.service.ConfigService;
import com.easipass.util.service.CusResultService;
import com.easipass.util.service.impl.ConfigServiceImpl;
import com.easipass.util.service.impl.CusResultServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {com.easipass.util.Main.class})
public final class Main {

    @Resource
    private CusResultService cusResultService;

    @Test
    public void test1() {

    }

    public static void main(String[] args) {}

}