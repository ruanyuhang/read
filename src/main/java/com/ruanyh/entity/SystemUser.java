package com.ruanyh.entity;


public class SystemUser {
    private Integer id;         // 主键ID(必备)

    private Long modifiedTime;  // 修改时间(必备)
    private Long createTime;    // 创建时间(必备)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
