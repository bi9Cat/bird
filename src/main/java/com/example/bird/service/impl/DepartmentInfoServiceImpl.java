package com.example.bird.service.impl;

import com.example.bird.dao.DepartmentInfoDao;
import com.example.bird.model.DepartmentInfo;
import com.example.bird.service.DepartmentInfoService;
import com.example.bird.service.exception.BusinessException;
import com.example.bird.service.exception.ErrorEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentInfoServiceImpl implements DepartmentInfoService {

    @Autowired
    private DepartmentInfoDao departmentInfoDao;

    @Override
    public List<DepartmentInfo> queryBySupervisorId(long supervisorId) {
        if (supervisorId <= 0) {
            throw new BusinessException(ErrorEnum.USER_DIRECT_SUPERVISOR_EMPTY);
        }
        return departmentInfoDao.queryBySupervisorId(supervisorId);
    }

    @Override
    public List<DepartmentInfo> queryByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return List.of();
        }
        return departmentInfoDao.findAllById(ids);
    }
}
