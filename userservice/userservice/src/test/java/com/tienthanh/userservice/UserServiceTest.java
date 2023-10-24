package com.tienthanh.userservice;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.tienthanh.userservice.data.User;
import com.tienthanh.userservice.data.UserRepository;
import com.tienthanh.userservice.model.UserDTO;
import com.tienthanh.userservice.service.UserService;

@ExtendWith(SpringExtension.class)

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
	
    @InjectMocks
    private UserService userService;
    private User user;
    private UserDTO userDTO;
    

    @BeforeEach
    public void setUp(){
        user = new User(1L,"dev@gmail.com","123456","employeeID");
        userDTO = new UserDTO(1L,"dev@gmail.com","123456","employeeID","token","refresh");
        ReflectionTestUtils.setField(userService,"userRepository",userRepository);
//        ReflectionTestUtils.setField(userService,"passwordEncoder",passwordEncoder);
    }
    
    @Test
    void loginWithUserNotNullAndMatchPassword() {
    	when(userRepository.findByUsername(anyString())).thenReturn(user);
    	when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    	Assertions.assertNotNull(userService.login(user.getUsername(),user.getPassword()));
    }
    
    @Test
    void loginWithUserNotNullAndPasswordNotMatch() {
    	when(userRepository.findByUsername(anyString())).thenReturn(user);
    	when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
    	Assertions.assertNotNull(userService.login(user.getUsername(), user.getPassword()));
    }
    
    @Test
    void loginWithNullUser() {
    	when(userRepository.findByUsername(anyString())).thenReturn(null);
    	assertNull(userService.login(user.getUsername(), user.getPassword()).getId());

    }
    
    @Test
    void getAllUser() {
    	ArrayList<User> users = new ArrayList<>();
    	users.add(user);
    	when(userRepository.findAll()).thenReturn(users);
    	Assertions.assertNotNull(userService.getAllUser());
    }
    
    @Test
    void saveUser() {
    	when(passwordEncoder.encode(anyString())).thenReturn("123");
    	user.setPassword("123");
    	when(userRepository.save(any())).thenReturn(user);
    	
    	Assertions.assertEquals("123",userService.saveUser(userDTO).getPassword());
    	
    }
    
    
    

}
