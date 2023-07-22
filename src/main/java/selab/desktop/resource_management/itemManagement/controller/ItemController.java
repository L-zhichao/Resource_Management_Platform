package selab.desktop.resource_management.itemManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.service.ItemService;

import java.math.BigInteger;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //查询所有
    @RequestMapping("/all")
    public ModelAndView all(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/item_all");
        return modelAndView;
    }

    //新增物品
    @RequestMapping("/add")
    public String add(Item item){


        return "redirect:/item/all";
    }

    //查询物品，跳转到修改页面
    @RequestMapping("/edit")
    public ModelAndView edit(Long id){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("item_update");
        return modelAndView;
    }

    //修改物品
    @RequestMapping("/update")
    public String update(Long id){
        return "redirect:item/all";
    }


    //删除物品
    @RequestMapping("/delete")
    public String delete(Long id){
        return "redirect:item/all";
    }




}
