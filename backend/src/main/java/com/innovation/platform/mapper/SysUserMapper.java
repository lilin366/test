package com.innovation.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovation.platform.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper 接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
