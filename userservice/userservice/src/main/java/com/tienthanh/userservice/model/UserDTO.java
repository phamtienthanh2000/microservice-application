package com.tienthanh.userservice.model;

import com.tienthanh.userservice.data.User;

public class UserDTO {
	private Long id;
	private String username;
	private String password;
	private String employeeId;
	private String token;
	private String refreshtoken;

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDTO(Long id, String username, String password, String employeeId, String token, String refreshtoken) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.employeeId = employeeId;
		this.token = token;
		this.refreshtoken = refreshtoken;
	}

	public static UserDTO entityToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmployeeId(user.getEmployeeId());
		return userDTO;
	}

	public static User dtoToEntity(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setEmployeeId(dto.getEmployeeId());
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", employeeId=" + employeeId
				+ ", token=" + token + ", refreshtoken=" + refreshtoken + "]";
	}
	
	

}
