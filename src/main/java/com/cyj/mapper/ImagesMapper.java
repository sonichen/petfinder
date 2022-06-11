package com.cyj.mapper;

import com.cyj.pojo.Images;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2022-05-09
 */
@Repository
public interface ImagesMapper extends BaseMapper<Images> {
List<String> queryImagesPath(String workId);
}
