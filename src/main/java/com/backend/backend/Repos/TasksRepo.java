package com.backend.backend.Repos;

import com.backend.backend.Entity.Tasks;
import com.backend.backend.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TasksRepo extends CrudRepository<Tasks, Long> {

    Set<Tasks> findByUsers(Users users);
    Set<Tasks> findByTaskCreatedByUserId(Integer id);
    Tasks findByTasksId(Integer id);
    Tasks findByTasksIdAndUsers(Integer id, Users users);
}
