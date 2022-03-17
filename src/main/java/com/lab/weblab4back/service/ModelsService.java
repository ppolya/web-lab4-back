package com.lab.weblab4back.service;


import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.Role;
import com.lab.weblab4back.model.User;

import java.util.List;

public interface ModelsService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String role);
    void addResultToUser(String username, Result result);
    User getUser(String username);
    List<User> getAllUsers();

    Result saveResult(Result result);
    void addUserToResult(String username, Result result);
    List<Result> getAllResults();
    List<Result> getUserResults(User user);
}
