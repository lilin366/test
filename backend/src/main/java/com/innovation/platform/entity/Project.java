package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 项目实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project")
public class Project extends BaseEntity {
    private String projectNo;
    private String projectName;
    private Integer projectType;
    private Integer projectLevel;
    private Long leaderId;
    private Long advisorId;
    private String description;
    private String researchPlan;
    private String expectedResult;
    private BigDecimal budget;
    private Integer status;
    private LocalDate startTime;
    private LocalDate endTime;
    private String college;
}
