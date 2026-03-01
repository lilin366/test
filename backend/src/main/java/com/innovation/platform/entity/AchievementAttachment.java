package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 成果附件实体
 */
@Data
@TableName("achievement_attachment")
public class AchievementAttachment implements Serializable {
    private Long id;
    private Long achievementId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private LocalDateTime createTime;
    private Long createBy;
    private Integer deleted;
}
