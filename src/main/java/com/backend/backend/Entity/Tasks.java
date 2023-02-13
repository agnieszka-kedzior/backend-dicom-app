package com.backend.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name  = "tasks")
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tasksId", updatable = false, nullable = false)
    private Integer tasksId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageId")
    @JsonBackReference
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Users users;

    @OneToMany(mappedBy = "tasks")
    private Set<TasksHistory> tasksHistory = new HashSet<TasksHistory>();

    @OneToMany(mappedBy = "tasks")
    private Set<Comments> comments = new HashSet<Comments>();

    private Integer taskCreatedByUserId;
    private Integer taskCompletedByUserId;

    private TaskStatus taskStatus;
    private TaskStep taskStep;
    private TaskType taskType;
    private TaskActionStatus taskActionStatus;

    private String tasksCreationDate;
    private String tasksExpirationDate;
    private String taskClosedDate;
    private String taskTitle;
    private String taskDesc;
    private String taskActionComment;
    private String taskReopenReason;

    public Integer getTasksId() {
        return tasksId;
    }

    public void setTasksId(Integer tasksId) {
        this.tasksId = tasksId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }



    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStep getTaskStep() {
        return taskStep;
    }

    public void setTaskStep(TaskStep taskStep) {
        this.taskStep = taskStep;
    }

    public String getTasksCreationDate() {
        return tasksCreationDate;
    }

    public void setTasksCreationDate(String tasksCreationDate) {
        this.tasksCreationDate = tasksCreationDate;
    }

    public String getTasksExpirationDate() {
        return tasksExpirationDate;
    }

    public void setTasksExpirationDate(String tasksExpirationDate) {
        this.tasksExpirationDate = tasksExpirationDate;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskActionStatus getTaskActionStatus() {
        return taskActionStatus;
    }

    public void setTaskActionStatus(TaskActionStatus taskActionStatus) {
        this.taskActionStatus = taskActionStatus;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskActionComment() {
        return taskActionComment;
    }

    public void setTaskActionComment(String taskActionComment) {
        this.taskActionComment = taskActionComment;
    }

    public Integer getTaskCreatedByUserId() {
        return taskCreatedByUserId;
    }

    public void setTaskCreatedByUserId(Integer taskCreatedByUserId) {
        this.taskCreatedByUserId = taskCreatedByUserId;
    }

    public String getTaskClosedDate() {
        return taskClosedDate;
    }

    public void setTaskClosedDate(String taskClosedDate) {
        this.taskClosedDate = taskClosedDate;
    }

    public Set<TasksHistory> getTasksHistory() {
        return tasksHistory;
    }

    public void setTasksHistory(Set<TasksHistory> tasksHistory) {
        this.tasksHistory = tasksHistory;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public Integer getTaskCompletedByUserId() {
        return taskCompletedByUserId;
    }

    public void setTaskCompletedByUserId(Integer taskCompletedByUserId) {
        this.taskCompletedByUserId = taskCompletedByUserId;
    }

    public String getTaskReopenReason() {
        return taskReopenReason;
    }

    public void setTaskReopenReason(String taskReopenReason) {
        this.taskReopenReason = taskReopenReason;
    }
}
