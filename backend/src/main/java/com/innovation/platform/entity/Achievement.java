package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成果实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("achievement")
public class Achievement extends BaseEntity {
    private Long projectId;
    private Integer achievementType;
    private String achievementName;
    private Integer achievementLevel;
    private String authorNames;
    private LocalDate publishTime;
    private String publishOrg;
    private String description;
    private BigDecimal credit;
    private Integer status;
    private Long auditorId;
    private LocalDateTime auditTime;
    private String auditOpinion;
}
