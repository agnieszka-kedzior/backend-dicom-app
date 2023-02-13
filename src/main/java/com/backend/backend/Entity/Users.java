package com.backend.backend.Entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name =  "users")
@Table(name = "users")
@NamedEntityGraph(name = "graph.users",
attributeNodes = {
        @NamedAttributeNode("image")
})
@JsonIgnoreProperties({"friends", "friendOf","userPassword", "image", "tasks","auth","comments"})
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId",updatable = false, nullable = false)
    private Integer userId;

    private String userName;
    private String userFullName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private String userRole;
    private String userRegDate;

    private String userDegree;
    private String userWork;
    private String userPosition;

    @OneToMany(mappedBy = "users")
    private Set<Image> image = new HashSet<Image>();


    @OneToMany(mappedBy = "users")
    private Set<Tasks> tasks = new HashSet<Tasks>();

    @OneToMany(mappedBy = "users")
    private Set<ImageAuth> auth = new HashSet<ImageAuth>();

    @OneToMany(mappedBy = "users")
    private Set<Comments> comments = new HashSet<Comments>();

    @JsonIgnoreProperties({"friends","friendOf"})
    @ManyToMany
    @JoinTable(name="friends",
            joinColumns=@JoinColumn(name="userId"),
            inverseJoinColumns=@JoinColumn(name="friendId")
    )
    private Set<Users> friends;

    @JsonIgnoreProperties({"friends","friendOf"})
    @ManyToMany(mappedBy = "friends")
    private Set<Users> friendOf;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRegDate() {
        return userRegDate;
    }

    public void setUserRegDate(String userRegDate) {
        this.userRegDate = userRegDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Image> getImage() {
        return image;
    }

    public void setImage(Set<Image> image) {
        this.image = image;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Set<ImageAuth> getAuth() {
        return auth;
    }

    public void setAuth(Set<ImageAuth> auth) {
        this.auth = auth;
    }


    public Set<Users> getFriends() {
        return friends;
    }

    public void setFriends(Set<Users> friends) {
        this.friends = friends;
    }

    public Set<Users> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(Set<Users> friendOf) {
        this.friendOf = friendOf;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public String getUserDegree() {
        return userDegree;
    }

    public void setUserDegree(String userDegree) {
        this.userDegree = userDegree;
    }

    public String getUserWork() {
        return userWork;
    }

    public void setUserWork(String userWork) {
        this.userWork = userWork;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }
}
