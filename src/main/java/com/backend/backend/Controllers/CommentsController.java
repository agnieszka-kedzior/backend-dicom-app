package com.backend.backend.Controllers;

import com.backend.backend.Entity.Comments;
import com.backend.backend.Services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/private/com")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , params = {"taskId","imageFrameId","comment"})
    public void addNewComment(Principal user
            , @RequestParam("taskId") Integer taskId
            , @RequestParam("imageFrameId") Integer frameId
            ,  @RequestParam("comment") String text){
        commentsService.addComment(taskId, frameId, text, user.getName());
    }

    @RequestMapping(value = "/get/{taskId}/{frameId}", method = RequestMethod.GET)
    public Comments getTaskFrameComment(@PathVariable("taskId") Integer taskId, @PathVariable("frameId") Integer frameId) {
        return commentsService.getComments(taskId, frameId);
    }
}
