package com.moensun.spring.boot.cloud.integration.other.minio;

import com.moensun.csi.api.oss.request.OssListObjectsRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@RequiredArgsConstructor
@SpringBootTest
@ActiveProfiles("test")
public class MinioTest {

    @Autowired
    private DefaultMinioClient  minioClient;

    @Test
    public void minio_test(){
        OssListObjectsRequest request = OssListObjectsRequest.builder().maxKeys(10).build();
        minioClient.listObjects(request);
    }

}
