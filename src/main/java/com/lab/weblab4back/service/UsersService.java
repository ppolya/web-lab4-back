package com.lab.weblab4back.service;


import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.Role;
import com.lab.weblab4back.model.User;

import java.util.List;

public interface UsersService {
    void saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String role);
    User getUser(String username);
    List<User> getAllUsers();

}
