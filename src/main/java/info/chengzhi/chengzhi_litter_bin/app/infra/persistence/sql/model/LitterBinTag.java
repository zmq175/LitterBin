package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model;

import java.util.Date;

public class LitterBinTag {
    private Long tagId;

    private String tagName;

    private String creator;

    private Date createTime;

    private Date updateTime;

    private Short opStatus;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Short opStatus) {
        this.opStatus = opStatus;
    }
}