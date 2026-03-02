package com.innovation.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovation.platform.entity.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目 Mapper 接口
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}
