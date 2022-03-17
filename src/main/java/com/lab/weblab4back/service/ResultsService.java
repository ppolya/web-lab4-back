package com.lab.weblab4back.service;

import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.User;
import com.lab.weblab4back.pojo.ResDTO;

import java.util.List;

public interface ResultsService {
    Result saveResult(ResDTO result, User user);
    List<Result> getAllResults();
    List<Result> getUserResults(User user);
    void clearResults(User user);
}
