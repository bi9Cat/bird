package com.example.bird.service;

import com.example.bird.model.DepartmentInfo;

import java.util.List;

public interface DepartmentInfoService {
    /**
     * 根据主管ID查询部门信息
     *
     * @param supervisorId 主管ID
     * @return 部门列表
     */
    List<DepartmentInfo> queryBySupervisorId(long supervisorId);

    /**
     * 根据ID批量查询
     *
     * @param ids 部门id
     * @return 部门列表
     */
    List<DepartmentInfo> queryByIds(List<Long> ids);
}
