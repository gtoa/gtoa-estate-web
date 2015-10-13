package com.renren.gota.webserver.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.renren.gota.webserver.model.User;

/**
 * 文件服务，定义文件上传，下载，请求文件列表的功能
 * 
 * @author zhanzhan.qi@renren-inc.com 2015年10月9日 下午4:20:20
 */
public interface FileService {

    /**
     * 列出用户所有的文件
     */
    public List<String> listFiles(User user);

    /**
     * 上传文件到一个用户的文件夹下
     */
    public boolean uploadFile(User user, MultipartFile file);

    /**
     * 删除用户文件夹下的fileName文件
     */
    public boolean deleteFile(User user, String fileName);

    /**
     * 下载用户指定的文件
     */
    public void downloadFile(User user, String fileName, HttpServletResponse response);

}
