package com.example.bird.dao;

import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.LoginMethod;
import com.example.bird.model.enums.UserStatus;
import com.example.bird.model.enums.UserType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
public class UserInfoDao implements IRepository<UserInfo, Long> {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<UserInfo> userRowMapper = ((rs, rowNum) -> {
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
    });

    @Override
    public UserInfo insert(UserInfo userInfo) {
        String sql = "insert into tb_user_info(" +
                "userName," +
                "phoneNumber," +
                "email," +
                "nickName," +
                "password," +
                "directSupervisorId," +
                "departmentId," +
                "userType," +
                "status," +
                "loginMethod," +
                "createTime," +
                "updateTime) values(:userName," +
                ":phoneNumber," +
                ":email," +
                ":nickName," +
                ":password," +
                ":directSupervisorId," +
                ":departmentId," +
                ":userType," +
                ":status," +
                ":loginMethod," +
                ":createTime," +
                ":updateTime)";

        MapSqlParameterSource params = getMapSqlParameterSource(userInfo);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        userInfo.setId(keyHolder.getKey().longValue());
        return userInfo;
    }

    @Override
    public void update(UserInfo userInfo) {
        String sql = "update tb_user_info set " +
                "userName=:userName," +
                "phoneNumber=:phoneNumber," +
                "email=:email," +
                "nickName=:nickName," +
                "password=:password," +
                "directSupervisorId=:directSupervisorId," +
                "departmentId=:departmentId," +
                "userType=:userType," +
                "status=:status," +
                "loginMethod=:loginMethod," +
                "updateTime=:updateTime " +
                "where id = :id";
        MapSqlParameterSource params = getMapSqlParameterSource(userInfo);
        params.addValue("id", userInfo.getId());
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource getMapSqlParameterSource(UserInfo userInfo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userName", userInfo.getUserName());
        params.addValue("phoneNumber", userInfo.getPhoneNumber());
        params.addValue("email", userInfo.getEmail());
        params.addValue("nickName", userInfo.getNickName());
        params.addValue("password", userInfo.getPassword());
        params.addValue("directSupervisorId", userInfo.getDirectSupervisorId());
        params.addValue("departmentId", userInfo.getDepartmentId());
        params.addValue("userType", userInfo.getUserType().getIntCode());
        params.addValue("status", userInfo.getStatus().getIntCode());
        params.addValue("loginMethod", userInfo.getLoginMethod().getIntCode());
        params.addValue("createTime", userInfo.getCreateTime());
        params.addValue("updateTime", userInfo.getUpdateTime());
        return params;
    }

    @Override
    public Optional<UserInfo> findById(Long aLong) {
        try {
            String sql = "select * from tb_user_info where id = (:id) and status != 1";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", aLong);
            UserInfo result = jdbcTemplate.queryForObject(sql, params, userRowMapper);
            return Optional.ofNullable(result);
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.findById error.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<UserInfo> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UserInfo> findAllById(List<Long> ids) {
        try {
            if (CollectionUtils.isEmpty(ids)) {
                return List.of();
            }
            String sql = "select * from tb_user_info where id in (:ids)";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("ids", ids);
            return jdbcTemplate.query(sql, params, userRowMapper);
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.findAllById error.", e);
            return List.of();
        }
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int deleteById(Long id) {
        String sql = "update tb_user_info set status = 1 where id = (:id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    public int count(String userName, String phoneNumber, UserType userType) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            StringBuilder sql = new StringBuilder("select count(*) from tb_user_info where 1=1");
            if (StringUtils.isNotBlank(userName)) {
                sql.append(" and userName like :userName");
                params.addValue("userName", "%" + userName + "%");
            }
            if (StringUtils.isNotBlank(phoneNumber)) {
                sql.append(" and phoneNumber like :phoneNumber");
                params.addValue("phoneNumber", "%" + phoneNumber + "%");
            }
            if (userType != null) {
                sql.append(" and userType = :userType");
                params.addValue("userType", userType.getIntCode());
            }
            sql.append(" and status != 1");
            Integer count = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
            return count == null ? 0 : count;
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.count error.", e);
            return 0;
        }
    }

    public List<UserInfo> queryByUserNameAndPhoneNumber(String userName,
                                                        String phoneNumber,
                                                        UserType userType,
                                                        int offset,
                                                        int limit) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("offset", offset);
            params.addValue("limit", limit);
            StringBuilder sql = new StringBuilder("select * from tb_user_info where 1=1");
            if (StringUtils.isNotBlank(userName)) {
                sql.append(" and userName like :userName");
                params.addValue("userName", "%" + userName + "%");
            }
            if (StringUtils.isNotBlank(phoneNumber)) {
                sql.append(" and phoneNumber like :phoneNumber");
                params.addValue("phoneNumber", "%" + phoneNumber + "%");
            }
            if (userType != null) {
                sql.append(" and userType = :userType");
                params.addValue("userType", userType.getIntCode());
            }
            sql.append(" and status != 1 limit :offset,:limit");

            return jdbcTemplate.query(sql.toString(), params, userRowMapper);
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.queryByUserNameAndPhoneNumber error.", e);
            return new ArrayList<>();
        }
    }
}
