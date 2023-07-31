package selab.desktop.resource_management.itemManagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.utils.JsonResult;

public interface ItemService {

     Page<Item> selectAllItem(int page, int size,String search);

     JsonResult<?> addItem(Item item);

     Item getItemById(Long id);

     void updateItem(Item item);

     void deleteItemById(Long id);

}
