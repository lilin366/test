package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 学分规则实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("credit_rule")
public class CreditRule extends BaseEntity {
    private String ruleName;
    private Integer ruleType;
    private Integer targetType;
    private Integer targetLevel;
    private BigDecimal baseCredit;
    private BigDecimal leaderCoefficient;
    private BigDecimal memberCoefficient;
    private String description;
    private Integer status;
}
