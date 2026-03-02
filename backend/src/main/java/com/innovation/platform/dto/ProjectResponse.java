package com.innovation.platform.dto;

import com.innovation.platform.entity.Project;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目响应 DTO
 */
@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String projectNo;
    private String projectName;
    private Integer projectType;
    private Integer projectLevel;
    private Long leaderId;
    private String leaderName;
    private Long advisorId;
    private String advisorName;
    private String description;
    private String researchPlan;
    private String expectedResult;
    private BigDecimal budget;
    private Integer status;
    private LocalDate startTime;
    private LocalDate endTime;
    private String college;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /**
     * 从实体转换
     */
    public static ProjectResponse fromEntity(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .projectNo(project.getProjectNo())
                .projectName(project.getProjectName())
                .projectType(project.getProjectType())
                .projectLevel(project.getProjectLevel())
                .leaderId(project.getLeaderId())
                .advisorId(project.getAdvisorId())
                .description(project.getDescription())
                .researchPlan(project.getResearchPlan())
                .expectedResult(project.getExpectedResult())
                .budget(project.getBudget())
                .status(project.getStatus())
                .startTime(project.getStartTime())
                .endTime(project.getEndTime())
                .college(project.getCollege())
                .createTime(project.getCreateTime())
                .updateTime(project.getUpdateTime())
                .build();
    }
}
