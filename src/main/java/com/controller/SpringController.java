package com.controller;

import com.dao.TestEntityDao;
import com.entity.User;
import com.util.MyFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by User on 2017/3/29.
 */

@RestController
@RequestMapping("/test")
public class SpringController extends BaseController {

    @Autowired
    TestEntityDao testEntityDao;

    @Value(value="${com.test.name}")
    private String name;

    @Value(value="${com.test.sex}")
    private String sex;

    @Value(value="${com.test.age}")
    private String age;

    @RequestMapping("/hello")
    public ModelAndView say(){
        //List<TestEntity> list = testEntityDao.findAll();
        ModelAndView mav = new ModelAndView("Success");
        mav.getModel().put("name", name);
        mav.addObject("sex", sex);
        return mav;
    }

    @RequestMapping("/testconfig")
    public User testConfig(){
        System.out.println(name + sex + age);
        User user = new User();
        user.setName(name);
        user.setAge(Integer.parseInt(age));
        user.setSex(sex);
        return user;
    }

    @RequestMapping("/fail")
    public ModelAndView jumpToFail(){
        ModelAndView mav = new ModelAndView("Fail");
        return mav;
    }

    //跳转到上传文件的页面
    @RequestMapping(value="/gouploadimg", method = RequestMethod.GET)
    public ModelAndView goUploadImg() {
        return new ModelAndView("uploadimg");
    }

    //处理文件上传
    @RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
    public String uploadImg(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request){

        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String name = file.getName();

        // 特殊情况有 * ^ : | . \  这些都需要转义
        String[] splitImageType = fileName.split("\\.");
        String fileNameNew = "abc"+"."+splitImageType[1];

        System.out.println("fileName-->" + fileName);
        System.out.println("ContentType-->" + contentType);
        System.out.println("Name-->" + name);
        System.out.println("fileNameNew-->" + fileNameNew);

        if(!contentType.contains("image")){
            return "This is not a image";
        }

        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        //String filePath = "C:\\Users\\Administrator\\Desktop\\vue-manage-system-master\\static\\uploadimg\\";

        try {
            MyFileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            System.out.println(e);
        }

        return "uploadimg success!!" + filePath;
    }

    @RequestMapping(value = "/testdownload")
    public ResponseEntity<FileSystemResource> listExport(String proNo) {
        File file = new File("D:\\workingFiles\\test.txt");
        return export(file);
    }
}
