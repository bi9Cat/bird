package com.example.bird.model;

import com.example.bird.model.enums.DepartmentStatus;
import lombok.Data;

@Data
public class DepartmentInfo {
    /**
     * 部门id
     */
    private long id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门主管ID
     */
    private long supervisorId;

    /**
     * 部门状态
     */
    private DepartmentStatus status;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 更新时间
     */
    private long updateTime;
}
