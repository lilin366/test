package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教师信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("teacher_info")
public class TeacherInfo extends BaseEntity {
    private Long userId;
    private String teacherNo;
    private String college;
    private String title;
    private String researchField;
}
