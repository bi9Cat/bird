package com.example.bird.dao;

import com.example.bird.model.DepartmentInfo;
import com.example.bird.model.enums.DepartmentStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Repository
public class DepartmentInfoDao implements Repository<DepartmentInfo, Long> {

    private static final String FIELD_SUPERVISOR_ID = "supervisorId";
    private static final RowMapper<DepartmentInfo> DEPARTMENT_INFO_ROW_MAPPER = (rs, rowNum) -> {
        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setId(rs.getLong("id"));
        departmentInfo.setDepartmentName(rs.getString("departmentName"));
        departmentInfo.setStatus(DepartmentStatus.valueOfCode(rs.getString("status")));
        departmentInfo.setSupervisorId(rs.getLong(FIELD_SUPERVISOR_ID));
        departmentInfo.setCreateTime(rs.getLong("createTime"));
        departmentInfo.setUpdateTime(rs.getLong("updateTime"));
        return departmentInfo;
    };

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public DepartmentInfo insert(DepartmentInfo entity) {
        return null;
    }

    @Override
    public void update(DepartmentInfo entity) {

    }

    @Override
    public Optional<DepartmentInfo> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<DepartmentInfo> findAll() {
        return null;
    }

    @Override
    public List<DepartmentInfo> findAllById(List<Long> ids) {
        try {
            if (CollectionUtils.isEmpty(ids)) {
                return List.of();
            }
            String sql = "select * from tb_department_info where id in (:ids)";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("ids", ids);
            return jdbcTemplate.query(sql, params, DEPARTMENT_INFO_ROW_MAPPER);
        } catch (DataAccessException e) {
            LOG.error("DepartmentInfoDao.findAllById error.", e);
            return List.of();
        }
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public int deleteById(Long aLong) {
        return 0;
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    public List<DepartmentInfo> queryBySupervisorId(long supervisorId) {
        try {
            String sql = "select * from tb_department_info where supervisorId = :supervisorId";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(FIELD_SUPERVISOR_ID, supervisorId);
            return jdbcTemplate.query(sql, params, DEPARTMENT_INFO_ROW_MAPPER);
        } catch (DataAccessException e) {
            LOG.error("DepartmentInfoDao.queryBySupervisorId error.", e);
            return List.of();
        }
    }
}
