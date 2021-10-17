package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model;

public class LitterBinPostTagMapping {
    private Long postTagMappingId;

    private Long postId;

    private Long tagId;

    public Long getPostTagMappingId() {
        return postTagMappingId;
    }

    public void setPostTagMappingId(Long postTagMappingId) {
        this.postTagMappingId = postTagMappingId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}