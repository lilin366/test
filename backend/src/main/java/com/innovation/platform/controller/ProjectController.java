package com.innovation.platform.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.innovation.platform.common.PageResult;
import com.innovation.platform.common.Result;
import com.innovation.platform.dto.ProjectQueryRequest;
import com.innovation.platform.dto.ProjectRequest;
import com.innovation.platform.dto.ProjectResponse;
import com.innovation.platform.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 项目管理控制器
 */
@Tag(name = "项目管理", description = "项目的增删改查接口")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@SaCheckLogin
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "分页查询项目列表")
    @GetMapping
    public Result<PageResult<ProjectResponse>> page(ProjectQueryRequest request) {
        IPage<ProjectResponse> page = projectService.page(request);
        return Result.success(PageResult.of(page));
    }

    @Operation(summary = "根据ID查询项目详情")
    @GetMapping("/{id}")
    public Result<ProjectResponse> getById(
            @Parameter(description = "项目ID") @PathVariable Long id) {
        ProjectResponse project = projectService.getById(id);
        return Result.success(project);
    }

    @Operation(summary = "创建项目")
    @PostMapping
    public Result<Long> create(@Valid @RequestBody ProjectRequest request) {
        Long id = projectService.create(request);
        return Result.success("创建成功", id);
    }

    @Operation(summary = "更新项目")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "项目ID") @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request) {
        projectService.update(id, request);
        return Result.success("更新成功", null);
    }

    @Operation(summary = "删除项目")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "项目ID") @PathVariable Long id) {
        projectService.delete(id);
        return Result.success("删除成功", null);
    }
}
