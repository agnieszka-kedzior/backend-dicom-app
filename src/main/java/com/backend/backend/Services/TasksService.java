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
public class TasksService {

    @Autowired
    private TasksRepo tasksRepo;

    @Autowired
    private TasksHistoryRepo tasksHistoryRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private ImageAuthRepo imageAuthRepo;

    public String getCurrentDate() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(now);
    }

    public void closure(String username, Integer id, TaskActionStatus taskActionStatus, String histText) {
        String currentDate = getCurrentDate();

        Users users = usersRepo.findUsersByUserName(username);
        Tasks tasks = tasksRepo.findByTasksId(id);

        tasks.setTaskStatus(TaskStatus.CLOSED);
        tasks.setTaskStep(TaskStep.CLOSE);
        tasks.setTaskActionStatus(taskActionStatus);
        tasks.setTaskClosedDate(currentDate);
        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.CLOSED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment(histText + users.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public Tasks getOneTask(Integer id) {
        return tasksRepo.findByTasksId(id);
    }

    public Set<Tasks> getOpenUserTasks(String username) {
        Users users = usersRepo.findUsersByUserName(username);
        Set<Tasks> usersTasks = tasksRepo.findByTaskCreatedByUserId(users.getUserId());
        Set<Tasks> openTasks = new HashSet<>();
        for (Tasks tasks : usersTasks) {
            if (!tasks.getTaskStatus().equals(TaskStatus.CLOSED)) {
                openTasks.add(tasks);
            }
        }
        return openTasks;
    }

    public Set<Tasks> getAssignedUserTasks(String username) {
        Users users = usersRepo.findUsersByUserName(username);
        Set<Tasks> usersTasks = tasksRepo.findByUsers(users);
        Set<Tasks> assignedTasks = new HashSet<>();
        for (Tasks tasks : usersTasks) {
            if (!tasks.getTaskStatus().equals(TaskStatus.CLOSED)) {
                assignedTasks.add(tasks);
            }
        }
        return assignedTasks;
    }

    public Set<Tasks> getClosedUserTasks(String username) {
        Users users = usersRepo.findUsersByUserName(username);
        Set<Tasks> usersTasks = tasksRepo.findByTaskCreatedByUserId(users.getUserId());
        Set<Tasks> closedTasks = new HashSet<>();
        for (Tasks tasks : usersTasks) {
            if (tasks.getTaskStatus().equals(TaskStatus.CLOSED)) {
                closedTasks.add(tasks);
            }
        }
        return closedTasks;
    }

    public String getTaskImageId(Integer id) {
        return tasksRepo.findByTasksId(id).getImage().getImageId().toString();
    }

    public String getTaskUserName(Integer id) {
        return tasksRepo.findByTasksId(id).getUsers().getUserFullName();
    }

    public String getCreatedByUserName(Integer taskId) {
        Integer userId = tasksRepo.findByTasksId(taskId).getTaskCreatedByUserId();
        Users users = usersRepo.findUsersByUserId(userId);
        return users.getUserFullName();
    }

    public String getCompletedByUserName(Integer taskId) {
        if(tasksRepo.findByTasksId(taskId).getTaskCompletedByUserId() == null){
            return "N/A";
        }else {
            Integer userId = tasksRepo.findByTasksId(taskId).getTaskCompletedByUserId();
            Users users = usersRepo.findUsersByUserId(userId);
            return users.getUserFullName();
        }
    }

    public void createNewTask(String user, String userAssigned, String taskType, String dueDate, String title, String desc, Integer imageId) {
        String currentDate = getCurrentDate();

        Users usersCreated = usersRepo.findUsersByUserName(user);
        Users usersAssigned = usersRepo.findUsersByUserFullName(userAssigned);
        Image image = imageRepo.findByImageId(imageId);

        Tasks tasks = new Tasks();
        tasks.setImage(image);
        tasks.setUsers(usersAssigned);
        usersAssigned.getTasks().add(tasks);
        image.getTasks().add(tasks);


        tasks.setTaskStatus(TaskStatus.NEW);
        tasks.setTaskCreatedByUserId(usersCreated.getUserId());
        tasks.setTasksCreationDate(currentDate);
        tasks.setTasksExpirationDate(dueDate);
        tasks.setTaskTitle(title);
        tasks.setTaskDesc(desc);
        tasks.setTaskStep(TaskStep.ACCEPT);
        tasks.setTaskType(TaskType.valueOf(taskType));
        tasks.setTaskActionStatus(TaskActionStatus.WAITING);

        if (imageAuthRepo.findByUsersAndAndImage(usersAssigned, image) == null) {
            ImageAuth imageAuth = new ImageAuth();
            imageAuth.setUsers(usersAssigned);
            imageAuth.setImage(image);
            usersAssigned.getAuth().add(imageAuth);
            image.getAuth().add(imageAuth);
            imageAuth.setGrantedDate(currentDate);
            imageAuth.setGrantedByUserID(usersCreated.getUserId());
            imageAuthRepo.save(imageAuth);
        } else {
            System.out.println("Access already has been granted");
        }

        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.CREATED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment("Task created by " + usersCreated.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public void acceptTask(String username, Integer id) {
        String currentDate = getCurrentDate();

        Users users = usersRepo.findUsersByUserName(username);
        Tasks tasks = tasksRepo.findByTasksIdAndUsers(id, users);
        tasks.setTaskStatus(TaskStatus.ACTIVE);
        tasks.setTaskStep(TaskStep.ANALYSIS);
        tasks.setTaskActionStatus(TaskActionStatus.ONGOING);
        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.ASSIGNED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment("Task accepted by " + users.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public void returnTask(String username, Integer id) {
        String currentDate = getCurrentDate();

        Users users = usersRepo.findUsersByUserName(username);
        Tasks tasks = tasksRepo.findByTasksIdAndUsers(id, users);
        Users backToUser = usersRepo.findUsersByUserId(tasks.getTaskCreatedByUserId());

        tasks.setTaskStatus(TaskStatus.REJECTED);
        tasks.setTaskStep(TaskStep.ASSIGN);
        tasks.setTaskActionStatus(TaskActionStatus.WAITING);
        tasks.setUsers(backToUser);
        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.DECLINED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment("Task declined by " + users.getUserFullName() + " and assigned by to " + backToUser.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public void submitTask(String username, Integer id) {
        String currentDate = getCurrentDate();

        Users users = usersRepo.findUsersByUserName(username);
        Tasks tasks = tasksRepo.findByTasksIdAndUsers(id, users);
        Users backToUser = usersRepo.findUsersByUserId(tasks.getTaskCreatedByUserId());

        tasks.setTaskStatus(TaskStatus.PENDING);
        tasks.setTaskStep(TaskStep.REVIEW);
        tasks.setTaskActionStatus(TaskActionStatus.WAITING);
        tasks.setUsers(backToUser);
        tasks.setTaskCompletedByUserId(users.getUserId());
        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.UPDATED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment("Task submitted by " + users.getUserFullName() + " and assigned by to " + backToUser.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public void assignNewUser(String username, Integer id, String userAssigned) {
        String currentDate = getCurrentDate();

        Users users = usersRepo.findUsersByUserName(username);
        Users usersAssigned = usersRepo.findUsersByUserFullName(userAssigned);

        Tasks tasks = tasksRepo.findByTasksIdAndUsers(id, users);

        tasks.setTaskStatus(TaskStatus.PENDING);
        tasks.setTaskStep(TaskStep.ACCEPT);
        tasks.setUsers(usersAssigned);
        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.ASSIGNED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment("Task assigned to " + usersAssigned.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public void canceledTask(String username, Integer id) {
        closure(username, id, TaskActionStatus.CANCELED, "Task canceled by ");
    }


    public void reopenTask(String username, Integer id, String why) {
        String currentDate = getCurrentDate();

        Users users = usersRepo.findUsersByUserName(username);
        Tasks tasks = tasksRepo.findByTasksIdAndUsers(id, users);
        Users backToUser = usersRepo.findUsersByUserId(tasks.getTaskCompletedByUserId());

        tasks.setTaskStatus(TaskStatus.ACTIVE);
        tasks.setTaskStep(TaskStep.REOPEN);
        tasks.setTaskActionStatus(TaskActionStatus.ONGOING);
        tasks.setTaskReopenReason(why);
        tasks.setUsers(backToUser);
        tasksRepo.save(tasks);

        TasksHistory hist = new TasksHistory();
        hist.setTasks(tasks);
        hist.setTaskHistType(TaskHistType.UPDATED);
        hist.setTaskHistDate(currentDate);
        hist.setTaskHistComment("Task re-opened by " + users.getUserFullName() + " and assigned by to " + backToUser.getUserFullName());
        tasks.getTasksHistory().add(hist);
        tasksHistoryRepo.save(hist);
    }

    public void closeTask(String username, Integer id) {
        closure(username, id, TaskActionStatus.COMPLETED, "Task closed by ");
    }


    public void forcedClosureTask(String username, Integer id) {
        closure(username, id, TaskActionStatus.CANCELED, "Task closure forced by ");
    }

}
