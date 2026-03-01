package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 学分明细实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("credit_detail")
public class CreditDetail extends BaseEntity {
    private Long userId;
    private Long projectId;
    private Long achievementId;
    private Integer creditSource;
    private BigDecimal credit;
    private BigDecimal coefficient;
    private Long ruleId;
    private String remark;
    private Integer status;
}
