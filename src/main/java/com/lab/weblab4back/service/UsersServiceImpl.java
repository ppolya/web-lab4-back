package com.lab.weblab4back.service;

import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.Role;
import com.lab.weblab4back.model.User;
import com.lab.weblab4back.repo.ResultRepository;
import com.lab.weblab4back.repo.RoleRepository;
import com.lab.weblab4back.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersServiceImpl implements UsersService,
        UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        });
        return user;
    }

    public boolean isUserExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        log.info("Registering user {} and saving in the database", user.getUsername());
        userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} in the database", role.getRole());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        User user = userRepository.findByUsername(username);
        Role roleName = roleRepository.findByRole(role);
        log.info("Adding role {} to the user {}", roleName.getRole(), user.getUsername());
        user.getRoles().add(roleName);
    }


    //most likely this method is not necessary
//    @Override
//    public void addUserToResult(String username, Result result) {
//        User user = userRepository.findByUsername(username);
//        log.info("Matched the user {} with the result {}", user.getUsername(), result);
//        result.setUser(user);
//    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

}
