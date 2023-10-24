package com.tienthanh.userservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tienthanh.userservice.data.User;
import com.tienthanh.userservice.data.UserRepository;
import com.tienthanh.userservice.model.UserDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "application.properties")
public class IntegrationTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTest.class);


	@LocalServerPort
	private int port;

	// tool for call rest API
	private static RestTemplate restTemplate;

	@MockBean
	UserRepository userRepository;

	@MockBean
	PasswordEncoder passwordEncoder;
	
	private User user;

	Gson gson = new Gson();

	private String baseUrl = "http://localhost";

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		user = new User(1L, "dev@gmail.com", "123456", "employeeID");
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/users");
		HttpHeaders headers = new HttpHeaders();
		ArrayList<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(mediaTypes);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

	}

	@Test
	void getAllUser() {
		List<User> users = new ArrayList<>();
		users.add(user);
		when(userRepository.findAll()).thenReturn(users);
		ResponseEntity<List> response = restTemplate.getForEntity(baseUrl.concat("/listUser"), List.class);
		System.out.println(gson.toJson(response.getBody()));
		System.out.println(response.getStatusCode());
		Assertions.assertEquals(gson.toJson(users), gson.toJson(response.getBody()));
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	@Test
	void addUser() {
		UserDTO inputDto = new UserDTO(null, "Thanh", "123", "employeeId",null, null);
		UserDTO outputDTO = new UserDTO(1L, "Thanh", "xxxx","employeeId", null, null);
		when(passwordEncoder.encode(anyString())).thenReturn("xxxx");
		User savedUser = new User(1L, "Thanh", "xxxx", "employeeId");
		when(userRepository.save(any(User.class))).thenReturn(savedUser);
		ResponseEntity<User> response = restTemplate.postForEntity(baseUrl.concat("/addUser"), inputDto, User.class);
		System.out.println(gson.toJson(response.getBody()));
		System.out.println(response.getStatusCode());
		Assertions.assertEquals(gson.toJson(outputDTO), gson.toJson(response.getBody()));
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void loginSuccess() {
		UserDTO inputDto = new UserDTO(null, "Thanh", "123", "employeeId", null, null);

		when(userRepository.findByUsername(anyString())).thenReturn(user);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		ResponseEntity<UserDTO> response = restTemplate.postForEntity(baseUrl.concat("/login"), inputDto, UserDTO.class);
		assertNotNull(response.getBody().getToken());
		assertNotNull(response.getBody().getRefreshtoken());

	}

}
