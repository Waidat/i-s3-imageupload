package com.ygnapi.ygn.serverS3;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.auth.AWSCredentials;

@Configuration
public class AmazonConfig {


  @Bean
  public AmazonS3 s3() {
    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAXCM3JFSXMNIBDF5D",
            "H3YeFyM/2076ucCfqNq0YKnU95piTUyqn+/XDGMu"
    );

    return new AmazonS3Client(credentials);
  }
}
