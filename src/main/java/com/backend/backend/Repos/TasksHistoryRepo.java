package com.backend.backend.Repos;

import com.backend.backend.Entity.Tasks;
import com.backend.backend.Entity.TasksHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TasksHistoryRepo extends CrudRepository<TasksHistory, Long> {

    TasksHistory findByTasksHistId(Integer id);
    Set<TasksHistory> findByTasks(Tasks tasks);
}
