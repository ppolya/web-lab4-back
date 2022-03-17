package com.lab.weblab4back.repo;

import com.lab.weblab4back.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByRole(String role);
}
