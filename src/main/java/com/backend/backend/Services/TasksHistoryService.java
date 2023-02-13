package com.backend.backend.Services;

import com.backend.backend.Entity.*;
import com.backend.backend.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class TasksHistoryService {

    @Autowired
    private TasksHistoryRepo tasksHistoryRepo;

    @Autowired
    private TasksRepo tasksRepo;

    @Autowired
    private UsersRepo usersRepo;

    public TasksHistory getTasksHistory(Integer id){
        return tasksHistoryRepo.findByTasksHistId(id);
    }

    public Set<TasksHistory> getHistory(Integer taskId){
        Tasks tasks = tasksRepo.findByTasksId(taskId);
        return tasksHistoryRepo.findByTasks(tasks);
    }

}
