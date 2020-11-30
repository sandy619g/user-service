package com.example.users.helper;


import com.example.users.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.example.users.common.TestConstants.*;
import static com.example.users.common.TestUtils.getFileBytes;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CSVHelperTest {

    @Autowired
    CSVHelper csvHelper;


    @Test
    public void when_file_is_not_csv() throws FileNotFoundException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("foo", "foo.txt", TEXT_CONTENT_TYPE,
                "Hello World".getBytes());
        boolean isType = csvHelper.hasCSVFormat(mockMultipartFile);
        assertEquals(false,isType);
    }


    @Test
    public void when_file_is_csv_() throws FileNotFoundException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("foo", "foo.csv", CSV_CONTENT_TYPE,
                "Hello World".getBytes());
        boolean isType = csvHelper.hasCSVFormat(mockMultipartFile);
        assertEquals(true,isType);
    }

    @Test
    public void when_empty_file_is_parsed() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE,
                "".getBytes());
        List<User> users = csvHelper.convert(mockMultipartFile.getInputStream());
        assertEquals(0,users.size());
    }

    @Test
    public void when_csv_file_is_parsed() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE,getFileBytes());
        List<User> users = csvHelper.convert(mockMultipartFile.getInputStream());
        assertNotNull(users);
    }

    @Test
    public void when_null_object_is_parsed() throws IOException {
        MockMultipartFile mockMultipartFile =  new MockMultipartFile("file", RESOURCE_NAME, CSV_CONTENT_TYPE,getFileBytes());
        assertThrows(NullPointerException.class, () -> {
            csvHelper.convert(null);
        });
    }
}
