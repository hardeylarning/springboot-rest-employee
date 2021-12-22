package com.rocktech.employeewebservice.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("filter1")
    public List<UserDetails> filter1(){
        return UserDetails.getUserDetails();
    }

    @GetMapping("filter2")
    public MappingJacksonValue filter2 () {
        return getMappingJacksonValue("userId");
    }

    @GetMapping("filter3")
    public MappingJacksonValue filter3 () {
        return getMappingJacksonValue("panNumber");
    }

    private MappingJacksonValue getMappingJacksonValue(String val1) {
        List<UserDetails> details = UserDetails.getUserDetails();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.filterOutAllExcept(val1, "userName");
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("UserDetails", simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(details);
        mappingJacksonValue.setFilters(filterProvider);
        return  mappingJacksonValue;
    }
}

@JsonIgnoreProperties(value = {"panNumber"})
class UserDetails {
    private int userId;
    private String userName;
    private String panNumber;

    public static List<UserDetails> getUserDetails() {
        return Arrays.asList(
                new UserDetails(12, "Roqeeb", "AVDFERHG"),
                new UserDetails(13, "Ayinde", "ERHGAVDF"),
                new UserDetails(14, "Adelani", "FERAVDHG")
        );
    }

    public UserDetails() {
    }

    public UserDetails(int userId, String userName, String panNumber) {
        this.userId = userId;
        this.userName = userName;
        this.panNumber = panNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", panNumber='" + panNumber + '\'' +
                '}';
    }
}
