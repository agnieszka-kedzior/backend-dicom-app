package com.backend.backend.Repos;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ImageRepo extends CrudRepository<Image, Long> {

    Image findByImageId(Integer id);
    Set<Image> findByUsers(Users users);
}
