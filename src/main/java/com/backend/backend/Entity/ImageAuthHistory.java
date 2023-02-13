package com.backend.backend.Entity;

import javax.persistence.*;

@Entity(name = "imageAuthHistory")
@Table(name = "imageAuthHistory")
public class ImageAuthHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authHistId", updatable = false, nullable = false)
    private Integer authHistId;

    private Integer histImageAuthId;
    private Integer histImageId;
    private Integer histUserId;
    private Integer histGrantedByUserId;
    private String histGrantedDate;

    public Integer getAuthHistId() {
        return authHistId;
    }

    public void setAuthHistId(Integer authHistId) {
        this.authHistId = authHistId;
    }

    public Integer getHistImageAuthId() {
        return histImageAuthId;
    }

    public void setHistImageAuthId(Integer histImageAuthId) {
        this.histImageAuthId = histImageAuthId;
    }

    public Integer getHistImageId() {
        return histImageId;
    }

    public void setHistImageId(Integer histImageId) {
        this.histImageId = histImageId;
    }

    public Integer getHistUserId() {
        return histUserId;
    }

    public void setHistUserId(Integer histUserId) {
        this.histUserId = histUserId;
    }

    public Integer getHistGrantedByUserId() {
        return histGrantedByUserId;
    }

    public void setHistGrantedByUserId(Integer histGrantedByUserId) {
        this.histGrantedByUserId = histGrantedByUserId;
    }

    public String getHistGrantedDate() {
        return histGrantedDate;
    }

    public void setHistGrantedDate(String histGrantedDate) {
        this.histGrantedDate = histGrantedDate;
    }
}
