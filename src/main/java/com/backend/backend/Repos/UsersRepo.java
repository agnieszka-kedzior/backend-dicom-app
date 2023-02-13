package com.backend.backend.Repos;

import com.backend.backend.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepo extends CrudRepository<Users, Long> {

    Users findUsersByUserName(String name);
    Users findUsersByUserId(Integer id);
    Users findUsersByUserFullName(String fullName);
}
