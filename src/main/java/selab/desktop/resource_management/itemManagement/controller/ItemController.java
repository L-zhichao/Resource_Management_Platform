package selab.desktop.resource_management.itemManagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.service.ItemService;


@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //查询所有
    @RequestMapping("/all")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<Item> itemPage = itemService.findPage(page, size);
        modelAndView.addObject("itemPage",itemPage);
        modelAndView.setViewName("/item_all");
        return modelAndView;
    }

    //新增物品
    @RequestMapping("/add")
    public String add(Item item){
        itemService.add(item);
        return "redirect:/item/all";
    }

    //查询物品，跳转到修改页面
    @RequestMapping("/edit")
    public ModelAndView edit(Long id){
        ModelAndView modelAndView = new ModelAndView();
        Item item = itemService.findById(id);
        modelAndView.addObject("item",item);
        modelAndView.setViewName("item_update");
        return modelAndView;
    }

    //修改物品
    @RequestMapping("/update")
    public String update(Item item){
        itemService.update(item);
        return "redirect:item/all";
    }


    //删除物品
    @RequestMapping("/delete")
    public String delete(Long id){
        itemService.delete(id);
        return "redirect:item/all";
    }



}
