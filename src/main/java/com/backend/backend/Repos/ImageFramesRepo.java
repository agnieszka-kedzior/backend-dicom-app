package com.backend.backend.Repos;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.ImageFrames;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ImageFramesRepo extends CrudRepository<ImageFrames, Long> {

    ImageFrames findFirstByImage_ImageId(Integer id);
    ImageFrames findImageFramesByImage_ImageIdAndAndImageFrameNumber(Integer imageId, Integer frameNumber);
    Set<ImageFrames> findByImage(Image image);
    ImageFrames findByImageFramesId(Integer id);
}
