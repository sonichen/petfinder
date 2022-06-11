package com.cyj.utils;



import ch.qos.logback.core.util.FileUtil;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tomcat.jni.File;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.io.*;

public class DownloadTools {
    public static void main(String[] args) {

    }
//    Resource resource
    public static void downloadWord(HttpServletResponse response,String title,String text) {
//        String title = resource.getTitle();
//        String text = resource.getContent();
//        String title="测试";
//        String text="snowflflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为 毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID），12bit作为毫秒内的流水号（意味 着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。可以保证几乎全球唯 一！";
        try {
            //word内容
            String content="<html><body>" +
                    "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-size: 24px;\">"
                    + title + "</span></p>" + text + "</body></html>";
            byte b[] = content.getBytes("GBK");  //这里是必须要设置编码的，不然导出中文就会乱码。
            ByteArrayInputStream bais = new ByteArrayInputStream(b);//将字节数组包装到流中

            /*
             * 关键地方
             * 生成word格式 */
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            //输出文件
//            request.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");//导出word格式
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(title.getBytes("GB2312"),"iso8859-1") + ".doc");
            ServletOutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
            poifs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
