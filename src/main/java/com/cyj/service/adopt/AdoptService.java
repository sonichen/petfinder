package com.cyj.service.adopt;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.dto.AdoptDto;
import com.cyj.dto.AdoptQueryDto;
import com.cyj.pojo.Adopt;
import com.cyj.pojo.Adopt;
import com.cyj.utils.JsonObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface AdoptService extends IService<Adopt> {


    JsonObject findMyStar();

    JsonObject<AdoptDto> findAdopt(AdoptQueryDto adoptQueryDto );
    /**
     * 添加
     *
     * @param
     * @return int
     */
    int add(Adopt adoptDto , MultipartFile[] files ) throws Exception;

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
     * @param adopt
     * @return int
     */
    int updateData(Adopt adopt);

    /**
     * id查询数据
     *
     * @param id id
     * @return Adopt
     */
    Adopt findById(Long id);
}
