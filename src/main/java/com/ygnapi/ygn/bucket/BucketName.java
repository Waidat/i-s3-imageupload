package com.ygnapi.ygn.bucket;

public enum  BucketName {

    BUCKET_NAME("wai");

    private String bucketName ;

    BucketName(String wai) {
        this.bucketName  = wai;
    }

    public String getBucketName() {
        return bucketName;
    }
}
