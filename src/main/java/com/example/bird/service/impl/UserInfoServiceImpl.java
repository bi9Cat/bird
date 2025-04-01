package com.example.bird.service.impl;

import com.example.bird.controller.response.FilterResponse;
import com.example.bird.dao.UserInfoDao;
import com.example.bird.model.DepartmentInfo;
import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.UserStatus;
import com.example.bird.model.enums.UserType;
import com.example.bird.service.DepartmentInfoService;
import com.example.bird.service.UserInfoService;
import com.example.bird.service.exception.BusinessException;
import com.example.bird.service.exception.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private DepartmentInfoService departmentInfoService;

    private static final Pattern phoneNumberRegex = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");
    private static final Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(8);

    @Override
    public UserInfo createUser(UserInfo userInfo) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        checkCreateUser(userInfo);
        userInfo.setUserType(UserType.ORDINARY_EMPLOYEES);
        userInfo.setStatus(UserStatus.NORMAL);

        List<DepartmentInfo> departmentInfos = departmentInfoService.queryBySupervisorId(userInfo.getDirectSupervisorId());
        if (CollectionUtils.isEmpty(departmentInfos)) {
            throw new BusinessException(ErrorEnum.SYSTEM_ERROR);
        }
        userInfo.setDepartmentId(departmentInfos.get(0).getId());
        long nowTime = System.currentTimeMillis();
        userInfo.setCreateTime(nowTime);
        userInfo.setUpdateTime(nowTime);

        return userInfoDao.insert(userInfo);
    }

    private void checkCreateUser(UserInfo userInfo) {
        if (userInfo == null) {
            throw new BusinessException(ErrorEnum.USER_INFO_INPUT_EMPTY);
        }
        if (userInfo.getUserType() == null) {
            throw new BusinessException(ErrorEnum.USER_TYPE_IS_EMPTY);
        }
        if (userInfo.getDirectSupervisorId() <= 0) {
            throw new BusinessException(ErrorEnum.USER_DIRECT_SUPERVISOR_EMPTY);
        }
        if (!phoneNumberRegex.matcher(userInfo.getPhoneNumber()).matches()) {
            throw new BusinessException(ErrorEnum.USER_PHONE_NUMBER_ERROR);
        }
        if (!emailRegex.matcher(userInfo.getEmail()).matches()) {
            throw new BusinessException(ErrorEnum.USER_EMAIL_ERROR);
        }

    }

    @Override
    public boolean deleteUser(long userId) {
        if (userId <= 0) {
            throw new BusinessException(ErrorEnum.USER_PARAM_ERROR);
        }
        Optional<UserInfo> existUser = userInfoDao.findById(userId);
        if (existUser.isEmpty()) {
            throw new BusinessException(ErrorEnum.USER_NOT_EXIST);
        }
        int deleteResult = userInfoDao.deleteById(userId);
        return deleteResult > 0;
    }

    @Override
    public UserInfo updateUser(UserInfo userInfo) {
        checkUpdateUser(userInfo);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<UserInfo> existUser = userInfoDao.findById(userInfo.getId());
        if (existUser.isEmpty()) {
            throw new BusinessException(ErrorEnum.USER_NOT_EXIST);
        }
        Optional<DepartmentInfo> departmentInfo = departmentInfoService.queryBySupervisorId(userInfo.getDirectSupervisorId())
                .stream()
                .findFirst();

        departmentInfo.ifPresent(d -> userInfo.setDepartmentId(d.getId()));

        long nowTime = System.currentTimeMillis();
        userInfo.setUpdateTime(nowTime);
        userInfoDao.update(userInfo);
        return fillName(Arrays.asList(userInfoDao.findById(userInfo.getId()).get())).stream().findFirst().orElse(null);
    }

    private void checkUpdateUser(UserInfo userInfo) {
        if (userInfo == null) {
            throw new BusinessException(ErrorEnum.USER_INFO_INPUT_EMPTY);
        }
        if (userInfo.getId() <= 0) {
            throw new BusinessException(ErrorEnum.USER_ID_ERROR);
        }
        if (userInfo.getUserType() == null) {
            throw new BusinessException(ErrorEnum.USER_LOGIN_METHOD_IS_EMPTY);
        }
        if (userInfo.getDirectSupervisorId() <= 0) {
            throw new BusinessException(ErrorEnum.USER_DIRECT_SUPERVISOR_EMPTY);
        }
        if (!phoneNumberRegex.matcher(userInfo.getPhoneNumber()).matches()) {
            throw new BusinessException(ErrorEnum.USER_PHONE_NUMBER_ERROR);
        }
        if (!emailRegex.matcher(userInfo.getEmail()).matches()) {
            throw new BusinessException(ErrorEnum.USER_EMAIL_ERROR);
        }

    }

    @Override
    public UserInfo queryByUserId(long userId) {
        if (userId <= 0) {
            throw new BusinessException(ErrorEnum.USER_PARAM_ERROR);
        }
        Optional<UserInfo> existUser = userInfoDao.findById(userId);
        return existUser.orElse(null);
    }

    @Override
    public UserInfo changePassword(UserInfo userInfo) {
        if (userInfo == null) {
            throw new BusinessException(ErrorEnum.USER_INFO_INPUT_EMPTY);
        }
        if (userInfo.getId() <= 0) {
            throw new BusinessException(ErrorEnum.USER_ID_ERROR);
        }
        if (StringUtils.isBlank(userInfo.getPassword())) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_EMPTY);
        }

        Optional<UserInfo> existUser = userInfoDao.findById(userInfo.getId());
        if (existUser.isEmpty()) {
            throw new BusinessException(ErrorEnum.USER_NOT_EXIST);
        }

        UserInfo existUserInfo = existUser.get();
        existUserInfo.setUpdateTime(System.currentTimeMillis());
        existUserInfo.setPassword(userInfo.getPassword());
        userInfoDao.update(existUserInfo);
        return existUserInfo;
    }

    @Override
    public int count(String userName, String phoneNumber, UserType userType) {
        return userInfoDao.count(userName, phoneNumber, userType);
    }

    @Override
    public FilterResponse<List<UserInfo>> filter(String userName, String phoneNumber, UserType userType, int page, int pageSize) {
        LOG.info("UserInfoServiceImpl filter. userName:{},phoneNumber:{},userType:{},page:{},pageSize:{}",
                userName, phoneNumber, userType, page, pageSize);
        int count = userInfoDao.count(userName, phoneNumber, userType);
        if (count <= 0) {
            return new FilterResponse(page, pageSize);
        }
        pageSize = Math.min(pageSize, 100);
        int offset = (page - 1) * pageSize;
        int limit = pageSize;
        List<UserInfo> userInfos = userInfoDao.queryByUserNameAndPhoneNumber(userName, phoneNumber, userType, offset, limit);
        userInfos = fillName(userInfos);
        return new FilterResponse(count, page, pageSize, userInfos);
    }

    private List<UserInfo> fillName(List<UserInfo> users) {
        List<Long> supervisorIds = users.stream().map(UserInfo::getDirectSupervisorId).distinct().toList();
        List<Long> departmentIds = users.stream().map(UserInfo::getDepartmentId).distinct().toList();
        Future<List<UserInfo>> querySupervisorFuture = EXECUTOR_SERVICE.submit(() -> userInfoDao.findAllById(supervisorIds));
        Future<List<DepartmentInfo>> queryDepartmentFuture = EXECUTOR_SERVICE.submit(() -> departmentInfoService.queryByIds(departmentIds));

        List<UserInfo> supervisors;
        List<DepartmentInfo> departmentInfos;
        try {
            supervisors = querySupervisorFuture.get();
            departmentInfos = queryDepartmentFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

        Map<Long, UserInfo> supervisorsMap = CollectionUtils.isEmpty(supervisors)
                ? new HashMap<>()
                : supervisors.stream().collect(Collectors.toMap(UserInfo::getId, s -> s, (k1, k2) -> k1));
        Map<Long, DepartmentInfo> departmentInfoMap = CollectionUtils.isEmpty(departmentInfos)
                ? new HashMap<>()
                : departmentInfos.stream().collect(Collectors.toMap(DepartmentInfo::getId, d -> d, (k1, k2) -> k1));

        return users.stream().map(u -> {
            Optional.ofNullable(supervisorsMap.get(u.getDirectSupervisorId())).ifPresent(s -> u.setDirectSupervisorName(s.getUserName()));
            Optional.ofNullable(departmentInfoMap.get(u.getDepartmentId())).ifPresent(d -> u.setDepartmentName(d.getDepartmentName()));
            return u;
        }).toList();
    }
}
