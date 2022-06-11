package com.cyj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyj.pojo.Logs;
import jdk.jfr.Registered;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2022-04-29
 */
@Repository
public interface LogsMapper extends BaseMapper<Logs> {

    List<Logs> queryLogs(Long userId);
}
