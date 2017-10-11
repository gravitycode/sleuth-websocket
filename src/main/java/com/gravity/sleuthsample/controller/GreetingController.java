package com.gravity.sleuthsample.controller;

import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.sleuth.Span;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gravity.sleuthsample.pojo.Greeting;
import com.gravity.sleuthsample.pojo.HelloMessage;
import com.gravity.sleuthsample.websocket.client.HelloClient;

@Controller
public class GreetingController {

    private static Log logger = LogFactory.getLog(GreetingController.class);
    
    @ResponseBody
    @RequestMapping(path="/trace", method=RequestMethod.GET)
    public String traceTest() {
        return "trace!";
    }
    
    @ResponseBody
    @RequestMapping(path="/send", method=RequestMethod.GET)
    public String launch() throws InterruptedException, ExecutionException {
        HelloClient helloClient = new HelloClient();

        ListenableFuture<StompSession> f = helloClient.connect();
        StompSession stompSession = f.get();

        logger.info("Subscribing to greeting topic using session " + stompSession);
        helloClient.subscribeGreetings(stompSession);

        logger.info("Sending hello message" + stompSession);
        helloClient.sendHello(stompSession);
        
        return "message sent!";
    }
    
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println("Sending Greeting");
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}