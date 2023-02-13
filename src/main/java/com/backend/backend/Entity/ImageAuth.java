package com.backend.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "imageAuth")
@Table(name = "imageAuth")
public class ImageAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageAuthId", updatable = false, nullable = false)
    private Integer imageAuthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageId")
    @JsonBackReference
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Users users;

    private String grantedDate;
    private Integer grantedByUserID;

    public Integer getImageAuthId() {
        return imageAuthId;
    }

    public void setImageAuthId(Integer imageAuthId) {
        this.imageAuthId = imageAuthId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getGrantedDate() {
        return grantedDate;
    }

    public void setGrantedDate(String grantedDate) {
        this.grantedDate = grantedDate;
    }

    public Integer getGrantedByUserID() {
        return grantedByUserID;
    }

    public void setGrantedByUserID(Integer grantedByUserID) {
        this.grantedByUserID = grantedByUserID;
    }
}
