package selab.desktop.resource_management.itemManagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.mapper.ItemMapper;

@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public Page<Item> findPage(int page,int size){
    Page<Item> selectPage=itemMapper.selectPage(new Page(page,size),null);
    return selectPage;
    }

    public void add(Item item){
        itemMapper.insert(item);
    }

    public Item findById(Long id){
        return itemMapper.selectById(id);
    }

    public void update(Item item){
        itemMapper.updateById(item);
    }

    public void delete(Long id){
        itemMapper.deleteById(id);
    }


}
