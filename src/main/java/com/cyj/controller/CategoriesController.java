package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;
import com.cyj.service.categories.CategoriesService;
import com.cyj.utils.Constants;
import com.cyj.utils.JsonObject;
import com.cyj.utils.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;
import com.cyj.pojo.Categories;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2022-05-09
 */
@CrossOrigin
@Api(tags = {"分类管理"})
@RestController
@UserLoginToken
@RequestMapping("/categories")
public class CategoriesController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private CategoriesService categoriesService;

    @ApiOperation(value = "查询分类",notes = "notice,adopt,community")
//    @UserLoginToken
    @PostMapping("/findCategories")
    public JsonObject<Categories> findCategories(String module){
        JsonObject jsonObject=null;
        try{
            jsonObject=categoriesService.findCategories(module);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }

    @ApiOperation(value = "新增分类",notes = "module写模块，value写名字，description写备注")
    @UserLoginToken
    @PostMapping("/add")
    public R add(@RequestBody Categories categories){
       try{
            categoriesService.add(categories);
            return R.ok();
       }catch (Exception e){
           return R.fail(Constants.FAIL_MSG);
       }
    }

    @ApiOperation(value = "删除")
    @UserLoginToken
    @PostMapping("/delete")
    public R delete(@PathVariable("id") Long id){
        try{
            categoriesService.delete(id);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }

    @ApiOperation(value = "更新")
    @UserLoginToken
    @PostMapping("/update")
    public R update(@RequestBody Categories categories){
        try{
            categoriesService.updateData(categories);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }


}
