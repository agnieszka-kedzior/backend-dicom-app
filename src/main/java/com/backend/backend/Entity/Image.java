package com.backend.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name =  "image")
@Table(name = "image")
@JsonIgnoreProperties({"tasks"})
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId", updatable = false, nullable = false)
    private Integer imageId;

    private String imageName;
    private String imagePath;
    private Integer imageNumberOfFrames;
    private String imageUploadDate;

    private String imageBodyPart;
    private String imageStudy;
    private String imageSeries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    @JsonBackReference
    private Patient patient;

    @OneToMany(mappedBy = "image")
    private Set<ImageFrames> imageFrames = new HashSet<ImageFrames>();

    @OneToMany(mappedBy = "image")
    private Set<Tasks> tasks = new HashSet<Tasks>();

    @OneToMany(mappedBy = "image")
    private Set<ImageAuth> auth = new HashSet<ImageAuth>();

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Users getUsers() {
        return users;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<ImageFrames> getImageFrames() {
        return imageFrames;
    }

    public void setImageFrames(Set<ImageFrames> imageFrames) {
        this.imageFrames = imageFrames;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<ImageAuth> getAuth() {
        return auth;
    }

    public void setAuth(Set<ImageAuth> auth) {
        this.auth = auth;
    }

    public Integer getImageNumberOfFrames() {
        return imageNumberOfFrames;
    }

    public void setImageNumberOfFrames(Integer imageNumberOfFrames) {
        this.imageNumberOfFrames = imageNumberOfFrames;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getImageBodyPart() {
        return imageBodyPart;
    }

    public void setImageBodyPart(String imageBodyPart) {
        this.imageBodyPart = imageBodyPart;
    }

    public String getImageStudy() {
        return imageStudy;
    }

    public void setImageStudy(String imageStudy) {
        this.imageStudy = imageStudy;
    }

    public String getImageSeries() {
        return imageSeries;
    }

    public void setImageSeries(String imageSeries) {
        this.imageSeries = imageSeries;
    }

    public String getImageUploadDate() {
        return imageUploadDate;
    }

    public void setImageUploadDate(String imageUploadDate) {
        this.imageUploadDate = imageUploadDate;
    }

}
