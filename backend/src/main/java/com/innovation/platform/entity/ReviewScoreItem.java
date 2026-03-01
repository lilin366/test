package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 评审评分项实体
 */
@Data
@TableName("review_score_item")
public class ReviewScoreItem implements Serializable {
    private Long id;
    private Long reviewId;
    private String itemName;
    private BigDecimal itemScore;
    private BigDecimal maxScore;
    private String itemComment;
    private LocalDateTime createTime;
}
