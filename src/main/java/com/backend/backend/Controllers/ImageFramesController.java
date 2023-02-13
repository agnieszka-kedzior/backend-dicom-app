package com.backend.backend.Controllers;

import com.backend.backend.Entity.ImageFrames;
import com.backend.backend.Services.ImageFramesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;

@RestController
@RequestMapping("private/f")
public class ImageFramesController {

    @Autowired
    private ImageFramesService imageFramesService;

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public ImageFrames getFirstImageFrames(@PathVariable("id") Integer id) {
        return imageFramesService.getFirstFrameByImageId(id);
    }

    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public Set<ImageFrames> getImageFrames(@PathVariable("id") Integer id) {
        return imageFramesService.getImageFrames(id);
    }

    @RequestMapping(
            value = "/id/{id}"
            , method = RequestMethod.GET
    )
    public ResponseEntity<byte[]> getFirstImageById(@PathVariable("id") Integer id) throws IOException {
        RandomAccessFile f = new RandomAccessFile(imageFramesService.getFirstFrameByImageId(id).getImageFramePath(), "r");
        byte[] b = new byte[(int) f.length()];
        f.readFully(b);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/f/{imageId}/{frame}"
            , method = RequestMethod.GET
    )
    public ResponseEntity<byte[]> getImageByIdAndFrameNr(@PathVariable("imageId") Integer id, @PathVariable("frame") Integer frame) throws IOException {
        RandomAccessFile f = new RandomAccessFile(imageFramesService.getFrameByImageIdAndFrameNumber( id, frame).getImageFramePath(), "r");
        byte[] b = new byte[(int) f.length()];
        f.readFully(b);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(b, headers, HttpStatus.CREATED);
    }
}
