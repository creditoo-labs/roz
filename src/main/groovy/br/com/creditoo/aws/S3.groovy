package br.com.creditoo.aws

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectResult

class S3 {

    Map upload(String region, String bucket, String path, byte[] content) {
        AmazonS3Client client = AmazonS3ClientBuilder.standard().withRegion(region).build()

        ObjectMetadata objectMetadata = new ObjectMetadata()
        objectMetadata.setContentLength(content.length)
        PutObjectResult result = client.putObject(bucket, path, new ByteArrayInputStream(content), objectMetadata)

        return [path: "s3://${bucket}/${path}", result: result]
    }
}
