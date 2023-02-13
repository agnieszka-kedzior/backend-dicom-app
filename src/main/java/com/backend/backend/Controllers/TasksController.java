package com.backend.backend.Controllers;

import com.backend.backend.Entity.TaskType;
import com.backend.backend.Entity.Tasks;
import com.backend.backend.Services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/private/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public TaskType[] getTaskTypes(){
        return TaskType.values();
    }

    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
    public Tasks getOneTask(@PathVariable("id") Integer id){
        return tasksService.getOneTask(id);
    }


    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public Set<Tasks> getOpenUserTasks(Principal user){
        return tasksService.getOpenUserTasks(user.getName());
    }

    @RequestMapping(value = "/ass", method = RequestMethod.GET)
    public Set<Tasks> getAssignedUserTasks(Principal user){
        return tasksService.getAssignedUserTasks(user.getName());
    }

    @RequestMapping(value = "/closed", method = RequestMethod.GET)
    public Set<Tasks> getClosedUserTasks(Principal user){
        return tasksService.getClosedUserTasks(user.getName());
    }

    @RequestMapping(value = "/img/{id}", method = RequestMethod.GET)
    public String getTaskImageId(@PathVariable("id") Integer id){
        return tasksService.getTaskImageId(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getTaskUserNameId(@PathVariable("id") Integer id){
        return tasksService.getTaskUserName(id);
    }

    @RequestMapping(value = "/createdBy/{id}", method = RequestMethod.GET)
    public String getTaskCreatedByUserName(@PathVariable("id") Integer id){
        return tasksService.getCreatedByUserName(id);
    }

    @RequestMapping(value = "/completedBy/{id}", method = RequestMethod.GET)
    public String getTaskCompletedByUserName(@PathVariable("id") Integer id){
        return tasksService.getCompletedByUserName(id);
    }

    @RequestMapping(value = "/new"
            , method = RequestMethod.POST
            , params = {"userAssigned","taskType","dueDate","title","desc","image"})
    public void createNewTask(Principal user
            , @RequestParam("userAssigned") String userAssigned
            , @RequestParam("taskType") String taskType
            , @RequestParam("dueDate") String dueDate
            , @RequestParam("title") String title
            , @RequestParam("desc") String desc
            , @RequestParam("image") Integer imageId){
        tasksService.createNewTask( user.getName() ,userAssigned, taskType, dueDate, title, desc, imageId);
    }

    @RequestMapping(value = "/accept"
            , method = RequestMethod.POST
            , params = {"taskId"})
    public void createNewTask(Principal user, @RequestParam("taskId") Integer id){
        tasksService.acceptTask(user.getName(), id);
    }

    @RequestMapping(value = "/decline"
            , method = RequestMethod.POST
            , params = {"taskId"})
    public void declineNewTask(Principal user, @RequestParam("taskId") Integer id){
        tasksService.returnTask(user.getName(), id);
    }

    @RequestMapping(value = "/reopen"
            , method = RequestMethod.POST
            , params = {"taskId","why"})
    public void reopenNewTask(Principal user
            , @RequestParam("taskId") Integer id
            , @RequestParam("why") String why){
        tasksService.reopenTask(user.getName(), id, why);
    }

    @RequestMapping(value = "/submit"
            , method = RequestMethod.POST
            , params = {"taskId"})
    public void submitTask(Principal user, @RequestParam("taskId") Integer id){
        tasksService.submitTask(user.getName(), id);
    }

    @RequestMapping(value = "/ass/user"
            , method = RequestMethod.POST
            , params = {"taskId","userAssigned"})
    public void declineNewTask(Principal user, @RequestParam("taskId") Integer id, @RequestParam("userAssigned") String userAssigned){
        tasksService.assignNewUser(user.getName(), id, userAssigned);
    }

    @RequestMapping(value = "/cancel"
            , method = RequestMethod.POST
            , params = {"taskId"})
    public void cancelTask(Principal user, @RequestParam("taskId") Integer id){
        tasksService.canceledTask(user.getName(), id);
    }

    @RequestMapping(value = "/close"
            , method = RequestMethod.POST
            , params = {"taskId"})
    public void closeTask(Principal user, @RequestParam("taskId") Integer id){
        tasksService.closeTask(user.getName(), id);
    }

    @RequestMapping(value = "/forced/close"
            , method = RequestMethod.POST
            , params = {"taskId"})
    public void forceTaskClosure(Principal user, @RequestParam("taskId") Integer id){
        tasksService.forcedClosureTask(user.getName(), id);
    }
}
