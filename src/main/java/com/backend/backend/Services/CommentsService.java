package com.backend.backend.Services;

import com.backend.backend.Entity.*;
import com.backend.backend.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private TasksRepo tasksRepo;

    @Autowired
    private TasksHistoryRepo tasksHistoryRepo;


    @Autowired
    private ImageFramesRepo imageFramesRepo;

    public void addComment(Integer taskId, Integer imageFrameId, String commentText, String username){
        Date now = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        String currentDate=sdf.format(now);

        Tasks tasks = tasksRepo.findByTasksId(taskId);
        ImageFrames imageFrames = imageFramesRepo.findByImageFramesId(imageFrameId);
        Users usersCreated = usersRepo.findUsersByUserName(username);

        if (commentsRepo.findByTasksAndAndImageFrames(tasks, imageFrames) == null) {
            Comments comment = new Comments();

            comment.setUsers(usersCreated);
            comment.setTasks(tasks);
            comment.setImageFrames(imageFrames);

            usersCreated.getComments().add(comment);
            tasks.getComments().add(comment);
            imageFrames.getComments().add(comment);

            comment.setCommentDate(currentDate);
            comment.setCommentText(commentText);
            commentsRepo.save(comment);

            TasksHistory hist = new TasksHistory();
            hist.setTasks(tasks);
            hist.setTaskHistType(TaskHistType.COMMENT);
            hist.setTaskHistDate(currentDate);
            hist.setTaskHistComment("Comment added: "+commentText+"(Frame "+imageFrames.getImageFrameNumber()+")");
            tasks.getTasksHistory().add(hist);
            tasksHistoryRepo.save(hist);
        } else {
            Comments comments = commentsRepo.findByTasksAndAndImageFrames(tasks, imageFrames);
            comments.setCommentDate(currentDate);
            comments.setCommentText(commentText);
            commentsRepo.save(comments);

            TasksHistory hist = new TasksHistory();
            hist.setTasks(tasks);
            hist.setTaskHistType(TaskHistType.COMMENT);
            hist.setTaskHistDate(currentDate);
            hist.setTaskHistComment("Comment updated: "+commentText+"(Frame "+imageFrames.getImageFrameNumber()+")");
            tasks.getTasksHistory().add(hist);
            tasksHistoryRepo.save(hist);
        }

        tasks.setTaskStep(TaskStep.SUBMIT);
        tasksRepo.save(tasks);
    }

    public Comments getComments(Integer taskId, Integer imageFrameId){
        Tasks tasks = tasksRepo.findByTasksId(taskId);
        ImageFrames imageFrames = imageFramesRepo.findByImageFramesId(imageFrameId);
        return commentsRepo.findByTasksAndAndImageFrames(tasks, imageFrames);
    }
}
