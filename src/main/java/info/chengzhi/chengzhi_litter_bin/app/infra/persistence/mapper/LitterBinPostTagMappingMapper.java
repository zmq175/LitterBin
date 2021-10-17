package info.chengzhi.chengzhi_litter_bin.app.infra.persistence.mapper;

import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPostTagMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LitterBinPostTagMappingMapper {

    int insert(LitterBinPostTagMapping record);

    int insertSelective(LitterBinPostTagMapping record);

    LitterBinPostTagMapping selectByPrimaryKey(Long postTagMappingId);

    int updateByPrimaryKeySelective(LitterBinPostTagMapping record);

    int updateByPrimaryKey(LitterBinPostTagMapping record);

    int addMappings(@Param("mappings") List<LitterBinPostTagMapping> mappings);
}