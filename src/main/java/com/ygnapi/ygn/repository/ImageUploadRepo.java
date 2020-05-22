package com.ygnapi.ygn.repository;

import com.ygnapi.ygn.model.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageUploadRepo extends JpaRepository<ImageUpload,Long> {

    List<ImageUpload> findByUserId(String userId);
}
