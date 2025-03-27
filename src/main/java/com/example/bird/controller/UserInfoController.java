package com.example.bird.controller;

import com.example.bird.controller.response.FilterResponse;
import com.example.bird.controller.response.Result;
import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.UserType;
import com.example.bird.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/create")
    public Result<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        UserInfo result = userInfoService.createUser(userInfo);
        return Result.success(result);
    }

    @PostMapping("/deleteById")
    public Result deleteUser(@RequestBody UserInfo userInfo) {
        boolean deleteSuccess = userInfoService.deleteUser(userInfo == null ? 0 : userInfo.getId());
        return deleteSuccess ? Result.success() : Result.error();
    }

    @PostMapping("/update")
    public Result<UserInfo> updateUser(@RequestBody UserInfo userInfo) {
        UserInfo result = userInfoService.updateUser(userInfo);
        return Result.success(result);
    }

    @GetMapping("/queryById")
    public Result<UserInfo> queryById(@RequestParam long userId) {
        UserInfo result = userInfoService.queryByUserId(userId);
        return Result.success(result);
    }

    @PostMapping("/changePassword")
    public Result<UserInfo> changePassword(@RequestBody UserInfo userInfo) {
        UserInfo result = userInfoService.changePassword(userInfo);
        return Result.success(result);
    }

    @GetMapping("/filter")
    public Result<FilterResponse> filter(@RequestParam @Nullable String userName,
                                         @RequestParam @Nullable String phoneNumber,
                                         @RequestParam @Nullable UserType userType,
                                         @RequestParam int page,
                                         @RequestParam int pageSize) {
        FilterResponse response = userInfoService.filter(userName, phoneNumber, userType, page, pageSize);
        return Result.success(response);
    }
}
