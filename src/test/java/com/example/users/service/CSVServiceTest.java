package com.example.users.service;


import com.example.users.model.User;
import com.example.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.users.common.TestConstants.*;
import static com.example.users.common.TestUtils.getFileBytes;
import static com.example.users.common.TestUtils.mockUsers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CSVServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    CSVService csvService;

    @Test
    public void test_save() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("users", RESOURCE_NAME, CSV_CONTENT_TYPE,getFileBytes());
        when(userRepository.saveAll(Mockito.any())).thenReturn(mockUsers());
        List<User> storeOrders = csvService.save(mockMultipartFile);
        assertEquals(mockUsers().size(),storeOrders.size());
    }

    @Test
    public void test_save_for_null_object() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            csvService.save(null);
        });
        String expectedMessage = "fail to store csv data";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}

