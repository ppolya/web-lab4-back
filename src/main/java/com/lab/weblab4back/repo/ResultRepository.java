package com.lab.weblab4back.repo;

import com.lab.weblab4back.model.Result;
import com.lab.weblab4back.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends PagingAndSortingRepository<Result, Long> {
    List<Result> findByUser(User user);
    void deleteResultsByUser(User user);
}
