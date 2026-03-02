package com.innovation.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.innovation.platform.dto.ProjectQueryRequest;
import com.innovation.platform.dto.ProjectRequest;
import com.innovation.platform.dto.ProjectResponse;
import com.innovation.platform.entity.Project;

/**
 * 项目服务接口
 */
public interface ProjectService {

    /**
     * 分页查询项目列表
     */
    IPage<ProjectResponse> page(ProjectQueryRequest request);

    /**
     * 根据ID查询项目详情
     */
    ProjectResponse getById(Long id);

    /**
     * 创建项目
     */
    Long create(ProjectRequest request);

    /**
     * 更新项目
     */
    void update(Long id, ProjectRequest request);

    /**
     * 删除项目
     */
    void delete(Long id);

    /**
     * 获取项目实体
     */
    Project getEntityById(Long id);
}
