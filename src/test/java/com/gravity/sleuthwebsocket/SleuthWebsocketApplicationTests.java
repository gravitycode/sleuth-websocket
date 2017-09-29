package com.gravity.sleuthwebsocket;

import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gravity.sleuthsample.WebSocketConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SleuthWebsocketApplicationTests.class, WebSocketConfig.class })
@SpringBootConfiguration
public class SleuthWebsocketApplicationTests {

    private static Log logger = LogFactory.getLog(SleuthWebsocketApplicationTests.class);

    @Test
    public void contextLoads() throws InterruptedException, ExecutionException {

    }

}
