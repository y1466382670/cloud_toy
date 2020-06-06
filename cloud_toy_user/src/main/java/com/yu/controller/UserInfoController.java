package com.yu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.entity.UserInfo;
import com.yu.request.SelectUserInfoReq;
import com.yu.service.UserInfoService;
import com.yu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "UserInfoController", description = "会员用户管理")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询会员用户列表
     * @param userInfoReq
     * @return
     */
    @RequestMapping(value = "/user.info.selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.查询会员列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public R selectAll(@RequestBody SelectUserInfoReq userInfoReq){
        PageHelper.startPage(userInfoReq.getPageNo(),userInfoReq.getPageSize());
        List<UserInfo> list = userInfoService.selectAll();
        PageInfo<UserInfo> pageInfo = new PageInfo<>(list);
        return R.ok().put("result",pageInfo);
    }

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/user.info.register", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.会员注册", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R registerUser(@RequestBody UserInfo userInfo){
        int result = this.userInfoService.registerUser(userInfo);
        if(result<=0){
            return R.error();
        }
        return R.ok().put("result","注册成功");
    }

    /**
     * 用户信息修改
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/user.info.modify", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.修改会员信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R updateByPrimaryKey(@RequestBody UserInfo userInfo){
        int result = this.userInfoService.updateByPrimaryKey(userInfo);
        if(result<=0){
            return R.error();
        }
        return R.ok().put("result","修改成功");
    }


    @RequestMapping(value = "/user.info.delete", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.删除会员", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R deleteByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        int result = this.userInfoService.deleteByPrimaryKey(id);
        if(result<=0){
            return R.error();
        }
        return R.ok().put("result","删除成功");
    }

    @RequestMapping(value = "/user.info.getDetail", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.根据用户id查询会员信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R selectByPrimaryKey(
            @ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true) int id
    ){
        UserInfo userInfo = this.userInfoService.selectByPrimaryKey(id);
        if(StringUtils.isEmpty(userInfo)){
            return R.error();
        }
        return R.ok().put("result",userInfo);
    }

    @RequestMapping(value = "/user.info.selectInfoByPhone", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.根据手机号查询会员详情", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R selectInfoByPhone(
            @ApiParam(value = "phone", required = true) @RequestParam(value = "phone", required = true) String phone
    ){
        UserInfo userInfo = this.userInfoService.selectByPhone(phone);
        if(StringUtils.isEmpty(userInfo)){
            return R.error();
        }
        return R.ok().put("result",userInfo);
    }

    @RequestMapping(value = "/user.info.selectInfoByToken", method = RequestMethod.POST)
    @ApiOperation(value = "会员管理.根据token查询会员", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public R selectInfoByToken(
            @ApiParam(value = "token", required = true) @RequestParam(value = "token", required = true) String token
    ){
        UserInfo userInfo = this.userInfoService.selectInfoByToken(token);
        if(StringUtils.isEmpty(userInfo)){
            return R.error();
        }
        return R.ok().put("result",userInfo);
    }

}
