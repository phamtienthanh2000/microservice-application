package com.tienthanh.userservice.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tienthanh.userservice.data.User;
import com.tienthanh.userservice.data.UserRepository;
import com.tienthanh.userservice.model.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private PasswordEncoder passwordEncoder;
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public UserDTO saveUser(UserDTO userDTO) {
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user = UserDTO.dtoToEntity(userDTO);
    	User user2 = userRepository.save(user);

		return UserDTO.entityToDTO(user2);
	}
	public UserDTO login(String username, String password) {
		// dang mock co password la 123
		User user = userRepository.findByUsername(username);
		UserDTO dto = new UserDTO();

		if(user !=null) {
			BeanUtils.copyProperties(user, dto);
			// gia su thang passwordEncoder dung
			if(passwordEncoder.matches(password, dto.getPassword())) {
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				String accessToken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
						.sign(algorithm);
				String refreshtoken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
						.sign(algorithm);
				dto.setToken(accessToken);
				dto.setRefreshtoken(refreshtoken);
			}
		}
		return dto;
	}
}
