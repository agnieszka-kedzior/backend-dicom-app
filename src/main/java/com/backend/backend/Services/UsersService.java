package com.backend.backend.Services;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.Patient;
import com.backend.backend.Entity.Users;
import com.backend.backend.Repos.PatientRepo;
import com.backend.backend.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PatientRepo patientRepo;

    public Users getUserByName(String name) {
        return usersRepo.findUsersByUserName(name);
    }

    public Users getUserById(Integer id) {
        return usersRepo.findUsersByUserId(id);
    }

    public void addUserFriend(Integer id, Integer fr) {
        Users user = usersRepo.findUsersByUserId(id);
        Users friend = usersRepo.findUsersByUserId(fr);

        user.getFriends().add(friend);
        usersRepo.save(user);
    }

    public Set<Users> getUserFriends(String name) {
        Users user = usersRepo.findUsersByUserName(name);
        return user.getFriends();
    }


    public Set<Image> getUserImagesInfo(String name) {
        Users user = usersRepo.findUsersByUserName(name);
        Set<Image> userImg = user.getImage();
        return userImg;
    }

    public String getUserId(String name) {
        Users users = usersRepo.findUsersByUserFullName(name);
        return users.getUserId().toString();
    }

    public String getUserName(Integer id) {
        Users users = usersRepo.findUsersByUserId(id);
        return users.getUserFullName();
    }

}
