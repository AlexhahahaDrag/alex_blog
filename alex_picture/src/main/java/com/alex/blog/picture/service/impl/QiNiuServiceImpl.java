package com.alex.blog.picture.service.impl;

import com.alex.blog.base.global.Constants;
import com.alex.blog.common.entity.admin.SystemConfig;
import com.alex.blog.common.exception.AlexException;
import com.alex.blog.picture.service.QiNiuService;
import com.alex.blog.picture.utils.QiNiuUtils;
import com.alex.blog.utils.utils.FeignUtils;
import com.alex.blog.utils.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *description:  七牛云服务类
 *author:       alex
 *createDate:   2021/8/10 21:45
 *version:      1.0.0
 */
@Service
@RefreshScope
@Slf4j
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private FeignUtils feignUtils;

    @Value(value = "${file.upload.path}")
    private String path;

    @Autowired
    private QiNiuUtils qiNiuUtils;

    @Override
    public List<String> batchUploadFile(List<MultipartFile> multipartFileList) throws IOException {
        if (multipartFileList == null || multipartFileList.size() == 0) {
            return null;
        }
        return multipartFileList.stream().map(item -> {
            String url = null;
            try {
                url = uploadSingleFile(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;
        }).collect(Collectors.toList());
    }

    /**
     * @param multipartFile
     * @description:  上传文件到七牛云
     * @author:       alex
     * @return:       java.lang.String
    */
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return uploadSingleFile(multipartFile);
    }


    @Override
    public String uploadPictureByUrl(String pirUrl, SystemConfig systemConfig) {
        String expandedName = "jpg";
        //获取文件新名
        String newFileName = System.currentTimeMillis() + Constants.SYMBOL_POINT + expandedName;
        InputStream ios = null;
        FileOutputStream fos = null;
        File dest = null;
        String fileUrl = "";
        BufferedOutputStream out = null;
        try {
            String newPath = path + File.separator + "temp" + newFileName;
            dest = new File(newPath);
            if (!dest.getParentFile().exists()) {
                dest.mkdirs();
            }
            URL url = new URL(pirUrl);
            URLConnection con = url.openConnection();
            // 设置用户代理
            con.setRequestProperty("User-agent", "	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
            // 设置10秒
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            ios = con.getInputStream();
            byte[] bytes = new byte[1024];
            fos = new FileOutputStream(dest, true);
            int len;
            while((len = ios.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            // 将文件转化为multipartFile
            FileInputStream fileInputStream = new FileInputStream(dest);
            MultipartFile fileData = new MockMultipartFile(dest.getName(),
                    dest.getName(), String.valueOf(ContentType.APPLICATION_OCTET_STREAM), fileInputStream);
            out = new BufferedOutputStream(new FileOutputStream(dest));
            out.write(fileData.getBytes());
            fileUrl = qiNiuUtils.uploadQiniu(dest, systemConfig);
        } catch (AlexException | IOException e) {
            throw new AlexException("0200", "获取文件超时，文件上传失败");
        } finally {
            try {
                if (dest != null) {
                    dest.delete();
                }
                if (ios != null) {
                    ios.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }

    /**
     * @param multipartFile
     * @description:  七牛服务图片上传
     * @author:       alex
     * @return:       java.lang.String
     */
    private String uploadSingleFile(MultipartFile multipartFile) throws IOException {
        File dest = null;
        BufferedOutputStream bos = null;
        String url = "";
        SystemConfig systemConfig = feignUtils.getSystemConfig();
        String oldName = multipartFile.getOriginalFilename();
        String picExpandedName = FileUtils.getPicExpandedName(oldName);
        //获取新文件名
        String newFileName = System.currentTimeMillis() + Constants.SYMBOL_POINT + picExpandedName;
        //创建一个临时路径
        String tempFiles = path + "/temp" + newFileName;
        try {
            dest = new File(tempFiles);
            if(!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            bos.write(multipartFile.getBytes());
            bos.flush();
            url = qiNiuUtils.uploadQiniu(dest, systemConfig);
        } catch (Exception e) {
            log.error("文件上传七牛云失败， {}", e.getMessage());
        } finally {
            if (dest != null && dest.getParentFile().exists()) {
                dest.delete();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return url;
    }
}
