package selab.desktop.resource_management.model.service.itemManagement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import selab.desktop.resource_management.model.domain.itemManagement.item.Item;

public interface ItemService extends IService<Item> {

     Page<Item> selectAllItem(int page, int size,String search);

     void addItem(Item item);

     Item getItemById(Long id);

     void updateItem(Item item);

     void deleteItemById(Long id);

}
