package com.backend.backend.Services;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.Patient;
import com.backend.backend.Entity.Users;
import com.backend.backend.Repos.ImageRepo;
import com.backend.backend.Repos.PatientRepo;
import com.backend.backend.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PatientService {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PatientRepo patientRepo;

    public void createPatient(String number, String name, String birth, String sex, String age){
        Patient find = patientRepo.findByPatientNumber(number);

        if(find == null) {
            Patient patient = new Patient();
            patient.setPatientNumber(number);

            patient.setPatientName(name);
            patient.setPatientBirthDate(birth);
            patient.setPatientSex(sex);
            patient.setPatientAge(age);

            patientRepo.save(patient);
        } else {
            find.setPatientName(name);
            find.setPatientBirthDate(birth);
            find.setPatientSex(sex);
            find.setPatientAge(age);

            patientRepo.save(find);
        }

    }

    public Set<String> getPatientsList(String name){
        Users users = usersRepo.findUsersByUserName(name);
        Set<Image> imgSet = users.getImage();
        Set<String> list = new HashSet<>();
        for(Image img : imgSet){
            list.add(img.getPatient().getPatientName());
        }
        return list;
    }

    public Patient getPatient(String name){
        return patientRepo.findByPatientName(name);
    }
}
