package com.backend.backend.Controllers;

import com.backend.backend.Entity.Patient;
import com.backend.backend.Services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("private/p")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public Set<String> getUserPatientsList(Principal user){
        return  patientService.getPatientsList(user.getName());
    }


    @RequestMapping(value = "/det/{name}", method = RequestMethod.GET, produces = "application/json")
    public Patient getPatientDetails(@PathVariable("name") String  name){ return  patientService.getPatient(name); }
}
