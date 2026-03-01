package com.innovation.platform.service;

import com.innovation.platform.dto.LoginRequest;
import com.innovation.platform.dto.LoginResponse;
import com.innovation.platform.dto.RegisterRequest;
import com.innovation.platform.entity.SysUser;

/**
 * 用户服务接口
 */
public interface SysUserService {
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 用户注册
     */
    void register(RegisterRequest request);
    
    /**
     * 用户登出
     */
    void logout();
    
    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);
    
    /**
     * 根据ID查询用户
     */
    SysUser getById(Long id);
}
