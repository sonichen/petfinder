package com.cyj.service.logs;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.pojo.Logs;
import com.cyj.utils.JsonObject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface LogsService extends IService<Logs> {


    /**
     * 标签列表
     * @param
     * @return
     */
    JsonObject<Logs> queryLogsList( );

    /**
     * 添加
     *
     * @param logs 
     * @return int
     */
    int add(Logs logs);

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
     * @param logs 
     * @return int
     */
    int updateData(Logs logs);

    /**
     * id查询数据
     *
     * @param id id
     * @return Logs
     */
    Logs findById(Long id);
}
