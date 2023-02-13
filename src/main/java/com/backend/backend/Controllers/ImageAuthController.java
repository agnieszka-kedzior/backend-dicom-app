package com.backend.backend.Controllers;

import com.backend.backend.Entity.ImageAuth;
import com.backend.backend.Services.ImageAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/private/auth")
public class ImageAuthController {

    @Autowired
    private ImageAuthService imageAuthService;

    @RequestMapping(value = "/{userId}/{imageId}", method = RequestMethod.POST)
    public void addAuthority(@PathVariable("userId") Integer id, @PathVariable("imageId") Integer imageId, Principal user){
        imageAuthService.addAuthority(id, imageId, user.getName());
    }

    @RequestMapping(value = "/add"
            , method = RequestMethod.POST
            , params = {"userId","imgId"})
    public void cancelTask(Principal user, @RequestParam("userId") Integer userId, @RequestParam("imgId") Integer imgId){
        imageAuthService.addAuthority(userId, imgId, user.getName());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Set<ImageAuth> getUsersAuthImages(Principal user) {
        return imageAuthService.getUsersAuth(user.getName());
    }

    @RequestMapping(value = "/granted", method = RequestMethod.GET)
    public Set<ImageAuth> getGrantedAuth(Principal user) {
        return imageAuthService.getGrantedAuth(user.getName());
    }

    @RequestMapping(value = "/img/{authId}", method = RequestMethod.GET)
    public String getAuthImageId(@PathVariable("authId") Integer id){
        return imageAuthService.getAuthImgId(id);
    }

    @RequestMapping(value = "/user/{authId}", method = RequestMethod.GET)
    public String getAuthUserNameId(@PathVariable("authId") Integer id){
        return imageAuthService.getAuthUserName(id);
    }

    @RequestMapping(value = "del/{authId}", method = RequestMethod.DELETE)
    public void deleteAuth(@PathVariable("authId") Integer id, Principal user){ imageAuthService.deleteAuthority(id, user.getName());}
}
