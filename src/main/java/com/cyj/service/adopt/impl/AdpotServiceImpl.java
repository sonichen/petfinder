package com.cyj.service.adopt.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cyj.dto.AdoptDto;
import com.cyj.dto.AdoptQueryDto;
import com.cyj.mapper.AdoptMapper;
import com.cyj.mapper.ImagesMapper;
import com.cyj.mapper.OthersMapper;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Adopt;
import com.cyj.pojo.Images;
import com.cyj.pojo.Others;
import com.cyj.pojo.User;
import com.cyj.service.adopt.AdoptService;

import com.cyj.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
@Service
public class AdpotServiceImpl extends ServiceImpl<AdoptMapper, Adopt> implements AdoptService {

    @Autowired
    AdoptMapper adoptMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ImagesMapper imagesMapper;
    @Autowired
    OthersMapper othersMapper;

    @Override
    public JsonObject findMyStar() {
        String userId=TokenUtil.getTokenUserId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("userId",userId);
        wrapper.eq("module","adopt");
        wrapper.eq("type","star");
        wrapper.eq("value","1");
        List<Others> list=othersMapper.selectList(wrapper);
        List result=new ArrayList();
        for(int i=0;i<list.size();i++){
             AdoptDto adoptDto=adoptMapper.queryMyLike(list.get(i).getPostId()+"");
             adoptDto.setUsername(userMapper.selectById(list.get(i).getUserId()).getUsername());
            List<String> paths=imagesMapper.queryImagesPath(list.get(i).getPostId()+"");
           adoptDto.setImages(paths);
           result.add(adoptDto);
        }
        JsonObject jsonObject=new JsonObject();
        jsonObject.setData(result);
        jsonObject.setCount((long)result.size());
        return jsonObject;
    }

    @Override
    public JsonObject<AdoptDto> findAdopt(AdoptQueryDto adoptQueryDto ) {
        Long userId=0l;
        try{
          userId=  Long.valueOf(TokenUtil.getTokenUserId());
        }catch (Exception e){
            userId=0l;
        }
        if(adoptQueryDto.getKeyword()!=null){
             adoptQueryDto.setKeyword("%"+adoptQueryDto.getKeyword()+"%");
        }
        adoptQueryDto.setPage(adoptQueryDto.getPagesize()*(adoptQueryDto.getPage()-1));
        List<AdoptDto> adopt=adoptMapper.queryAdoptsLists(adoptQueryDto);
        for(int i=0;i<adopt.size();i++){
            adopt.get(i).setUsername(userMapper.selectById(adopt.get(i).getUserId()).getUsername());
            List<String> paths=imagesMapper.queryImagesPath(adopt.get(i).getId()+"");
            adopt.get(i).setImages(paths);
            if(userId!=0){
                QueryWrapper wrapper=new QueryWrapper();
                wrapper.eq("userId",userId+"");
                wrapper.eq("postId",adopt.get(i).getId());
                wrapper.eq("module","adopt");
                Others others=othersMapper.selectOne(wrapper);
                adopt.get(i).setStar(others.getValue());
            }
        }
        JsonObject<AdoptDto> jsonObject=new JsonObject<>();
        jsonObject.setCount((long) adopt.size());
        jsonObject.setData(adopt);
        return jsonObject;
    }

    @Override
    public int add(Adopt adopt, MultipartFile[] files  ) throws Exception {
        String mark=System.currentTimeMillis()+"";
//        adopt.setCategoriesId();
        if(adopt.getCategoriesId()==0){
            adopt.setCategoriesId(12l);
        }
        adopt.setUserId(Long.valueOf(TokenUtil.getTokenUserId()));
        adopt.setStatus("0");
        adopt.setMark(mark);
         int result=baseMapper.insert(adopt);

         Adopt adopt1=adoptMapper.queryByMark(mark);

         for(int i=0;i<files.length;i++){
             File newFile= multipartFileToFile(files[i]);
                 String results= UploadUtils.upload(newFile);
             imagesMapper.insert(new Images(adopt1.getId(),results,"adopt"));
         }
        /**
         * 收藏
         */
        List<User> list=userMapper.selectList(null);
        for(int i=0;i<list.size();i++){
            Others others=new Others();
            others.setUserId(list.get(i).getId());
            others.setValue(0l);
            others.setModule("adopt");
            others.setType("star");
            others.setPostId(adopt1.getId());
            othersMapper.insert(others);
        }
        return  result;
    }

    @Override
    public int delete(Long id){
        System.out.println("要删除="+id);
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Adopt adopt){
        return baseMapper.updateById(adopt);
    }

    @Override
    public Adopt findById(Long id){
        return  baseMapper.selectById(id);
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
