package com.backend.backend.Services;

import com.backend.backend.Repos.ImageAuthHistoryRepo;
import com.backend.backend.Repos.ImageRepo;
import com.backend.backend.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageAuthHistoryService {

    @Autowired
    private ImageAuthHistoryRepo imageAuthHistoryRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private UsersRepo usersRepo;

}
