package com.example.users.helper;

import com.example.users.mapper.UserMapper;
import com.example.users.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CSVHelper {
  public static String TYPE = "text/csv";

  public boolean hasCSVFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  public List<User> convert(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      List<User> users = new ArrayList<User>();
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      for (CSVRecord csvRecord : csvRecords) {
        User user = new UserMapper().convertToUser(csvRecord);
        users.add(user);
      }
      return users;
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }


}
