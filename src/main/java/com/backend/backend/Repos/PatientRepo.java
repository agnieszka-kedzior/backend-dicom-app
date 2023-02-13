package com.backend.backend.Repos;

import com.backend.backend.Entity.Image;
import com.backend.backend.Entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PatientRepo extends CrudRepository<Patient, Long> {

    Patient findByPatientNumber(String id);
    Patient findByImage(Image image);
    Patient findByPatientName(String name);

}
