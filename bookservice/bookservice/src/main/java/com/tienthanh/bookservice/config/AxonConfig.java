package com.tienthanh.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;
@Configuration
public class AxonConfig {

	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();

		xStream.allowTypesByWildcard(new String[] { "com.tienthanh.**" });
		return xStream;
	}

}
