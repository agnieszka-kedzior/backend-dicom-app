package com.backend.backend.Controllers;

import com.backend.backend.Entity.Image;
import com.backend.backend.Services.ImageService;
import com.backend.backend.Services.PatientService;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.ConsumerFormatImageMaker;
import com.pixelmed.display.SourceImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/private/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/upload"
            , method = RequestMethod.POST
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestParam("file") MultipartFile file, Principal user) {
        AttributeList list = new AttributeList();
        Integer numberOfFrames = 0;
        String pID = null;
        String pName = null;
        String pBirthDate = null;
        String pSex = null;
        String pAge = null;
        String pBodyPart = null;
        String study = null;
        String series = null;

        String fileName = file.getOriginalFilename();
        String createPath = "D:\\PracaInz\\Backend\\filesUploaded\\" + getCurrentDate();
        String uploadFilePath = createPath + "\\";
        File fileChange = null;
        FileInputStream fileSavedStream = null;

        try {
            Files.createDirectories(Paths.get(createPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String filePath = uploadFilePath + file.getOriginalFilename();
        String outputJpgFile = filePath + ".jpg";
        File newFile = new File(uploadFilePath + file.getOriginalFilename());
        try {
            newFile.createNewFile();
            FileOutputStream outFile = new FileOutputStream(newFile);
            outFile.write(file.getBytes());
            outFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            list.read(newFile);

            SourceImage img = new com.pixelmed.display.SourceImage(list);
            numberOfFrames = img.getNumberOfFrames();

            pID = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.PatientID);
            pName = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.PatientName);
            pBirthDate = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.PatientBirthDate);
            pSex = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.PatientSex);
            pAge = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.PatientAge);
            pBodyPart = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.BodyPartExamined);
            study = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.StudyInstanceUID);
            series = Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.SeriesInstanceUID);

            String pIdRep = pID.replaceAll(".", "*");
            String pNameRep = pName.replaceAll(".", "*");
            String pBirthDateRep = pBirthDate.replaceAll(".", "*");
            String pSexRep = pSex.replaceAll(".", "*");
            String pAgeRep = pAge.replaceAll(".", "*");

            fileSavedStream = new FileInputStream(fileChange = new File(uploadFilePath
                    + file.getOriginalFilename()));
            byte[] arr = new byte[(int) fileChange.length()];
            fileSavedStream.read(arr, 0, arr.length);

            String fileText = new String(arr, StandardCharsets.ISO_8859_1);
            fileText = fileText.replace(pID, pIdRep);
            fileText = fileText.replace(pName, pNameRep);
            fileText = fileText.replace(pBirthDate, pBirthDateRep);
            fileText = fileText.replace(pSex, pSexRep);
            fileText = fileText.replace(pAge, pAgeRep);

            File changedFile = new File(uploadFilePath + file.getOriginalFilename());
            try {
                changedFile.createNewFile();
                FileOutputStream outFile = new FileOutputStream(changedFile);
                outFile.write(fileText.getBytes(StandardCharsets.ISO_8859_1));
                outFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ConsumerFormatImageMaker.convertFileToEightBitImage(filePath, outputJpgFile, "jpeg", 0);

            if (numberOfFrames == 1) {
                fileName = fileName + ".jpg";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        patientService.createPatient(pID, pName, pBirthDate, pSex, pAge);
        imageService.uploadFile(user.getName(), fileName, uploadFilePath, numberOfFrames, pID, pBodyPart, study, series);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Set<Image> getUsersImages(Principal user) {
        return imageService.getUserImages(user.getName());
    }


    @RequestMapping(
            value = "/by/{id}"
            , method = RequestMethod.GET
    )
    public Image getImageDetailsById(@PathVariable("id") Integer id) {
        return imageService.getImageById(id);
    }

    @RequestMapping(
            value = "/fnr/{id}"
            , method = RequestMethod.GET
    )
    public String getImageNumberOfFrames(@PathVariable("id") Integer id) {
        return imageService.getImageById(id).getImageNumberOfFrames().toString();
    }

    @RequestMapping(
            value = "/user/pat/{pat}"
            , method = RequestMethod.GET
    )
    public Set<Image> getUserImageByPatients(@PathVariable("pat") String pat, Principal user) {
        return imageService.getUserImagesByPatient(user.getName(), pat);
    }

    @RequestMapping(
            value = "/pat/{imgId}"
            , method = RequestMethod.GET
    )
    public String getImagePatient(@PathVariable("imgId") Integer imgId) {
        return imageService.getImagePatient(imgId);
    }

    @RequestMapping(
            value = "/{id}"
            , method = RequestMethod.GET
    )
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") Integer id) throws IOException {
        RandomAccessFile f = new RandomAccessFile(imageService.getImageById(id).getImagePath(), "r");
        byte[] b = new byte[(int) f.length()];
        f.readFully(b);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);
    }

    public String getCurrentDate() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now);
    }
}
