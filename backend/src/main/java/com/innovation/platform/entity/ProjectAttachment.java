package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目附件实体
 */
@Data
@TableName("project_attachment")
public class ProjectAttachment implements Serializable {
    private Long id;
    private Long projectId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String fileType;
    private Integer attachmentType;
    private LocalDateTime createTime;
    private Long createBy;
    private Integer deleted;
}
