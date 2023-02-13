package com.backend.backend.Repos;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.ImageAuth;
import com.backend.backend.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ImageAuthRepo extends CrudRepository<ImageAuth, Long> {

    Set<ImageAuth> findByUsers(Users users);
    Set<ImageAuth> findByGrantedByUserID(Integer id);
    ImageAuth findByUsersAndAndImage(Users users, Image image);
    ImageAuth findByImageAuthId(Integer id);
    ImageAuth findByGrantedByUserIDAndAndImageAuthId(Integer id, Integer authId);

}
