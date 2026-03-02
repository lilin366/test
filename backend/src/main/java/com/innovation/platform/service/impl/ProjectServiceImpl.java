package com.innovation.platform.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.innovation.platform.dto.ProjectQueryRequest;
import com.innovation.platform.dto.ProjectRequest;
import com.innovation.platform.dto.ProjectResponse;
import com.innovation.platform.entity.Project;
import com.innovation.platform.mapper.ProjectMapper;
import com.innovation.platform.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 项目服务实现类
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    @Override
    public IPage<ProjectResponse> page(ProjectQueryRequest request) {
        Page<Project> page = new Page<>(request.getCurrent(), request.getSize());
        
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(request.getProjectName()), Project::getProjectName, request.getProjectName())
                .eq(request.getProjectType() != null, Project::getProjectType, request.getProjectType())
                .eq(request.getProjectLevel() != null, Project::getProjectLevel, request.getProjectLevel())
                .eq(request.getStatus() != null, Project::getStatus, request.getStatus())
                .eq(request.getLeaderId() != null, Project::getLeaderId, request.getLeaderId())
                .eq(request.getAdvisorId() != null, Project::getAdvisorId, request.getAdvisorId())
                .eq(StringUtils.hasText(request.getCollege()), Project::getCollege, request.getCollege())
                .orderByDesc(Project::getCreateTime);

        IPage<Project> projectPage = projectMapper.selectPage(page, wrapper);
        
        return projectPage.convert(ProjectResponse::fromEntity);
    }

    @Override
    public ProjectResponse getById(Long id) {
        Project project = projectMapper.selectById(id);
        if (project == null) {
            throw new RuntimeException("项目不存在");
        }
        return ProjectResponse.fromEntity(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProjectRequest request) {
        Project project = request.toEntity();
        project.setStatus(project.getStatus() != null ? project.getStatus() : 0);
        
        // 设置创建人
        Long userId = StpUtil.getLoginIdAsLong();
        project.setCreateBy(userId);
        project.setUpdateBy(userId);
        
        projectMapper.insert(project);
        return project.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, ProjectRequest request) {
        Project existingProject = projectMapper.selectById(id);
        if (existingProject == null) {
            throw new RuntimeException("项目不存在");
        }

        Project project = request.toEntity();
        project.setId(id);
        
        // 设置更新人
        Long userId = StpUtil.getLoginIdAsLong();
        project.setUpdateBy(userId);
        
        // 保留原有的创建信息
        project.setCreateBy(existingProject.getCreateBy());
        project.setCreateTime(existingProject.getCreateTime());
        
        projectMapper.updateById(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Project project = projectMapper.selectById(id);
        if (project == null) {
            throw new RuntimeException("项目不存在");
        }
        projectMapper.deleteById(id);
    }

    @Override
    public Project getEntityById(Long id) {
        return projectMapper.selectById(id);
    }
}
