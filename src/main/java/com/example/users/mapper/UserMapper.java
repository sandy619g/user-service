package com.example.users.mapper;

import com.example.users.model.User;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class UserMapper {

    public static  final String DATE_FORMAT = "yyyy-MM-dd";
    public User convertToUser(CSVRecord csvRecord){
        User user = new User();
        user.setId(csvRecord.get("id"));
        user.setLogin(csvRecord.get("login"));
        user.setName(csvRecord.get("name"));
        user.setSalary(Double.valueOf(csvRecord.get("salary")));
        user.setStartDate(convertStringToDate(csvRecord.get("startDate")));
        return user;
    }

    private LocalDate convertStringToDate(String dateStr){
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
        return date;
    }

}
