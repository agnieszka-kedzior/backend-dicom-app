package com.backend.backend.Controllers;

import com.backend.backend.Entity.TasksHistory;
import com.backend.backend.Services.TasksHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/private/tasks/hist")
public class TasksHistoryController {

    @Autowired
    private TasksHistoryService tasksHistoryService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TasksHistory getOneTaskHistory(@PathVariable("id") Integer id){
        return tasksHistoryService.getTasksHistory(id);
    }

    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public Set<TasksHistory> getTaskHistory(@PathVariable("id") Integer taskId){
        return tasksHistoryService.getHistory(taskId);
    }

}
