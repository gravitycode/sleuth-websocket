package com.gravity.sleuthsample.websocket.client;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

public class HelloClient {

	private static Logger logger = Logger.getLogger(HelloClient.class);

	private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	private final CountDownLatch latch = new CountDownLatch(1);

	private StompSession session;

	private AtomicReference<Message<String>> message = new AtomicReference<>();

	public void connect() {

		Transport webSocketTransport = new WebSocketTransport(
				new StandardWebSocketClient());
		List<Transport> transports = Collections.singletonList(webSocketTransport);

		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

		String url = "ws://localhost:8080/stomp";
		try {
			this.session = stompClient
					.connect(url, headers, new MyHandler(), "localhost", 8080).get();
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted", e);
		}
		catch (ExecutionException e) {
			throw new IllegalStateException("Cannot connect", e);
		}
	}

	public void waitFor() {
		try {
			latch.await(30000L, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted", e);
		}
	}
	
	public Message<String> getMessage() {
		return message.get();
	}

	public void subscribeGreetings()
			throws ExecutionException, InterruptedException {
		session.subscribe("/topic/greetings", new StompFrameHandler() {

			public Type getPayloadType(StompHeaders stompHeaders) {
				return byte[].class;
			}

			public void handleFrame(StompHeaders stompHeaders, Object o) {
				logger.info("Received greeting " + new String((byte[]) o) + ": "
						+ stompHeaders);
				message.set(MessageBuilder.withPayload(new String((byte[]) o)).copyHeaders(stompHeaders).build());
				latch.countDown();
			}
		});
	}

	public void sendHello() {
		String jsonHello = "{ \"name\" : \"Nick\" }";
		session.send("/app/hello", jsonHello.getBytes());
	}

	private class MyHandler extends StompSessionHandlerAdapter {
		public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
			logger.info("Now connected");
		}
	}
}
