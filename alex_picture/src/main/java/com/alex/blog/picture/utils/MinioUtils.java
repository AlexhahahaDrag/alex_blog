package com.alex.blog.picture.utils;

import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.utils.utils.FeignUtils;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *description:  minio上传工具类
 *author:       alex
 *createDate:   2021/8/13 22:08
 *version:      1.0.0
 */
@Component
@Slf4j
public class MinioUtils {

    @Autowired
    private FeignUtils feignUtils;

    /**
     * @param data
     * @description:  上传文件
     * @author:       alex
     * @return:       java.lang.String
     */
    public String uploadFile(MultipartFile data) {
        return uploadSingleFile(data);
    }

    /**
     * @param list
     * @description:  批量上传文件
     * @author:       alex
     * @return:       java.lang.String
     */
    public String batchUploadFile(List<MultipartFile> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        list.forEach(item -> {
            sb.append(uploadSingleFile(item)).append(",");
        });
        return sb.replace(sb.length() - 1, sb.length(), "").toString();
    }

    private String uploadSingleFile(MultipartFile multipartFile) {
        String url = "";

        try{
            SystemConfig systemConfig = feignUtils.getSystemConfig();
            // Make 'asiatrip' bucket if not exist.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(systemConfig.getMinioEndPoint(), systemConfig.getMinioPort(), systemConfig.getMinioSecure())
                            .credentials(systemConfig.getMinioAccessKey(), systemConfig.getMinioSecretKey())
                            .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("test").build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("test")
                            .object("11.pdf")
                            .filename("C:\\Users\\majf\\Downloads\\11.pdf")
                            .build());
            System.out.println(
                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.getMessage());
        }
        return url;
    }
}
