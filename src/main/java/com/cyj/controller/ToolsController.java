package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;
import com.cyj.utils.Constants;
import com.cyj.utils.GetIPAddress;
import com.cyj.utils.ObjectData;
import com.cyj.utils.UploadUtils;
import com.cyj.utils.api.APIUtils;
import com.cyj.utils.constants.DevelopConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@CrossOrigin
@Api(tags = {"工具管理"})
@RestController

public class ToolsController {
    @PostMapping("/getLocation")
    public String getLocation(){
        System.out.println(APIUtils.getLocation());
        return APIUtils.getLocation();
    }
    @UserLoginToken
    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public ObjectData add(@RequestParam("file") MultipartFile file) throws Exception {
        ObjectData objectData=new ObjectData();
        File newFile= multipartFileToFile(file);
        try{
            String result= UploadUtils.upload(newFile);
            objectData.setData(result);
            objectData.setCode(Constants.OK_CODE);
        }catch (Exception e){
            objectData.setCode(Constants.FAIL_CODE);
        }
        return objectData;
    }
    public static File multipartFileToFile( @RequestParam MultipartFile file ) throws Exception {

        java.io.File toFile = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
