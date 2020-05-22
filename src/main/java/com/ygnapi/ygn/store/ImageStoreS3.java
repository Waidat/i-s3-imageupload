package com.ygnapi.ygn.store;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageStoreS3 {

    private final AmazonS3 s3;

    @Autowired
    public ImageStoreS3(AmazonS3 s3) {
        this.s3 = s3;

    }


    public void save(String bucketFilePath, String fileName, InputStream inputStream, Optional<Map<String, String>> optionalMetadata) {

        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(data -> {
            data.forEach(metadata::addUserMetadata);

        });

        try {
            s3.putObject(bucketFilePath, fileName, inputStream, metadata);
            System.out.println("Successfully File Upload");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] download(String bucketFilePath, String fileName) {


        try {
            ThreadExample1 example1 = new ThreadExample1(s3);
            example1.bucketFilePath = bucketFilePath;
            example1.fileName = fileName;
            example1.start();

            return example1.downloadS3();


        } catch (AmazonS3Exception | IOException e) {
            throw new IllegalArgumentException("File can't downlaod...", e);
        }
    }


    public URL downloadUrlS3(String bucketFilePath, String fileName) {

        URL obj = s3.getUrl(bucketFilePath, fileName);
        return obj;
    }
}


class ThreadExample1 extends Thread {

    private final AmazonS3 s3;
    public String bucketFilePath;
    public String fileName;


    ThreadExample1(AmazonS3 s3) {
        this.s3 = s3;
    }

    @SneakyThrows
    public void run() {
        downloadS3();

    }

    public byte[] downloadS3() throws IOException {

        System.out.println("Hello");
        S3Object obj = s3.getObject(bucketFilePath, fileName);
        URL object = s3.getUrl(bucketFilePath, fileName);
        System.out.println(object);
        return IOUtils.toByteArray(obj.getObjectContent());

    }

}
