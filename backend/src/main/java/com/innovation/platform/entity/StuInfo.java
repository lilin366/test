package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 学生信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("stu_info")
public class StuInfo extends BaseEntity {
    private Long userId;
    private String studentNo;
    private String college;
    private String major;
    private String grade;
    private String className;
    private Long advisorId;
    private BigDecimal totalCredit;
}
