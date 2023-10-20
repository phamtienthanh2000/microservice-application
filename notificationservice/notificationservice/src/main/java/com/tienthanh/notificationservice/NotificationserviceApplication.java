package com.tienthanh.notificationservice;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NotificationserviceApplication {
	private Logger logger =org.slf4j.LoggerFactory.getLogger(NotificationserviceApplication.class);

	@StreamListener(Sink.INPUT) 
	public void consumeMessage(Message message) {
		System.out.println("Message recieved "+message);
		logger.info("Consume Payload: "+"employee id "+message.getEmployeeId()+" "+message.getMessage());
	}

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}
