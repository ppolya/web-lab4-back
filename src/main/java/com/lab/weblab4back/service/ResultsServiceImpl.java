package com.lab.weblab4back.service;

import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.User;
import com.lab.weblab4back.pojo.ResDTO;
import com.lab.weblab4back.repo.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResultsServiceImpl implements ResultsService{
    private final ResultRepository resultRepository;

    @Override
    public Result saveResult(ResDTO result, User user) {
        Result res = new Result(result.getX(), result.getY(), result.getR(), user);
        user.getResultList().add(res);
        log.info("Saving result {} in the database", res);
        resultRepository.save(res);
        return res;
    }

    @Override
    public List<Result> getAllResults() {
        return (List<Result>) resultRepository.findAll();
    }

    @Override
    public List<Result> getUserResults(User user) {
        log.info("Get all user {} results", user.getUsername());
        return resultRepository.findByUser(user);
    }

    @Override
    public void clearResults(User user) {
        log.info("Deleting all user {} results", user.getUsername());
        resultRepository.deleteResultsByUser(user);
    }
}
