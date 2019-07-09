package io.jboot.admin.support.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AmazonS3Utils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonS3 amazonS3;

    public AmazonS3Utils(String accessKey, String secretKey, String region){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withClientConfiguration(new ClientConfiguration().withConnectionTimeout(10000));
        this.amazonS3 = builder.build();
    }

    public AmazonS3 getAmazonS3() {
        return amazonS3;
    }

    public void createBucket(String bucketName){
        amazonS3.createBucket(bucketName);
    }

    public ObjectListing listFiles(String bucketName){
        return amazonS3.listObjects(bucketName);
    }

    public void writeFile(String bucketName, String fileName, String payload){
        amazonS3.putObject(bucketName,fileName,payload);
    }
}
