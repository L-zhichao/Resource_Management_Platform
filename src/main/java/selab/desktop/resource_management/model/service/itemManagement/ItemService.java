package selab.desktop.resource_management.model.service.itemManagement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import selab.desktop.resource_management.model.domain.itemManagement.Item;
import selab.desktop.resource_management.utils.Result;

public interface ItemService {

     Page<Item> selectAllItem(int page, int size,String search);

     Result<Long> addItem(Item item);

     Item getItemById(Long id);

     void updateItem(Item item);

     void deleteItemById(Long id);

}
