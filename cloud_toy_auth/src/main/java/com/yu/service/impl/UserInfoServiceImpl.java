package com.yu.service.impl;

import com.yu.dao.UserInfoMapper;
import com.yu.entity.UserInfo;
import com.yu.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 查询所有会员用户
     * @return
     */
    @Override
    public List<UserInfo> selectAll() {
        return this.userInfoMapper.selectAll();
    }

    /**
     * 注册用户
     * @param userInfo
     * @return
     */
    @Transactional
    @Override
    public int registerUser(UserInfo userInfo) {
        int result = this.userInfoMapper.insert(userInfo);
        return result;
    }

    /**
     * 用户信息修改
     * @param userInfo
     * @return
     */
    @Transactional
    @Override
    public int updateByPrimaryKey(UserInfo userInfo) {
        int result = this.userInfoMapper.updateByPrimaryKey(userInfo);
        return result;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int deleteByPrimaryKey(int id) {
        int result = this.userInfoMapper.deleteByPrimaryKey(id);
        return result;
    }

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    @Override
    public UserInfo selectByPrimaryKey(int id) {
        UserInfo userInfo = this.userInfoMapper.selectByPrimaryKey(id);
        return userInfo;
    }

    /**
     * 根据手机号查询用户详情
     * @param phone
     * @return
     */
    @Override
    public UserInfo selectByPhone(String phone) {
        UserInfo userInfo = this.userInfoMapper.selectByPhone(phone);
        return userInfo;
    }

    /**
     * 修改用户token
     * @param id 主键
     * @param token
     * @return
     */
    @Override
    public boolean updateUserToken(int id, String token) {
        int result = this.userInfoMapper.updateUserToken(id, token);
        if(result<=0){
            return false;
        }
        return true;
    }
}
