package selab.desktop.resource_management.mapper.itemManagement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import selab.desktop.resource_management.domain.itemManagement.item.Item;
@Mapper
public interface ItemMapper extends BaseMapper<Item> {

}
