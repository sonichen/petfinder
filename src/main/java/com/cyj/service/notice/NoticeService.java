package com.cyj.service.notice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.dto.NoticeDto;
import com.cyj.dto.NoticeQueryDto;
import com.cyj.pojo.Notice;
import com.cyj.pojo.Notice;
import com.cyj.utils.JsonObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface NoticeService extends IService<Notice> {



    JsonObject<NoticeDto> findNotice(NoticeQueryDto noticeQueryDto);
    /**
     * 添加
     *
     * @param
     * @return int
     */
    int add(Notice noticeDto);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改
     *
     * @param notice 
     * @return int
     */
    int updateData(Notice notice);
    int updateDataClick(Notice notice);


    /**
     * id查询数据
     *
     * @param id id
     * @return Notice
     */
    NoticeDto findById(Long id);
}
