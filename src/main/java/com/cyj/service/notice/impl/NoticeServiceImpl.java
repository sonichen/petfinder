package com.cyj.service.notice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dto.NoticeDto;
import com.cyj.dto.NoticeQueryDto;
import com.cyj.mapper.CategoriesMapper;
import com.cyj.mapper.NoticeMapper;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Notice;
import com.cyj.pojo.Post;
import com.cyj.pojo.User;
import com.cyj.service.notice.NoticeService;
import com.cyj.utils.JsonObject;
import com.cyj.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CategoriesMapper categoriesMapper;

    @Override
    public JsonObject<NoticeDto> findNotice(NoticeQueryDto noticeQueryDto) {
        Page<Notice> page=new Page<>(noticeQueryDto.getPage(),noticeQueryDto.getPageSize());
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("categoriesId" , noticeQueryDto.getCategoriesId());
        List<Notice> notice=null;
        if(noticeQueryDto.getCategoriesId()==null){
            baseMapper.selectPage(page,null);
        }else{
            baseMapper.selectPage(page,wrapper);
        }
        notice= page.getRecords();
        List<NoticeDto> list=new ArrayList<>();
        for (int i=0;i<notice.size();i++){
            Notice temp=notice.get(i);
            String username=userMapper.selectById(temp.getUserId()).getUsername();
            String categoriesName=categoriesMapper.selectById(temp.getCategoriesId()).getValue();
            list.add(new NoticeDto(temp.getId(),username,categoriesName,temp.getTitle(),temp.getContent(),
                    temp.getCreateTime(),temp.getVersion(),temp.getStar(),0l));
        }
        System.out.println(list);
        JsonObject<NoticeDto> jsonObject=new JsonObject<>();
        jsonObject.setCount((long) notice.size());
        jsonObject.setData(list);
        return jsonObject;
    }

    @Override
    public int add(Notice notice){
        notice.setClick(0l);
        notice.setStar(0l);
        return baseMapper.insert(notice);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Notice notice){
        return baseMapper.updateById(notice);
    }
    @Override
    public int updateDataClick(Notice notice){
        Notice notice1=baseMapper.selectById(notice);
        return baseMapper.updateById(notice1);
    }
    @Override
    public NoticeDto findById(Long id){
        Notice notice=  baseMapper.selectById(id);
        NoticeDto noticeDto=new NoticeDto(id,userMapper.selectById(notice.getUserId()).getUsername(),
                categoriesMapper.selectById(notice.getCategoriesId()).getValue(),notice.getTitle(),notice.getContent(),
                notice.getCreateTime(),notice.getVersion(),notice.getStar(),0l);
        return noticeDto;
    }
}
