package com.backend.backend.Controllers;

import com.backend.backend.Services.ImageAuthHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/private/auth/hist")
public class ImageAuthHistoryController {

    @Autowired
    private ImageAuthHistoryService imageAuthHistoryService;

}
