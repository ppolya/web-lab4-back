package com.lab.weblab4back.repo;


import com.lab.weblab4back.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
   User findByUsername(String username);
   Boolean existsByUsername(String username);
}
