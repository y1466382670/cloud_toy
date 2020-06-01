package com.yu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.entity.UserInfo;
import com.yu.request.BaseRequestId;
import com.yu.request.SelectUserInfoReq;
import com.yu.service.UserInfoService;
import com.yu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "user.info.selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "查询会员用户列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @RequestMapping(value = "user.info.register", method = RequestMethod.POST)
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
    @RequestMapping(value = "user.info.modify", method = RequestMethod.POST)
    public R updateByPrimaryKey(@RequestBody UserInfo userInfo){
        int result = this.userInfoService.updateByPrimaryKey(userInfo);
        if(result<=0){
            return R.error();
        }
        return R.ok().put("result","修改成功");
    }


    @RequestMapping(value = "user.info.delete", method = RequestMethod.POST)
    public R deleteByPrimaryKey(@RequestBody BaseRequestId requestId){
        int result = this.userInfoService.deleteByPrimaryKey(requestId.getId());
        if(result<=0){
            return R.error();
        }
        return R.ok().put("result","删除成功");
    }

    @RequestMapping(value = "user.info.getDetail", method = RequestMethod.POST)
    public R selectByPrimaryKey(@RequestBody BaseRequestId requestId){
        UserInfo userInfo = this.userInfoService.selectByPrimaryKey(requestId.getId());
        if(StringUtils.isEmpty(userInfo)){
            return R.error();
        }
        return R.ok().put("result",userInfo);
    }

}
