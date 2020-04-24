package com.easipass.EpUtilServer.test;

import com.easipass.EpUtilServer.Main;
import com.easipass.EpUtilServer.entity.Response;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
public class BaseTest {

    /**
     * 断言判断响应
     * */
    public void assertResponse(Response response) {
        assert response.getFlag().equals("T");
    }

}
