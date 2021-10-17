package info.chengzhi.chengzhi_litter_bin.app.domain.services.core;

import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.mapper.LitterBinPostTagMappingMapper;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.mapper.LitterBinTagMapper;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinPostTagMapping;
import info.chengzhi.chengzhi_litter_bin.app.infra.persistence.sql.model.LitterBinTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LitterBinTagService {
  @Autowired
  private LitterBinTagMapper litterBinTagMapper;
  @Autowired
  private LitterBinPostTagMappingMapper litterBinPostTagMappingMapper;

  public List<LitterBinTag> getTagsByPostId(Long postId) {
    return litterBinTagMapper.getTagsByPostId(postId);
  }

  public List<LitterBinTag> getTagsByTagNames(List<String> tagNames) {
    return litterBinTagMapper.getTagsByTagNames(tagNames);
  }

  public void addTags(List<LitterBinTag> tags) {
    litterBinTagMapper.insertTags(tags);
  }

  public void addMappings(List<LitterBinPostTagMapping> mappings) {
    litterBinPostTagMappingMapper.addMappings(mappings);
  }
}
