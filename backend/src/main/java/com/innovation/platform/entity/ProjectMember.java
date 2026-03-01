package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目成员实体
 */
@Data
@TableName("project_member")
public class ProjectMember implements Serializable {
    private Long id;
    private Long projectId;
    private Long userId;
    private Integer memberOrder;
    private Integer role;
    private LocalDateTime joinTime;
    private LocalDateTime createTime;
    private Integer deleted;
}
