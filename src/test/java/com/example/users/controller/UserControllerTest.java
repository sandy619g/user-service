package com.example.users.controller;

import com.example.users.helper.CSVHelper;
import com.example.users.model.User;
import com.example.users.service.CSVService;
import com.example.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static com.example.users.common.TestConstants.*;
import static com.example.users.common.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    CSVService csvService;

    @MockBean
    CSVHelper csvHelper;
    @Test
    @DisplayName("Should fetch all users")
    void test_getAllUsers() throws Exception {
        List<User> users = mockUsers();
        when(userService.getAllUsers()).thenReturn(users);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users").contentType("application/json")).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());
        ObjectMapper objectMapper = ObjectMapperSetup();
        List<User> u = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User[].class));
        Assertions.assertEquals(users.get(0).getName(),u.get(0).getName());
    }

    @Test
    @DisplayName("Should return 400 if passed user=null")
    void test_createUserWithNull() throws Exception {
        User user = null;
        when(userService.createUser(user)).thenReturn(user);
        ObjectMapper objectMapper = ObjectMapperSetup();
        String inputJson =  objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType("application/json").content(inputJson)).andReturn();
        Assertions.assertEquals(400,mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Should be able to create a User")
    void test_createUser() throws Exception {
        List<User> users = mockUsers();
        User user = users.get(0);
        when(userService.createUser(user)).thenReturn(user);
        ObjectMapper objectMapper = ObjectMapperSetup();
        String inputJson =  objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1//users").contentType("application/json").content(inputJson)).andReturn();
        Assertions.assertEquals(201,mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Should be able to get the particular record")
    void test_getUser() throws Exception {
        User user = mockUsers().get(0);
        when(userService.getUser("test")).thenReturn(Optional.ofNullable(user));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1").contentType("application/json")).andReturn();
        Assertions.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Should be able to update user")
    void test_updateUser() throws Exception{
        User user = mockUser();
        ObjectMapper objectMapper = ObjectMapperSetup();
        String inputJson = objectMapper.writeValueAsString(user);
        when(userService.updateUser(user)).thenReturn(user);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(USERS_API_URL).contentType("application/json").content(inputJson)).andReturn();
        Assertions.assertEquals(202,mvcResult.getResponse().getStatus());
        ObjectMapper newObjectMapper = ObjectMapperSetup();
        User u = newObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assertions.assertEquals(user.getName(),u.getName());
    }

    @Test
    @DisplayName("Should be able to delete the user")
    void test_deleteUser() throws Exception {
        User user = mockUsers().get(0);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(USERS_API_URL+"/1").contentType("application/json")).andReturn();
        Assertions.assertEquals(204,mvcResult.getResponse().getStatus());
    }


    @Test
    public void deleteApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(USERS_API_URL+"/1"))
                .andExpect(status().isNoContent());
    }

    private ObjectMapper ObjectMapperSetup(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Test
    public void when_invalid_file_is_uploaded() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file",RESOURCE_NAME, TEXT_CONTENT_TYPE, getFileBytes());
        MvcResult mockMvcResult = mockMvc.perform(multipart(USERS_API_URL+"/upload").file(multipartFile)).andReturn();
        assertEquals(400,mockMvcResult.getResponse().getStatus());
    }

    @Test
    public void when_csv_file_is_uploaded() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE, getFileBytes());
        when(csvHelper.hasCSVFormat(multipartFile)).thenReturn(true);
        MvcResult mockMvcResult = mockMvc.perform(multipart(USERS_API_URL+"/upload").file(multipartFile)).andReturn();
        assertEquals(200,mockMvcResult.getResponse().getStatus());
    }


}
