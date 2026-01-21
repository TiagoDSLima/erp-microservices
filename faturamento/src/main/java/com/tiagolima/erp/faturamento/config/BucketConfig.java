package com.tiagolima.erp.faturamento.config;

import com.tiagolima.erp.faturamento.config.props.MinioProps;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    @Autowired
    MinioProps minioProps;

    @Bean
    public MinioClient bucketClient(){
        return MinioClient.builder()
                .endpoint(minioProps.getUrl())
                .credentials(minioProps.getAccessKey(), minioProps.getSecretKey())
                .build();
    }
}
