package com.backend.backend.Repos;

import com.backend.backend.Entity.ImageAuthHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageAuthHistoryRepo extends CrudRepository<ImageAuthHistory, Long> {


}
