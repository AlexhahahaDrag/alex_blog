package com.alex.blog.picture.service.impl;

import com.alex.blog.base.global.Constants;
import com.alex.blog.common.entity.file.FileSort;
import com.alex.blog.common.exception.AlexException;
import com.alex.blog.picture.service.FileSortService;
import com.alex.blog.picture.service.LocalService;
import com.alex.blog.utils.utils.DateUtils;
import com.alex.blog.utils.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *description:
 *author:       alex
 *createDate:   2021/8/10 6:40
 *version:      1.0.0
 */
@Service
@Slf4j
@RefreshScope
public class LocalServiceImpl implements LocalService {

    @Autowired
    private FileSortService fileSortService;

    @Value(value = "${file.upload.path}")
    private String path;

    @Override
    public List<String> batchUploadFile(List<MultipartFile> multipartFileList, FileSort fileSort) throws IOException {
        return multipartFileList.stream().map(item -> {
            String url = null;
            try {
                url = uploadSingleFile(item, fileSort);
            } catch (IOException e) {
                log.error("上传文件失败:{}", e.getMessage());
                e.printStackTrace();
            }
            return url;
        }).collect(Collectors.toList());
    }

    /**
     * @param multipartFile
     * @param fileSort
     * @description:  上传文件
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String uploadFile(MultipartFile multipartFile, FileSort fileSort) throws IOException {
        return uploadSingleFile(multipartFile, fileSort);
    }

    /**
     * @param pirUrl
     * @param fileSort
     * @description:  通过url上传文件
     * @author:       alex
     * @return:       java.lang.String
     */
    @Override
    public String uploadPictureByUrl(String pirUrl, FileSort fileSort) {
        String expandedName = "jpg";
        String sortUrl = fileSort.getUrl() == null ? "base/common" : fileSort.getUrl();
        //获取文件新名
        String newFileName = System.currentTimeMillis() + Constants.SYMBOL_POINT + expandedName;
        //获取文件路径
        String newPath = getFileAbsolutePath(sortUrl, expandedName);
        String fileUrl = newPath + newFileName;
        String saveUrl = newPath + newFileName;
        File file = new File(newPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream ios = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(pirUrl);
            URLConnection con = url.openConnection();
            // 设置用户代理
            con.setRequestProperty("User-agent", "	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
            // 设置10秒
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            ios = con.getInputStream();
            byte[] bytes = new byte[1024];
            File saveFile = new File(saveUrl);
            fos = new FileOutputStream(saveFile, true);
            int len;
            while((len = ios.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (AlexException | IOException e) {
            throw new AlexException("0200", "获取文件超时，文件上传失败");
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }

    private String getFileAbsolutePath(String sortUrl, String expandedName) {
        return path + sortUrl + File.separator + expandedName + File.separator + DateUtils.getTimeStr(LocalDateTime.now(), DateUtils.YYYYMMDD) + File.separator;
    }


    /**
     * @param multipartFile
     * @param fileSort
     * @description:  上传单一文件
     * @author:       alex
     * @return:       java.lang.String
     */
    private String uploadSingleFile(MultipartFile multipartFile, FileSort fileSort) throws IOException {
        String sortUrl = fileSort.getUrl() == null ? "base/common" : fileSort.getUrl();
        String oldName = multipartFile.getOriginalFilename();
        //获取文件扩展名，默认jpg
        String picExpandedName = FileUtils.getPicExpandedName(oldName);
        //获取文件新名
        String newFileName = System.currentTimeMillis() + Constants.SYMBOL_POINT + picExpandedName;
        //获取文件路径
        String newPath = getFileAbsolutePath(sortUrl, picExpandedName);
        String picUrl = newPath + newFileName;
        String saveUrl = newPath + newFileName;
        File file = new File(newPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File saveFile = new File(saveUrl);
        saveFile.createNewFile();
        multipartFile.transferTo(saveFile);
        return picUrl;
    }
}
