package selab.desktop.resource_management.itemManagement.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import selab.desktop.resource_management.itemManagement.domain.Item;
import selab.desktop.resource_management.itemManagement.service.ItemService;
import selab.desktop.resource_management.itemManagement.utils.ItemPage;
import selab.desktop.resource_management.itemManagement.utils.JsonResult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //分页查询所有的物品
   @GetMapping()
    public JsonResult<ItemPage> all(@RequestParam(defaultValue = "1")int page,
                                    @RequestParam(defaultValue = "5")int size,
                                    @RequestParam(defaultValue = "") String search) throws UnsupportedEncodingException {
       search = new String(search.getBytes("ISO-8859-1"), "UTF-8");
       Page<Item> itemPage = itemService.selectAllItem(page, size,search);
       long total = itemPage.getTotal();
       long pages = itemPage.getPages();
       List<Item> records = itemPage.getRecords();
       ItemPage itemPage1 = new ItemPage(total,pages,records);
       return new JsonResult<>(200,"success",itemPage1);
   }
   //添加物品
   @PostMapping()
   public JsonResult<?> add(@RequestBody Item item){
       return itemService.addItem(item);

   }
   //修改物品
   @PutMapping()
    public JsonResult<?> update(Item item){
       itemService.updateItem(item);
       return new JsonResult<>(200,"success",null);
   }
   //通过id查询物品
   @GetMapping("/{id}")
    public JsonResult<Item> select(@PathVariable Long id){
       Item item = itemService.getItemById(id);
       return new JsonResult<>(200,null,item);
   }
   //通过id删除物品
   @DeleteMapping("/{id}")
    public JsonResult<?> delete(@PathVariable Long id){
     itemService.deleteItemById(id);
     return new JsonResult<>(200,"success",null);
   }

    @Value("${file.upload-path}")
    private String uploadPath;

    @RequestMapping("/img-upload")
    public JsonResult uploadImg(MultipartFile file){

        try {
            //拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            //拿到图片上传到的目录的磁盘路径
            String uploadDirPath = uploadDirFile.getAbsolutePath();
            //拿到图片保存到的磁盘路径
            String fileUploadPath = uploadDirPath + "\\" + file.getOriginalFilename();
            //保存图片
            file.transferTo(new File(fileUploadPath));
            //成功响应
            return new JsonResult(200,"success",null);
        } catch (IOException e) {
            //失败响应
            return new JsonResult<>(500,"图片上传失败",null);
        }
    }
}
