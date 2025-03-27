package com.example.bird.model;

import com.example.bird.model.enums.LoginMethod;
import com.example.bird.model.enums.UserStatus;
import com.example.bird.model.enums.UserType;
import lombok.Data;

@Data
public class UserInfo {
    /**
     * 用户id
     */
    private long id;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属部门id
     */
    private long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 主管id
     */
    private long directSupervisorId;

    /**
     * 主管名称
     */
    private String directSupervisorName;

    /**
     * 状态
     */
    private UserStatus status;

    /**
     * 用户类型
     */
    private UserType userType;

    /**
     * 登录方式
     */
    private LoginMethod loginMethod;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 更新时间
     */
    private long updateTime;
}
