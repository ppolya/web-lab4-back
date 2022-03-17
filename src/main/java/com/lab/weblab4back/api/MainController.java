package com.lab.weblab4back.api;

import com.lab.weblab4back.pojo.*;
import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.Role;
import com.lab.weblab4back.model.User;
import com.lab.weblab4back.security.jwt.JwtUtils;
import com.lab.weblab4back.service.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MainController {
    private final UsersServiceImpl modelsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody UserDTO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        log.info("User {} try to login with password {}", username, password);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        log.info("User {} jwt token: {}", loginRequest.getUsername(), jwt);
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
       if(modelsService.isUserExist(signupRequest.getUsername())) {
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error: Username is exist"));
       }
        User user = new User(signupRequest.getUsername(), passwordEncoder.encode(signupRequest.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(null, "ROLE_USER"));
        user.setRoles(roles);
        modelsService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(modelsService.getAllUsers());
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(modelsService.saveRole(role));
    }

//    @PostMapping("/user/addRes")
//    public ResponseEntity<Result> addResult(@RequestBody String username, @RequestBody ResDTO res) {
//        User user = modelsService.getUser(username);
//        Result result = new Result(res.getX(), res.getY(), res.getY());
//        result.setUser(user);
//        modelsService.addResultToUser(username, result);
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping("/admin/addRole")
//    public ResponseEntity<?> addRoleToUser(@RequestBody UserDTO userDTO) {
//        modelsService.addRoleToUser(userDTO.getUsername(), userDTO.getRole());
//        return ResponseEntity.ok().build();
//    }

}
