package com.ygnapi.ygn.serviceImpl;


import com.ygnapi.ygn.bucket.BucketName;
import com.ygnapi.ygn.model.CStores;
import com.ygnapi.ygn.model.ImageUpload;
import com.ygnapi.ygn.repository.CStoresRepo;
import com.ygnapi.ygn.repository.ImageUploadRepo;
import com.ygnapi.ygn.response.CStoresRes;
import com.ygnapi.ygn.service.ImageUploadS3Service;
import com.ygnapi.ygn.store.ImageStoreS3;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.*;


@Service
public class ImageUploadS3ImplService implements ImageUploadS3Service {

    @Autowired
    private ImageStoreS3 storeS3;

    @Autowired
    private ImageUploadRepo repo;

    @Autowired
    private CStoresRepo cStoreRepo;

    private String fileName ;

    @Override
    public void uploadImageS3(String userId, MultipartFile file,String brand,Integer price,String description) {

        String bucketPath = String.format("%s/%s", BucketName.BUCKET_NAME.getBucketName(),"images");
        fileName = String.format("%s",file.getOriginalFilename());

        Map<String,String> metadata = extractMetadata(file);

        try{

            ImageUpload upload  = new ImageUpload();
            upload.setUserId(userId);
            upload.setImageName(fileName);
            repo.save(upload);


            CStores cStores = new CStores();
            cStores.setUserId(userId);
            cStores.setBrand(brand);
            cStores.setPrice(price);
            cStores.setDescription(description);
            cStores.setImageName(fileName);
            cStoreRepo.save(cStores);

            Thread thread = new Thread(){

                @SneakyThrows
                public void run(){
                    storeS3.save(bucketPath,fileName,file.getInputStream(), Optional.of(metadata));
                }
            };
            thread.start();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public  List<CStoresRes> downloadImageS3(String userId) {


                List<CStoresRes> res = new ArrayList<>();
                String bucketPath = String.format("%s/%s", BucketName.BUCKET_NAME.getBucketName(),"images");
                List<CStores> uploadList = cStoreRepo.findByUserId(userId);

                for(CStores cStores : uploadList){

                   // byte[] imageByte =  storeS3.download(bucketPath,cStores.getImageName());
                    URL url = storeS3.downloadUrlS3(bucketPath,cStores.getImageName());

                    CStoresRes res1 = new CStoresRes();


                    res1.setId(cStores.getId().intValue());
                    res1.setBrand(cStores.getBrand());
                    res1.setPrice(cStores.getPrice());
                    res1.setDescription(cStores.getDescription());
                    res1.setImageName(cStores.getImageName());
                    res1.setImageUrl(url);
                    res1.setUserId(cStores.getUserId());

                    res.add(res1);
                }
        return  res;
    }


    public Map<String,String> extractMetadata(MultipartFile file){

        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-type",file.getContentType());
        metadata.put("Content-length",String.valueOf(file.getSize()));

        return metadata;
    }



}

