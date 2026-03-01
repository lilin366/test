package com.innovation.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志实体
 */
@Data
@TableName("sys_log")
public class SysLog implements Serializable {
    private Long id;
    private Long userId;
    private String username;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private Long time;
    private Integer result;
    private String errorMsg;
    private LocalDateTime createTime;
}
