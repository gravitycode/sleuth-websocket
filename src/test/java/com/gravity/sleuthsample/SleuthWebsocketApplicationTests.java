package com.gravity.sleuthsample;

import java.util.concurrent.ExecutionException;

import com.gravity.sleuthsample.websocket.client.HelloClient;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.sleuth.instrument.messaging.TraceMessageHeaders;
import org.springframework.cloud.sleuth.util.ArrayListSpanAccumulator;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SleuthWebsocketApplicationTests {

	@Autowired
	private ArrayListSpanAccumulator accumulator;

	@Test
	public void contextLoads() throws InterruptedException, ExecutionException {

		HelloClient helloClient = new HelloClient();

		helloClient.connect();
		helloClient.subscribeGreetings();
		helloClient.sendHello();
		helloClient.waitFor();

		assertThat(helloClient.getMessage()).isNotNull();
		System.err.println(helloClient.getMessage());
		assertThat(helloClient.getMessage().getHeaders()).containsKey(TraceMessageHeaders.SPAN_ID_NAME);
		System.err.println(accumulator.getSpans());
	}

}
