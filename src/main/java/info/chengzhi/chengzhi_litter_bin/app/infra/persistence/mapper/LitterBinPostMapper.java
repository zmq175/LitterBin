package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.mapper;

import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LitterBinPostMapper {
  int deleteByPrimaryKey(Long postId);

  int insert(LitterBinPost record);

  int insertSelective(LitterBinPost record);

  LitterBinPost selectByPrimaryKey(Long postId);

  int updateByPrimaryKeySelective(LitterBinPost record);

  int updateByPrimaryKeyWithBLOBs(LitterBinPost record);

  int updateByPrimaryKey(LitterBinPost record);

  LitterBinPost selectLatestPostByCreator(@Param("creator") String creator);
}