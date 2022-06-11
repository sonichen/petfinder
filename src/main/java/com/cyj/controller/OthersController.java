//package com.cyj.controller;
//
//import com.cyj.annotation.UserLoginToken;
//import com.cyj.pojo.Others;
//import com.cyj.service.others.OthersService;
//import com.cyj.utils.Constants;
//import com.cyj.utils.JsonObject;
//import com.cyj.utils.R;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author cyj
// * @since 2022-05-09
// */
//@Api(tags = {"领养"})
//@RestController
//@UserLoginToken
//@RequestMapping("/others")
//public class OthersController {
//
//    private Logger log = LoggerFactory.getLogger(getClass());
//
//    @Resource
//    private OthersService othersService;
//
//    @ApiOperation(value = "查询领养")
//    @PostMapping("/findOthers")
//    public JsonObject<Others> findOthers(){
//        JsonObject jsonObject=null;
//        try{
//            jsonObject=othersService.queryOthersList();
//            jsonObject.setCode(Constants.OK_CODE);
//        }catch (Exception e){
//            e.printStackTrace();
//            jsonObject.setCode(Constants.FAIL_CODE);
//        }
//        return jsonObject;
//    }
//
//    @ApiOperation(value = "发起收养")
//    @PostMapping("/add")
//    public R add(@RequestBody Others others){
//       try{
//            othersService.add(others);
//            return R.ok();
//       }catch (Exception e){
//           return R.fail(Constants.FAIL_MSG);
//       }
//    }
//
//    @ApiOperation(value = "结束领养")
//    @PostMapping("/delete")
//    public R delete( Long id){
//        System.out.println("要删除="+id);
//        try{
//            othersService.delete(id);
//            return R.ok();
//        }catch (Exception e){
//            return R.fail(Constants.FAIL_MSG);
//        }
//    }
//
//    @ApiOperation(value = "更新信息")
//    @PostMapping("/update")
//    public R update(@RequestBody Others others){
//        try{
//            othersService.updateData(others);
//            return R.ok();
//        }catch (Exception e){
//            return R.fail(Constants.FAIL_MSG);
//        }
//    }
//
//
//}
