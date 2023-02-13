package com.backend.backend.Services;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.ImageAuth;
import com.backend.backend.Entity.ImageAuthHistory;
import com.backend.backend.Entity.Users;
import com.backend.backend.Repos.ImageAuthHistoryRepo;
import com.backend.backend.Repos.ImageAuthRepo;
import com.backend.backend.Repos.ImageRepo;
import com.backend.backend.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
public class ImageAuthService {

    @Autowired
    private ImageAuthRepo imageAuthRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ImageAuthHistoryRepo imageAuthHistoryRepo;

    public void addAuthority(Integer userID, Integer imageId, String username){
        Users users = usersRepo.findUsersByUserName(username);
        Users usersAssigned = usersRepo.findUsersByUserId(userID);
        Image image = imageRepo.findByImageId(imageId);

        if (imageAuthRepo.findByUsersAndAndImage(usersAssigned, image) == null) {
            ImageAuth imageAuth = new ImageAuth();
            imageAuth.setUsers(usersAssigned);
            imageAuth.setImage(image);
            usersAssigned.getAuth().add(imageAuth);
            image.getAuth().add(imageAuth);

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            imageAuth.setGrantedDate(dateFormat.format(date));
            imageAuth.setGrantedByUserID(users.getUserId());

            imageAuthRepo.save(imageAuth);
        }
    }

    public void deleteAuthority(Integer id, String name){
        Users user = usersRepo.findUsersByUserName(name);
        ImageAuth imageAuth = imageAuthRepo.findByGrantedByUserIDAndAndImageAuthId(user.getUserId(),id);

        ImageAuthHistory imageAuthHistory = new ImageAuthHistory();
        imageAuthHistory.setHistImageAuthId(imageAuth.getImageAuthId());
        imageAuthHistory.setHistUserId(imageAuth.getUsers().getUserId());
        imageAuthHistory.setHistImageId(imageAuth.getImage().getImageId());
        imageAuthHistory.setHistGrantedByUserId(imageAuth.getGrantedByUserID());
        imageAuthHistory.setHistGrantedDate(imageAuth.getGrantedDate());

        imageAuthHistoryRepo.save(imageAuthHistory);
        imageAuthRepo.delete(imageAuth);
    }

   public Set<ImageAuth> getUsersAuth(String name){
       Users user = usersRepo.findUsersByUserName(name);
       return imageAuthRepo.findByUsers(user);
   }

   public Set<ImageAuth> getGrantedAuth(String name){
        Users users = usersRepo.findUsersByUserName(name);
        return imageAuthRepo.findByGrantedByUserID(users.getUserId());
   }

   public String getAuthImgId(Integer id){
        ImageAuth imageAuth = imageAuthRepo.findByImageAuthId(id);
        return imageAuth.getImage().getImageId().toString();
   }

   public String getAuthUserName(Integer id){
       ImageAuth imageAuth = imageAuthRepo.findByImageAuthId(id);
       return imageAuth.getUsers().getUserFullName();
   }
}
