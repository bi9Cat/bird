package com.example.bird.dao;

import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.UserType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Repository
public class UserInfoDao implements Repository<UserInfo, Long> {

    private static final String ID = "id";
    private static final String USER_NAME = "userName";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String USER_TYPE = "userType";
    private static final String LIKE_OPT = "%";
    private static final String AND_USER_NAME_LIKE_USER_NAME = " and userName like :userName";
    private static final String AND_PHONE_NUMBER_LIKE_PHONE_NUMBER = " and phoneNumber like :phoneNumber";
    private static final String AND_USER_TYPE_USER_TYPE = " and userType = :userType";
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UserInfo insert(UserInfo userInfo) {
        String sql = "insert into tb_user_info("
                + "userName,"
                + "phoneNumber,"
                + "email,"
                + "nickName,"
                + "password,"
                + "directSupervisorId,"
                + "departmentId,"
                + "userType,"
                + "status,"
                + "loginMethod,"
                + "createTime,"
                + "updateTime) values(:userName,"
                + ":phoneNumber,"
                + ":email,"
                + ":nickName,"
                + ":password,"
                + ":directSupervisorId,"
                + ":departmentId,"
                + ":userType,"
                + ":status,"
                + ":loginMethod,"
                + ":createTime,"
                + ":updateTime)";

        MapSqlParameterSource params = getMapSqlParameterSource(userInfo);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        userInfo.setId(keyHolder.getKey().longValue());
        return userInfo;
    }

    @Override
    public void update(UserInfo userInfo) {
        String sql = "update tb_user_info set "
                + "userName=:userName,"
                + "phoneNumber=:phoneNumber,"
                + "email=:email,"
                + "nickName=:nickName,"
                + "password=:password,"
                + "directSupervisorId=:directSupervisorId,"
                + "departmentId=:departmentId,"
                + "userType=:userType,"
                + "status=:status,"
                + "loginMethod=:loginMethod,"
                + "updateTime=:updateTime "
                + "where id = :id";
        MapSqlParameterSource params = getMapSqlParameterSource(userInfo);
        params.addValue(ID, userInfo.getId());
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource getMapSqlParameterSource(UserInfo userInfo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_NAME, userInfo.getUserName());
        params.addValue(PHONE_NUMBER, userInfo.getPhoneNumber());
        params.addValue("email", userInfo.getEmail());
        params.addValue("nickName", userInfo.getNickName());
        params.addValue("password", userInfo.getPassword());
        params.addValue("directSupervisorId", userInfo.getDirectSupervisorId());
        params.addValue("departmentId", userInfo.getDepartmentId());
        params.addValue(USER_TYPE, userInfo.getUserType().getIntCode());
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
            params.addValue(ID, aLong);
            UserInfo result = jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
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
            return jdbcTemplate.query(sql, params, new UserRowMapper());
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.findAllById error.", e);
            return List.of();
        }
    }

    @Override
    public int deleteById(Long id) {
        String deleteSql = "update tb_user_info set status = 1 where %s = (:%s)";
        String sql = String.format(deleteSql, ID, ID);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(ID, id);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    public int count(String userName, String phoneNumber, UserType userType) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            StringBuilder sql = new StringBuilder("select count(*) from tb_user_info where 1=1");
            if (StringUtils.isNotBlank(userName)) {
                sql.append(AND_USER_NAME_LIKE_USER_NAME);
                params.addValue(USER_NAME, LIKE_OPT + userName + LIKE_OPT);
            }
            if (StringUtils.isNotBlank(phoneNumber)) {
                sql.append(AND_PHONE_NUMBER_LIKE_PHONE_NUMBER);
                params.addValue(PHONE_NUMBER, LIKE_OPT + phoneNumber + LIKE_OPT);
            }
            if (userType != null) {
                sql.append(AND_USER_TYPE_USER_TYPE);
                params.addValue(USER_TYPE, userType.getIntCode());
            }
            sql.append(" and status != 1");
            Integer count = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
            return count == null ? 0 : count;
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.count error.", e);
            return 0;
        }
    }

    public List<UserInfo> queryByUserNameAndPhoneNumber(String userName, String phoneNumber, UserType userType, int offset, int limit) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("offset", offset);
            params.addValue("limit", limit);
            StringBuilder sql = new StringBuilder("select * from tb_user_info where 1=1");
            if (StringUtils.isNotBlank(userName)) {
                sql.append(AND_USER_NAME_LIKE_USER_NAME);
                params.addValue(USER_NAME, LIKE_OPT + userName + LIKE_OPT);
            }
            if (StringUtils.isNotBlank(phoneNumber)) {
                sql.append(AND_PHONE_NUMBER_LIKE_PHONE_NUMBER);
                params.addValue(PHONE_NUMBER, LIKE_OPT + phoneNumber + LIKE_OPT);
            }
            if (userType != null) {
                sql.append(AND_USER_TYPE_USER_TYPE);
                params.addValue(USER_TYPE, userType.getIntCode());
            }
            sql.append(" and status != 1 limit :offset,:limit");

            return jdbcTemplate.query(sql.toString(), params, new UserRowMapper());
        } catch (DataAccessException e) {
            LOG.error("UserInfoDao.queryByUserNameAndPhoneNumber error.", e);
            return new ArrayList<>();
        }
    }
}
