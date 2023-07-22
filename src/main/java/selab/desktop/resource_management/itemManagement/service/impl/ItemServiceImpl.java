package selab.desktop.resource_management.itemManagement.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.mapper.ItemMapper;
import selab.desktop.resource_management.itemManagement.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public Page<Item> selectAllItem(int page,int size){
    Page<Item> selectPage=itemMapper.selectPage(new Page(page,size),null);
    return selectPage;
    }

    public void addItem(Item item){
        itemMapper.insert(item);
    }

    public Item getItemById(Long id){
        return itemMapper.selectById(id);
    }

    public void updateItem(Item item){
        itemMapper.updateById(item);
    }

    public void deleteItemById(Long id){
        itemMapper.deleteById(id);
    }


}
