package com.backend.backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "imageFrames")
@Table(name = "imageFrames")
public class ImageFrames implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageFramesId", updatable = false, nullable = false)
    private Integer imageFramesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageId")
    @JsonBackReference
    private Image image;

    @OneToMany(mappedBy = "imageFrames")
    private Set<Comments> comments = new HashSet<Comments>();

    private Integer imageFrameNumber;
    private String imageFrameFileName;
    private String imageFramePath;

    public Integer getImageFramesId() {
        return imageFramesId;
    }

    public void setImageFramesId(Integer imageFramesId) {
        this.imageFramesId = imageFramesId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getImageFrameNumber() {
        return imageFrameNumber;
    }

    public void setImageFrameNumber(Integer imageFrameNumber) {
        this.imageFrameNumber = imageFrameNumber;
    }

    public String getImageFrameFileName() {
        return imageFrameFileName;
    }

    public void setImageFrameFileName(String imageFrameFileName) {
        this.imageFrameFileName = imageFrameFileName;
    }

    public String getImageFramePath() {
        return imageFramePath;
    }

    public void setImageFramePath(String imageFramePath) {
        this.imageFramePath = imageFramePath;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }
}
