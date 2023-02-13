package com.backend.backend.Services;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.ImageFrames;
import com.backend.backend.Repos.ImageFramesRepo;
import com.backend.backend.Repos.ImageRepo;
import com.backend.backend.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ImageFramesService {

    @Autowired
    private ImageFramesRepo imageFramesRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ImageRepo imageRepo;

    public ImageFrames getFirstFrameByImageId(Integer id){
        return imageFramesRepo.findFirstByImage_ImageId(id);
    }

    public ImageFrames getFrameByImageIdAndFrameNumber(Integer imageId, Integer number){
        return imageFramesRepo.findImageFramesByImage_ImageIdAndAndImageFrameNumber(imageId, number);
    }

    public Set<ImageFrames> getImageFrames(Integer imageId){
        Image image = imageRepo.findByImageId(imageId);
        return imageFramesRepo.findByImage(image);
    }
}
