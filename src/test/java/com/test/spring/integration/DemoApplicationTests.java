package com.test.spring.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	@Qualifier("channelError")
	private MessageChannel errorChannel;

	@Autowired
	@Qualifier("channelIgnoreError")
	private MessageChannel ignoreErrorChannel;

	@Autowired
	private QueueChannel testChannel;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testErrorChannel() {
		Message<String> message = MessageBuilder.withPayload("TEST PAYLOAD STRING").build();

		try {
			errorChannel.send(message);
		} catch (Exception e) {
			System.out.println("Error Here!");
		}

		Message<?> result = testChannel.receive(100);

		Assert.assertEquals(null, result);
	}

	@Test
	public void testIgnoreErrorChannel() {
		Message<String> message = MessageBuilder.withPayload("TEST PAYLOAD STRING").build();

		try {
			ignoreErrorChannel.send(message);
		} catch (Exception e) {
			System.out.println("There should be no error here!");
		}

		Message<?> result = testChannel.receive(100);

		Assert.assertEquals(message.getPayload(), result.getPayload());
	}
}
