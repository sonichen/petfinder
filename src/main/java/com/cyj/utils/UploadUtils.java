package com.cyj.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class UploadUtils {

    public static String upload(File imgFile)   {
       try{
           byte [] file=getBytesByFile(imgFile.getAbsolutePath());
           System.out.println(imgFile.getAbsolutePath());
           String  filePath="http://8.130.8.164:8080/img/re/resource/";
           //            设置唯一的id
           long n = (long)(Math.random() % 29) + 1;
           long n1 = (long)(Math.random() % 29) + 1;
           String fileName= "petFinder_"+(new SnowflakeIdUtils(n,n1).nextId())+".jpg";
           return uploadFile(file,filePath,fileName);
       }catch (Exception e){
           return "上传失败";
       }
    }

    /**
     * 上传到文件服务器
     * @author: yaco
     * @Date  : 2019年7月18日 下午4:26:57
     */
    public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        Client client = new Client();
        WebResource resource = client.resource(filePath + fileName);
        resource.put(String.class, file);
        return filePath + fileName;
    }

    public static void main(String[] args) throws Exception {
//       byte[] file=getBytesByFile("F:\\test.png");
//        String result=uploadFile(file,"http://8.130.8.164:8080/img/infuzhou/food/","test.png");
    }
    //将文件转换成Byte数组
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        System.out.println("文件大小为: " + file.length());
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
