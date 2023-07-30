package selab.desktop.resource_management.itemManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.mapper.ItemMapper;
import selab.desktop.resource_management.itemManagement.service.ItemService;
import selab.desktop.resource_management.itemManagement.utils.Result;

import java.awt.print.Book;
import java.util.List;

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

    public Result<Long> addItem(Item item){
        QueryWrapper<Item> queryWrapper = new QueryWrapper();
        queryWrapper.eq("itemname", item.getItemname());
        List<Item> items = itemMapper.selectList(queryWrapper);
        if (items.size() > 0) {
            return new Result(500, "fail",null);
        }
         itemMapper.insert(item);

        return new Result<>(200,"success",item.getItemId());
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
