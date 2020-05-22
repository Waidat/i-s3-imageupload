package com.ygnapi.ygn.controller;

import com.ygnapi.ygn.model.CStores;
import com.ygnapi.ygn.repository.CStoresRepo;
import com.ygnapi.ygn.repository.ImageUploadRepo;
import com.ygnapi.ygn.response.CStoresRes;
import com.ygnapi.ygn.serviceImpl.ImageUploadS3ImplService;
import com.ygnapi.ygn.store.ImageStoreS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/spring/ygn")
@CrossOrigin("*")
public class ImageUploadController {

    @Autowired
    private ImageUploadS3ImplService implService;

    @Autowired
    private CStoresRepo repo;




    @PostMapping(
            path = "{userId}/image/upload"
    )
    public void imageUpload(@PathVariable("userId") String userId,
                            @RequestParam("file")MultipartFile multipartFile,
                            @RequestParam("brand")String brand,
                            @RequestParam("price")Integer price,
                            @RequestParam("description") String description){



        implService.uploadImageS3(userId,multipartFile,brand,price,description);



    }

    @GetMapping(path = "{userId}/image/download")
    public List<CStoresRes> downImageS3(@PathVariable("userId") String userId){

     return  implService.downloadImageS3(userId);
    }


}
