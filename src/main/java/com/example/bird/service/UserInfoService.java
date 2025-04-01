package com.example.bird.service;

import com.example.bird.controller.response.FilterResponse;
import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.UserType;

public interface UserInfoService {

    /**
     * 创建用户
     *
     * @param userInfo 用户信息
     * @return 用户信息
     */
    UserInfo createUser(UserInfo userInfo);

    /**
     * 根据id删除用户
     *
     * @param userId 用户id
     * @return 是否删除成功
     */
    boolean deleteUser(long userId);

    /**
     * 更新用户
     *
     * @param userInfo 用户信息
     * @return 更新后的用户信息
     */
    UserInfo updateUser(UserInfo userInfo);

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户id
     * @return 用户ID为userId的用户信息
     */
    UserInfo queryByUserId(long userId);

    /**
     * 修改密码
     *
     * @param userInfo 传入用户信息
     * @return 修改后的用户信息
     */
    UserInfo changePassword(UserInfo userInfo);

    FilterResponse filter(String userName, String phoneNumber, UserType userType, int page, int pageSize);

    int count(String userName, String phoneNumber, UserType userType);
}
