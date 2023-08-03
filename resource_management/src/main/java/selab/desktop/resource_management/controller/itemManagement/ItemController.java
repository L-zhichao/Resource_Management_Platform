package selab.desktop.resource_management.controller.itemManagement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import selab.desktop.resource_management.domain.itemManagement.item.Item;
import selab.desktop.resource_management.service.itemManagement.ItemService;
import selab.desktop.resource_management.utils.ItemPage;
import selab.desktop.resource_management.utils.JsonResult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@Tag(name = "物品管理Controller层")
@RequestMapping("/item")
public class ItemController {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    private ItemService itemService;

    @Operation(summary = "查询所有物品")
    @GetMapping("/all")
    public JsonResult<ItemPage> selectAllItem(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "5") int size,
                                              @RequestParam(defaultValue = "") String search) throws UnsupportedEncodingException {
        search = new String(search.getBytes("ISO-8859-1"), "UTF-8");
        Page<Item> itemPage = itemService.selectAllItem(page, size, search);
        long total = itemPage.getTotal();
        long pages = itemPage.getPages();
        List<Item> records = itemPage.getRecords();
        ItemPage itemPage1 = new ItemPage(total, pages, records);
        return new JsonResult<>(20000, "查询成功", itemPage1);
    }

    @Operation(summary = "增加物品")
    @PostMapping("/save")
    public JsonResult<Void> addItem(@RequestBody Item item) {
        return new JsonResult<>(JsonResult.SUCCESS, null, null);

    }

    @Operation(summary = "物品更改")
    @PutMapping("/update")
    public JsonResult<?> updateItem(Item item) {
        itemService.updateItem(item);
        return new JsonResult<>(20000, "修改成功", null);
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/select/{id}")
    public JsonResult<Item> select(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return new JsonResult<>(200, null, item);
    }

    //通过id删除物品
    @Operation(summary = "根据id删除物品")
    @DeleteMapping("/delete/{id}")
    public JsonResult<?> delete(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return new JsonResult<>(200, "success", null);
    }

    @Operation(description = "图片上传")
    @PostMapping("/img-upload")
    public JsonResult uploadImg(MultipartFile file) {

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
            return new JsonResult(200, "success", null);
        } catch (IOException e) {
            //失败响应
            return new JsonResult<>(500, "图片上传失败", null);
        }
    }
}
