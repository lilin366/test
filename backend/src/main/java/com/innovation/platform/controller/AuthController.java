package com.innovation.platform.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.innovation.platform.common.Result;
import com.innovation.platform.dto.LoginRequest;
import com.innovation.platform.dto.LoginResponse;
import com.innovation.platform.dto.RegisterRequest;
import com.innovation.platform.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Tag(name = "认证管理", description = "登录、注册、登出接口")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SysUserService sysUserService;

    @SaIgnore
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = sysUserService.login(request);
        return Result.success("登录成功", response);
    }

    @SaIgnore
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        sysUserService.register(request);
        return Result.success("注册成功", null);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        sysUserService.logout();
        return Result.success("登出成功", null);
    }
}
