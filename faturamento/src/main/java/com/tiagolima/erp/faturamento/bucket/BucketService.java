package com.tiagolima.erp.faturamento.bucket;

import com.tiagolima.erp.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient minioClient;
    private final MinioProps minioProps;

    public void upload(BucketFile bucketFile) {
        try{
            var object = PutObjectArgs
                    .builder()
                    .bucket(minioProps.getBucketName())
                    .object(bucketFile.name())
                    .stream(bucketFile.is(), bucketFile.size(), -1)
                    .contentType(bucketFile.type().toString())
                    .build();
            minioClient.putObject(object);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String getUrl(Long codigoPedido){
        try{
            var obejct = GetPresignedObjectUrlArgs
                    .builder()
                    .method(Method.GET)
                    .bucket(minioProps.getBucketName())
                    .object(retornaNome(codigoPedido))
                    .expiry(7, TimeUnit.DAYS)
                    .build();

            return minioClient.getPresignedObjectUrl(obejct);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private String retornaNome(Long codigoPedido) {
        return String.format("nota-fiscal-%d.pdf", codigoPedido);
    }
}
