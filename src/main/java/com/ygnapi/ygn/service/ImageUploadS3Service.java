package com.ygnapi.ygn.service;

import com.ygnapi.ygn.response.CStoresRes;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploadS3Service {

    public void uploadImageS3(String userId, MultipartFile file,String brand,Integer price,String description);

    public List<CStoresRes> downloadImageS3(String userId);
}
