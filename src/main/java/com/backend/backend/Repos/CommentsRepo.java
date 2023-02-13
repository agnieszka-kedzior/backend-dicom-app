package com.backend.backend.Repos;

import com.backend.backend.Entity.Comments;
import com.backend.backend.Entity.ImageFrames;
import com.backend.backend.Entity.Tasks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentsRepo extends CrudRepository<Comments, Long> {

    Comments findByTasksAndAndImageFrames(Tasks tasks, ImageFrames imageFrames);
}
