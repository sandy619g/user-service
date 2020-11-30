package com.example.users.common;

import com.example.users.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.users.common.TestConstants.RESOURCE_NAME;

public class TestUtils {

    public static byte[] getFileBytes() throws IOException {
        ClassLoader classLoader = new TestUtils().getClass().getClassLoader();
        File fileObj = new File(classLoader.getResource(RESOURCE_NAME).getFile());
        return Files.readAllBytes(fileObj.toPath());
    }
    public static User mockUser(){
        User user = new User();
        user.setId("test");
        user.setName("test");
        user.setLogin("test");
        user.setSalary(123);
        LocalDate dt = LocalDate.parse("2018-11-01");
        user.setStartDate(dt);
        return user;
    }
    public static List<User> mockUsers(){
        List<User> users = new ArrayList<>();
        users.add(mockUser());
        return users;
    }
}
