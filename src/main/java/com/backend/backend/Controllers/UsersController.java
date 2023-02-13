package com.backend.backend.Controllers;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.Users;
import com.backend.backend.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/private/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/det", method = RequestMethod.GET, produces = "application/json")
    public Users getUserInfo(Principal user){
        return usersService.getUserByName(user.getName());
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public Users getUserByName(@PathVariable("name") String name){
        return usersService.getUserByName(name);
    }

    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET,  produces = "application/json")
    public Users getUserById(@PathVariable("id") Integer id){ return  usersService.getUserById(id);}

    @RequestMapping(value = "/add/{id}/{fr}", method = RequestMethod.POST)
    public void addUserFriend(@PathVariable("id") Integer id, @PathVariable("fr") Integer fr){
        usersService.addUserFriend(id, fr);
    }

    @RequestMapping(value = "/fr", method = RequestMethod.GET, produces = "application/json")
    public Set<Users> getUserFriends(Principal user){
        return  usersService.getUserFriends(user.getName());
    }

    @RequestMapping(value = "/img", method = RequestMethod.GET, produces = "application/json")
    public Set<Image> getUserImages(Principal user){
        return  usersService.getUserImagesInfo(user.getName());
    }


    @RequestMapping(value = "/id/{name}", method = RequestMethod.GET)
    public String getId(@PathVariable("name") String name){
        return usersService.getUserId(name);
    }

    @RequestMapping(value = "/name/{id}", method = RequestMethod.GET)
    public String getName(@PathVariable("id") Integer id){
        return usersService.getUserName(id);
    }
}
