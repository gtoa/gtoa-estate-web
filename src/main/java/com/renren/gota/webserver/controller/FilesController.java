package com.renren.gota.webserver.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jets3t.service.S3ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.renren.gota.webserver.common.annotation.LoginRequired;
import com.renren.gota.webserver.model.User;
import com.renren.gota.webserver.service.FileService;

@Controller
public class FilesController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/showfiles", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @LoginRequired
    public ModelAndView showFileList(HttpServletRequest request) throws S3ServiceException {
        ModelAndView mv = new ModelAndView("file");
        User user = (User) request.getAttribute("user");
        List<String> fileList = fileService.listFiles(user);
        mv.addObject("fileList", fileList);
        return mv;
    }

    @RequestMapping(value = "/deletefile", produces = "application/json;charset=UTF-8")
    @LoginRequired
    public String deleteFile(HttpServletRequest request, @RequestParam String fileName) {
        User user = (User) request.getAttribute("user");
        fileService.deleteFile(user, fileName);
        return "redirect:/showfiles";
    }

    @RequestMapping(value = "downloadfile", method = RequestMethod.GET)
    @LoginRequired
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileName)
        throws IOException {
        User user = (User) request.getAttribute("user");
        fileService.downloadFile(user, fileName, response);
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @LoginRequired
    public String uploadFile(HttpServletRequest request,
        @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        User user = (User) request.getAttribute("user");
        fileService.uploadFile(user, file);
        return "redirect:/showfiles";
    }
}
