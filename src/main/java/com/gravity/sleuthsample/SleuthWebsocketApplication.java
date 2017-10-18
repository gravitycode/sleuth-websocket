package com.gravity.sleuthsample;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.util.ArrayListSpanAccumulator;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SleuthWebsocketApplication {

	
	@Bean
	public ArrayListSpanAccumulator accumulator() {
		return new ArrayListSpanAccumulator();
	}

	@Bean
	public Sampler defaultTraceSampler() {
		return new AlwaysSampler();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(SleuthWebsocketApplication.class, args);
	}
}
