package com.example.users.controller;

import com.example.users.common.ResponseMessage;
import com.example.users.helper.CSVHelper;
import com.example.users.model.User;
import com.example.users.service.CSVService;
import com.example.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    CSVHelper csvHelper;

    @Autowired
    CSVService csvService;

    @PostMapping("/users/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        log.debug("call to upload file "+file.getOriginalFilename());
        if (csvHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                log.error(e.getMessage());
                message = "Could not upload the file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        Optional<User> user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user){
            User userNew = null;
            userNew = userService.createUser(user);
        if(ObjectUtils.isEmpty(userNew)){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userNew,HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody User employee){
        User user = userService.updateUser(employee);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
