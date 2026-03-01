package com.innovation.platform.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.innovation.platform.dto.LoginRequest;
import com.innovation.platform.dto.LoginResponse;
import com.innovation.platform.dto.RegisterRequest;
import com.innovation.platform.entity.SysUser;
import com.innovation.platform.mapper.SysUserMapper;
import com.innovation.platform.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public LoginResponse login(LoginRequest request) {
        // 查询用户
        SysUser user = getByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 验证密码
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }
        
        // 登录
        StpUtil.login(user.getId());
        
        // 返回登录信息
        return LoginResponse.builder()
                .token(StpUtil.getTokenValue())
                .userId(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .avatar(user.getAvatar())
                .roleType(user.getRoleType())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest request) {
        // 检查用户名是否已存在
        if (getByUsername(request.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建用户
        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword()));
        user.setRealName(request.getRealName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setRoleType(request.getRoleType() != null ? request.getRoleType() : 0);
        user.setStatus(1);
        
        sysUserMapper.insert(user);
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
        );
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }
}
