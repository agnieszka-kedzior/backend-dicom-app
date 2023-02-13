package com.backend.backend.Services;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.ImageFrames;
import com.backend.backend.Entity.Patient;
import com.backend.backend.Entity.Users;
import com.backend.backend.Repos.ImageFramesRepo;
import com.backend.backend.Repos.ImageRepo;
import com.backend.backend.Repos.PatientRepo;
import com.backend.backend.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private ImageFramesRepo imageFramesRepo;

    public Image getImageById(Integer id){return imageRepo.findByImageId(id);}

    public void uploadFile(String name, String fileName, String path, Integer numberOfFrames, String patientID, String body, String study, String series){
        Users user = usersRepo.findUsersByUserName(name);
        Patient patient = patientRepo.findByPatientNumber(patientID);

        Date now = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        String currentDate=sdf.format(now);

        Image image = new Image();
        image.setImageName(fileName);
        image.setImagePath(path+fileName);
        image.setImageUploadDate(currentDate);
        image.setImageBodyPart(body);
        image.setImageStudy(study);
        image.setImageSeries(series);

        image.setUsers(user);
        image.setPatient(patient);
        image.setImageNumberOfFrames(numberOfFrames);

        user.getImage().add(image);
        patient.getImage().add(image);

        imageRepo.save(image);

        if(numberOfFrames == 1 | numberOfFrames == 0){
            ImageFrames frame = new ImageFrames();
            frame.setImage(image);
            frame.setImageFrameFileName(fileName);
            frame.setImageFrameNumber(1);
            frame.setImageFramePath(path+fileName);
            image.getImageFrames().add(frame);
            imageFramesRepo.save(frame);
        }else{
            for (int i = 1; i <= numberOfFrames; i++) {
                String newFileName;
                ImageFrames frame = new ImageFrames();
                frame.setImage(image);
                if(i < 10){
                    newFileName = fileName+"_000000000"+i+".jpg";
                    frame.setImageFrameFileName(newFileName);
                }else{
                    newFileName = fileName+"_00000000"+i+".jpg";
                    frame.setImageFrameFileName(newFileName);
                }
                frame.setImageFrameNumber(i);
                frame.setImageFramePath(path+newFileName);
                image.getImageFrames().add(frame);
                imageFramesRepo.save(frame);
            }
        }
    }

    public Set<Image> getUserImages(String name){
        Users user = usersRepo.findUsersByUserName(name);
        return imageRepo.findByUsers(user);
    }

    public Set<Image> getUserImagesByPatient(String user, String pat){
        Users users = usersRepo.findUsersByUserName(user);
        Patient patient = patientRepo.findByPatientName(pat);

        Set<Image> userImages = users.getImage();
        Set<Image> images = new HashSet<Image>();

        for(Image img : userImages){
            if(img.getPatient() == patient){
                images.add(img);
            }
        }

        return images;
    }

    public String getImagePatient(Integer imgId){
        Image image = imageRepo.findByImageId(imgId);
        Patient patient = patientRepo.findByImage(image);
        return patient.getPatientName();
    }

}
