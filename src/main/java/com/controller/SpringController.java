package com.controller;

import com.dao.TestEntityDao;
import com.entity.User;
import com.service.HttpService;
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
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 2017/3/29.
 */

@RestController
@RequestMapping("/test")
public class SpringController extends BaseController {

    private final static String HOST_DOMAIN = "https://mplive-1253454074.file.myqcloud.com/mplive_dev/";

    @Autowired
    TestEntityDao testEntityDao;

    @Autowired
    HttpService httpService;

    @Value(value="${com.test.name}")
    private String name;

    @Value(value="${com.test.sex}")
    private String sex;

    @Value(value="${com.test.age}")
    private String age;

    @RequestMapping("/hello")
    public ModelAndView say() throws Exception {

        String str = "very nice to meet u my friend this is a very good day";
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(str.getBytes("utf-8"));
        str = toHex(bytes);
        System.out.println("MD5 = " + str);

        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
        byte[] bytes2 = messageDigest2.digest(str.getBytes("utf-8"));
        String str2 = byte2Hex(bytes2);
        System.out.println("SHA-256 = " + str2);

        //List<TestEntity> list = testEntityDao.findAll();
        ModelAndView mav = new ModelAndView("Success");
        mav.getModel().put("name", name);
        mav.addObject("sex", sex);
        return mav;
    }

    //MD5
    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    //SHA-256
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
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
                     HttpServletRequest request) throws IOException {

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

        //httpService.inputStreamUpload(file.getInputStream(), fileName);

        //String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath = "C:\\Users\\Administrator\\Desktop\\vue-manage-system-master\\static\\uploadimg\\";

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
