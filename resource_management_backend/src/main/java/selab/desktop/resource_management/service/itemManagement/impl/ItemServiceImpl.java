package selab.desktop.resource_management.service.itemManagement.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.itemManagement.item.Item;
import selab.desktop.resource_management.exception.itemManagement.ItemExistsException;
import selab.desktop.resource_management.mapper.itemManagement.ItemMapper;
import selab.desktop.resource_management.service.itemManagement.ItemService;


import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public Page<Item> selectAllItem(int page, int size, String search){
    LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
    if (search != null){
            wrapper.like(Item::getItemName,search);
        }
    Page<Item> selectPage=itemMapper.selectPage(new Page(page,size),wrapper);
    return selectPage;
    }

    public void addItem(Item item){
        QueryWrapper<Item> queryWrapper = new QueryWrapper();
        queryWrapper.eq("item_name", item.getItemName());
        List<Item> items = itemMapper.selectList(queryWrapper);
        if (items.size() > 0) {
            throw new ItemExistsException("物品已经存在");
        }
         itemMapper.insert(item);
    }

    public Item getItemById(Long id){
        return itemMapper.selectById(id);
    }

    public void updateItem(Item item){
        System.out.println("============="+item.getItemId());
        itemMapper.updateById(item);
    }

    public void deleteItemById(Long id){
        itemMapper.deleteById(id);
    }


}
