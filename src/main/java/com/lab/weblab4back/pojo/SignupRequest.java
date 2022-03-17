package com.lab.weblab4back.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SignupRequest {
    private String username;
    private List<String> roles;
    private String password;
}
