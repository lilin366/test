package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 评审实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("review")
public class Review extends BaseEntity {
    private Long projectId;
    private Long reviewerId;
    private Integer reviewType;
    private BigDecimal score;
    private String opinion;
    private Integer result;
    private Integer status;
    private LocalDateTime reviewTime;
}
