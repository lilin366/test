package com.innovation.platform.dto;

import lombok.Data;

/**
 * 项目查询请求 DTO
 */
@Data
public class ProjectQueryRequest {
    /**
     * 项目名称（模糊查询）
     */
    private String projectName;

    /**
     * 项目类型
     */
    private Integer projectType;

    /**
     * 项目级别
     */
    private Integer projectLevel;

    /**
     * 项目状态
     */
    private Integer status;

    /**
     * 负责人ID
     */
    private Long leaderId;

    /**
     * 指导老师ID
     */
    private Long advisorId;

    /**
     * 学院
     */
    private String college;

    /**
     * 当前页
     */
    private Integer current = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;
}
