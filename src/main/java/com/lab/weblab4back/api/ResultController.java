package com.lab.weblab4back.api;

import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.User;
import com.lab.weblab4back.pojo.MessageResponse;
import com.lab.weblab4back.pojo.ResDTO;
import com.lab.weblab4back.repo.UserRepository;
import com.lab.weblab4back.service.ResultsService;
import com.lab.weblab4back.service.UsersService;
import com.lab.weblab4back.service.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ResultController {
    private final ResultsService resultsService;
    private final UsersServiceImpl usersService;

    @PostMapping()
    public ResponseEntity<?> addResult(@AuthenticationPrincipal User user, @RequestBody ResDTO res) {
        return ResponseEntity.ok(resultsService.saveResult(res, user));
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkWork(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new MessageResponse("you have got access to this controller"));
    }

    @GetMapping
    public ResponseEntity<?> getUserEntries(@AuthenticationPrincipal User user) {
        User my_user = (User) usersService.loadUserByUsername(user.getUsername());
        return ResponseEntity.ok(resultsService.getUserResults(my_user));
    }

//    @GetMapping
//    public @ResponseBody Iterable<?> getAllResults(@AuthenticationPrincipal User user) {
//        return user.getResultList();
//    }

//    @GetMapping
//    public ResponseEntity<?> getUserResults(@AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(resultsService.getUserResults(user));
//    }


    @DeleteMapping
    public ResponseEntity<?> clear(@AuthenticationPrincipal User user) {
        resultsService.clearResults(user);
        return ResponseEntity.ok(new MessageResponse("clear all!"));
    }

}
