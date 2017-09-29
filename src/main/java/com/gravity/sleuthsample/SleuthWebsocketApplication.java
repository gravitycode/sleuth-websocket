package com.gravity.sleuthsample;

import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;

import com.gravity.sleuthsample.websocket.client.HelloClient;

@SpringBootApplication
public class SleuthWebsocketApplication {
    
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(SleuthWebsocketApplication.class, args);
	}
}
