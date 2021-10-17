package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.mapper;

import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LitterBinTagMapper {
    int deleteByPrimaryKey(Long tagId);

    int insert(LitterBinTag record);

    int insertSelective(LitterBinTag record);

    LitterBinTag selectByPrimaryKey(Long tagId);

    int updateByPrimaryKeySelective(LitterBinTag record);

    int updateByPrimaryKey(LitterBinTag record);

    List<LitterBinTag> getTagsByPostId(@Param("postId") Long postId);
}