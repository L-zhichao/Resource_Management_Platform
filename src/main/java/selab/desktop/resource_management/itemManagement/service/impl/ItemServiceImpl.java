package selab.desktop.resource_management.itemManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.mapper.ItemMapper;
import selab.desktop.resource_management.itemManagement.service.ItemService;

import java.awt.print.Book;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public Page<Item> selectAllItem(int page,int size,String search){
    LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
    if (StringUtils.isNotBlank(search)){
            wrapper.like(Item::getItemname,search);
        }
    Page<Item> selectPage=itemMapper.selectPage(new Page(page,size),wrapper);
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
