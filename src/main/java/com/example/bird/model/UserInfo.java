package com.example.bird.model;

import com.example.bird.model.enums.LoginMethod;
import com.example.bird.model.enums.UserStatus;
import com.example.bird.model.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户信息")
@Data
public class UserInfo {
    /**
     * 用户id
     */
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1L")
    private long id;

    /**
     * 用户姓名
     */
    @Schema(description = "用户名", example = "张三")
    private String userName;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "187****0462", pattern = "")
    private String phoneNumber;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "187****0462@163.com", pattern = "")
    private String email;

    /**
     * 用户昵称
     */
    @Schema(description = "昵称", example = "Alex")
    private String nickName;

    /**
     * 密码
     */
    @Schema(description = "密码", example = "****")
    private String password;

    /**
     * 所属部门id
     */
    @Schema(description = "所属部门ID", example = "1")
    private long departmentId;

    /**
     * 部门名称
     */
    @Schema(description = "所属部门名称", example = "技术部")
    private String departmentName;

    /**
     * 主管id
     */
    @Schema(description = "直属主管ID", example = "1")
    private long directSupervisorId;

    /**
     * 主管名称
     */
    @Schema(description = "直属主管姓名", example = "王大锤")
    private String directSupervisorName;

    /**
     * 状态
     */
    @Schema(description = "用户状态", example = "NORMAL")
    private UserStatus status;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型", example = "ORDINARY_EMPLOYEES")
    private UserType userType;

    /**
     * 登录方式
     */
    @Schema(description = "登录方式", example = "NORMAL_LOGIN")
    private LoginMethod loginMethod;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private long createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private long updateTime;
}
