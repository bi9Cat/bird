package com.example.bird.controller;

import com.example.bird.controller.response.FilterResponse;
import com.example.bird.controller.response.Result;
import com.example.bird.model.UserInfo;
import com.example.bird.model.enums.UserType;
import com.example.bird.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "创建用户", description = "创建用户信息")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "创建成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfo.class))})})
    @PostMapping("/create")
    public Result<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        UserInfo result = userInfoService.createUser(userInfo);
        return Result.success(result);
    }

    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    @ApiResponse(responseCode = "200",
            description = "删除成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            })
    @PostMapping("/deleteById")
    public Result deleteUser(@RequestBody @Schema(description = "删除用户只需要传入id") UserInfo userInfo) {
        boolean deleteSuccess = userInfoService.deleteUser(userInfo == null ? 0 : userInfo.getId());
        return deleteSuccess ? Result.success() : Result.error();
    }

    @Operation(summary = "更新用户", description = "更新用户")
    @ApiResponse(responseCode = "200",
            description = "更新成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            })
    @PostMapping("/update")
    public Result<UserInfo> updateUser(@RequestBody @Schema(description = "更新用户只需要传入必要的字段") UserInfo userInfo) {
        UserInfo result = userInfoService.updateUser(userInfo);
        return Result.success(result);
    }

    @Operation(summary = "查询用户", description = "根据用户ID查询用户")
    @ApiResponse(responseCode = "200",
            description = "查询成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            })
    @GetMapping("/queryById")
    public Result<UserInfo> queryById(@RequestParam long userId) {
        UserInfo result = userInfoService.queryByUserId(userId);
        return Result.success(result);
    }

    @Operation(summary = "修改密码", description = "修改用户密码")
    @ApiResponse(responseCode = "200",
            description = "修改成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            })
    @PostMapping("/changePassword")
    public Result<UserInfo> changePassword(@RequestBody @Schema(description = "修改用户密码只需要传入id和password") UserInfo userInfo) {
        UserInfo result = userInfoService.changePassword(userInfo);
        return Result.success(result);
    }

    @Operation(summary = "搜索用户", description = "根据用户名、手机号登信息分页查询用户")
    @Parameters({@Parameter(name = "userName", description = "用户名"), @Parameter(name = "phoneNumber", description = "手机号"), @Parameter(name = "userType",
            description = "用户类型", required = true), @Parameter(name = "page", description = "当前页", required = true), @Parameter(name = "pageSize",
            description = "分页大小", required = true)
    })
    @ApiResponse(responseCode = "200",
            description = "查询成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            })
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
