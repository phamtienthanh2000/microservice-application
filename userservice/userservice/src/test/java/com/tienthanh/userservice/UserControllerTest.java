package com.tienthanh.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.tienthanh.userservice.controller.UserController;
import com.tienthanh.userservice.data.User;
import com.tienthanh.userservice.model.UserDTO;
import com.tienthanh.userservice.service.UserService;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {
	
    @Mock
    private UserService userService;
	
    @InjectMocks
    private UserController userController;
    
    
    private User user;
    private UserDTO userDTO;
    
    @BeforeEach
    public void setUp(){
        user = new User(1L,"dev@gmail.com","123456","employeeID");
        userDTO = new UserDTO(1L,"dev@gmail.com","123456","employeeID","token","refresh");
        ReflectionTestUtils.setField(userController,"userService",userService);
    }

    
    @Test
    void getAllUser(){
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getAllUser()).thenReturn(users);
        Assertions.assertEquals(users,userController.getAllUser());
    }
    
    
    @Test
    void addUser(){
       UserDTO dto = new UserDTO();
       dto.setUsername("thanhpham");
       dto.setPassword("123123");
       when(userService.saveUser(dto)).thenReturn(dto);
       Assertions.assertEquals(dto.getUsername(), userController.addUser(dto).getUsername());
    }
    
    @Test
    void login(){
       UserDTO inputDTO = new UserDTO();
       inputDTO.setUsername("thanhpham");
       inputDTO.setPassword("123123");
      
       UserDTO expectDTO = new UserDTO();
       expectDTO.setId(1L);
       expectDTO.setEmployeeId("EmployeeId");
       expectDTO.setPassword("123123");
       expectDTO.setUsername("thanhpham");
       expectDTO.setToken("token1");
       expectDTO.setRefreshtoken("refreshToken1");
       
       
       UserDTO mockDTO = new UserDTO();
       mockDTO.setId(1L);
       mockDTO.setEmployeeId("EmployeeId");
       mockDTO.setPassword("123123");
       mockDTO.setUsername("thanhpham");
       mockDTO.setToken("token1");
       mockDTO.setRefreshtoken("refreshToken1");
       System.out.println("MockDTO "+mockDTO);
       
       when(userService.login(anyString(), anyString())).thenReturn(mockDTO);
       Assertions.assertEquals(expectDTO.getId(),userController.login(inputDTO).getId());
    }
    
    

}
