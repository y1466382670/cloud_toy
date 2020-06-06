package com.yu.dao;

import com.yu.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated Mon Jun 01 11:06:39 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated Mon Jun 01 11:06:39 CST 2020
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated Mon Jun 01 11:06:39 CST 2020
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated Mon Jun 01 11:06:39 CST 2020
     */
    List<UserInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated Mon Jun 01 11:06:39 CST 2020
     */
    int updateByPrimaryKey(UserInfo record);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    UserInfo selectByPhone(@Param("phone") String phone);

    /**
     * 修改用户token
     * @param id 主键
     * @param token
     * @return
     */
    int updateUserToken(@Param("id") int id, @Param("token") String token);
}