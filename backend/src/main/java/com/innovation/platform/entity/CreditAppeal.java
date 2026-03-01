package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学分申诉实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("credit_appeal")
public class CreditAppeal extends BaseEntity {
    private Long creditDetailId;
    private Long userId;
    private String appealReason;
    private String appealEvidence;
    private Integer status;
    private Long handlerId;
    private LocalDateTime handleTime;
    private String handleResult;
    private BigDecimal adjustedCredit;
}
