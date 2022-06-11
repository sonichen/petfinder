package com.cyj.mapper;

import com.cyj.dto.AdoptDto;
import com.cyj.dto.AdoptQueryDto;
import com.cyj.pojo.Adopt;
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
public interface AdoptMapper extends BaseMapper<Adopt> {
    List<AdoptDto> queryAdoptsLists(AdoptQueryDto adoptQueryDto );
    Adopt queryByMark(String mark);
    AdoptDto queryMyLike(String id);
}
