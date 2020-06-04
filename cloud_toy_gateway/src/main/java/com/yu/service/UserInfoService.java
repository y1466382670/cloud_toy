package com.yu.service;

import com.yu.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    /**
     * 查询所有会员用户
     * @return
     */
    List<UserInfo> selectAll();

    /**
     * 注册（新增）用户
     * @param userInfo
     * @return
     */
    int registerUser(UserInfo userInfo);

    /**
     * 用户信息修改
     * @param userInfo
     * @return
     */
    int updateByPrimaryKey(UserInfo userInfo);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteByPrimaryKey(int id);

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    UserInfo selectByPrimaryKey(int id);

    /**
     * 根据用户手机号查询用户详情
     * @param phone
     * @return
     */
    UserInfo selectByPhone(String phone);

    /**
     * 根据token 查询用户信息
     * @param token
     * @return
     */
    UserInfo getUserAccountByToken(String token);
}
