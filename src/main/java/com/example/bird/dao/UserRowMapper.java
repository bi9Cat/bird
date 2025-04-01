package com.example.bird.dao;

import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.LoginMethod;
import com.example.bird.model.enums.UserStatus;
import com.example.bird.model.enums.UserType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserInfo> {
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(rs.getLong("id"));
        userInfo.setUserName(rs.getString("userName"));
        userInfo.setPhoneNumber(rs.getString("phoneNumber"));
        userInfo.setEmail(rs.getString("email"));
        userInfo.setNickName(rs.getString("nickName"));
        userInfo.setPassword(rs.getString("password"));
        userInfo.setDirectSupervisorId(rs.getLong("directSupervisorId"));
        userInfo.setDepartmentId(rs.getLong("departmentId"));
        userInfo.setUserType(UserType.valueOfCode(rs.getString("userType")));
        userInfo.setStatus(UserStatus.valueOfCode(rs.getString("status")));
        userInfo.setLoginMethod(LoginMethod.valueOfCode(rs.getString("loginMethod")));
        userInfo.setCreateTime(rs.getLong("createTime"));
        userInfo.setUpdateTime(rs.getLong("updateTime"));
        return userInfo;
    }
}
