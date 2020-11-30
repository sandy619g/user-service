package com.example.users.service;

import com.example.users.helper.CSVHelper;
import com.example.users.model.User;
import com.example.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CSVService {
  @Autowired
  UserRepository repository;

  @Autowired
  CSVHelper csvHelper;

  public List<User> save(MultipartFile file) {
    List<User> userList = new ArrayList<>();
    try {
      log.debug("CSVService : save() to file "+file.getOriginalFilename());
      List<User> users = csvHelper.convert(file.getInputStream());
      userList = repository.saveAll(users);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
    return userList;
  }

}
