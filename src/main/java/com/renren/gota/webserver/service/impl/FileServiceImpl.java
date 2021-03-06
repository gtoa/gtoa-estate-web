package com.renren.gota.webserver.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.FileService;

/**
 * @author zhanzhan.qi@renren-inc.com 2015年10月9日 下午4:24:28
 */
@Service
public class FileServiceImpl implements FileService {

    private static Log LOG = LogFactory.getLog(FileServiceImpl.class);
    private static S3Service s3Service;
    private static final String BUCKET_NAME = "bucket.userfiles";

    static {
        AWSCredentials credentials =
                new AWSCredentials("AKIAIWRFYAXKDNBUVQ4Q", "5oNYmcrDhKxAnq1J/S+3ELq26y3BSWYb3PRETJXa");
        s3Service = new RestS3Service(credentials);
    }

    @Override
    public List<String> listFiles(User user) {
        List<String> list = new ArrayList<String>();
        if (null == user) {
            return list;
        }

        try {
            String prefix = getUserFilePrefix(user);
            if (null == prefix || "".equals(prefix)) {
                return list;
            }
            String delimiter = null;
            S3Object[] objects = s3Service.listObjects(BUCKET_NAME, prefix, delimiter);
            for (S3Object obj : objects) {
                String fileName = removePrefix(obj.getName(), prefix);
                list.add(fileName);
            }
        } catch (S3ServiceException e) {
            LOG.error("get objects from s3 error", e);
        }

        return list;
    }

    @Override
    public boolean uploadFile(User user, MultipartFile file) {
        if (null == user || null == file) {
            return false;
        }

        String prefix = getUserFilePrefix(user);
        String fileName = prefix + file.getOriginalFilename();

        boolean uploadResult = false;
        S3Object object = new S3Object(fileName);
        try {
            object.setDataInputStream(file.getInputStream());
            object.setContentLength(file.getSize());
            object.setContentType(file.getContentType());
            object = s3Service.putObject(BUCKET_NAME, object);
            if (null == object) {
                uploadResult = false;
            } else {
                uploadResult = true;
            }
        } catch (Exception e) {
            LOG.error("uploadFile exception", e);
        }

        return uploadResult;
    }

    @Override
    public void downloadFile(User user, String fileName, HttpServletResponse response) {
        if (null == user || StringUtils.isBlank(fileName) || null == response) {
            return;
        }

        String prefix = getUserFilePrefix(user);
        String objectKey = prefix + fileName;
        try {
            S3Object s3Object = s3Service.getObject(BUCKET_NAME, objectKey);
            if (null == s3Object || s3Object.getDataInputStream() == null) {
                return;
            }
            InputStream in = s3Object.getDataInputStream();
            response.setContentType(s3Object.getContentType());
            response.setHeader("Content-Disposition", "attachment;fileName="
                                                      + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            if (null == in) {
                return;
            }
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[1024 * 1024];
            int len;
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            in.close();

        } catch (Exception e) {
            LOG.error("get object from s3 error", e);
        }
    }

    @Override
    public boolean deleteFile(User user, String fileName) {

        if (null == user || StringUtils.isBlank(fileName)) {
            return false;
        }

        String prefix = getUserFilePrefix(user);
        if (StringUtils.isBlank(prefix)) {
            return false;
        }

        String objectKey = prefix + fileName;
        try {
            s3Service.deleteObject(BUCKET_NAME, objectKey);
        } catch (ServiceException e) {
            LOG.error("delete file in s3 error", e);
            return false;
        }
        return true;
    }

    private static String getUserFilePrefix(User user) {
        int id = user.getId();
        return "user_" + id + "/";
    }

    public static String removePrefix(String str, String prefix) {
        int index = prefix.length();
        String newStr = str.substring(index, str.length());
        return newStr;
    }

}
