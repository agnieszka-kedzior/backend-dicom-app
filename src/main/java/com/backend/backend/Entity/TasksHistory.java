package com.backend.backend.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name  = "tasksHistory")
@Table(name = "tasksHistory")
public class TasksHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tasksHistId", updatable = false, nullable = false)
    private Integer tasksHistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tasksId")
    @JsonBackReference
    private Tasks tasks;

    private TaskHistType taskHistType;
    private String taskHistDate;
    private String taskHistComment;

    public Integer getTasksHistId() {
        return tasksHistId;
    }

    public void setTasksHistId(Integer tasksHistId) {
        this.tasksHistId = tasksHistId;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public TaskHistType getTaskHistType() {
        return taskHistType;
    }

    public void setTaskHistType(TaskHistType taskHistType) {
        this.taskHistType = taskHistType;
    }

    public String getTaskHistDate() {
        return taskHistDate;
    }

    public void setTaskHistDate(String taskHistDate) {
        this.taskHistDate = taskHistDate;
    }

    public String getTaskHistComment() {
        return taskHistComment;
    }

    public void setTaskHistComment(String taskHistComment) {
        this.taskHistComment = taskHistComment;
    }
}
