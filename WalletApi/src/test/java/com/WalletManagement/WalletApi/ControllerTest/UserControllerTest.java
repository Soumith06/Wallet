package com.WalletManagement.WalletApi.ControllerTest;

import com.WalletManagement.WalletApi.controller.UserController;
import com.WalletManagement.WalletApi.exceptions.NotFoundException;
import com.WalletManagement.WalletApi.model.User;
import com.WalletManagement.WalletApi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    User user1=new User(0L,"sou06","sou","00","d@gmail.com","false");
    User user2=new User(1L,"soum06","soum","000","g@gmail.com","false");

    @Test
    @DisplayName("Get All Users success")
    public void getAllUsersTest() throws Exception {
        List<User> userList= Stream.of(user1,user2).collect(Collectors.toList());

        Mockito.when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].userName",Matchers.is(user1.getUserName())));

    }

    @Test
    @DisplayName("usersNotFoundException in Get All Users success")
    public void usersNotFoundExceptionTest() throws Exception{
        Mockito.when(userService.getAllUsers()).thenThrow(new NotFoundException("Users List Empty"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("Users List Empty"));

    }

    @Test
    @DisplayName("Get User by Id success")
    public void getUserByIdTest() throws Exception{
        Mockito.when(userService.getUserById(user1.getUserId())).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/"+user1.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.notNullValue()))
                .andExpect(jsonPath("$.userName",Matchers.is(user1.getUserName())));
    }

    @Test
    @DisplayName("userNotFoundException in Get User by Id")
    public void userNotFoundExceptionOfGetUserByIdTest() throws Exception{
        Mockito.when(userService.getUserById(user1.getUserId())).thenThrow(new NotFoundException("User Doesn't Exist"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/"+user1.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("User Doesn't Exist"));

    }

    @Test
    @DisplayName("Delete User by Id success")
    public void deleteUserByIdTest() throws Exception{
        Mockito.doNothing().when(userService).deleteUserById(user1.getUserId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/"+user1.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string("User Deleted"));

    }

    @Test
    @DisplayName("userNotFoundException in Delete User by Id")
    public void userNotFoundExceptionOfDeleteUserByIdTest() throws Exception{
        Mockito.doThrow(new NotFoundException("User Doesn't Exist")).when(userService).deleteUserById(user1.getUserId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/"+user1.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string("User Doesn't Exist"));

    }

    @Test
    @DisplayName("Create User Success")
    public void createUserTest() throws Exception{
        Mockito.when(userService.addUser(user1)).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(user1))
                )
                .andExpect(status().isCreated());

        System.out.println(this.objectMapper.writeValueAsString(user1));
    }
}
