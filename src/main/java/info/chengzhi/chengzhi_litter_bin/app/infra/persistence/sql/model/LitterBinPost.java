package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model;

import java.util.Date;

public class LitterBinPost {
  private Long postId;

  private String creator;

  private Date createTime;

  private Date updateTime;

  private Short opStatus;

  private String content;

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content == null ? null : content.trim();
  }
}