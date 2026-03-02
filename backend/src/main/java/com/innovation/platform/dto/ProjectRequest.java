package com.innovation.platform.dto;

import com.innovation.platform.entity.Project;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 项目请求 DTO
 */
@Data
public class ProjectRequest {
    private String projectNo;
    
    private String projectName;
    
    private Integer projectType;
    
    private Integer projectLevel;
    
    private Long leaderId;
    
    private Long advisorId;
    
    private String description;
    
    private String researchPlan;
    
    private String expectedResult;
    
    private String budget;
    
    private Integer status;
    
    private LocalDate startTime;
    
    private LocalDate endTime;
    
    private String college;

    /**
     * 转换为实体
     */
    public Project toEntity() {
        Project project = new Project();
        project.setProjectNo(this.projectNo);
        project.setProjectName(this.projectName);
        project.setProjectType(this.projectType);
        project.setProjectLevel(this.projectLevel);
        project.setLeaderId(this.leaderId);
        project.setAdvisorId(this.advisorId);
        project.setDescription(this.description);
        project.setResearchPlan(this.researchPlan);
        project.setExpectedResult(this.expectedResult);
        if (this.budget != null && !this.budget.isEmpty()) {
            project.setBudget(new BigDecimal(this.budget));
        }
        project.setStatus(this.status);
        project.setStartTime(this.startTime);
        project.setEndTime(this.endTime);
        project.setCollege(this.college);
        return project;
    }
}
