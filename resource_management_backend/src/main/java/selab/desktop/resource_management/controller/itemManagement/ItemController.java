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
import java.io.*;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/item")
@Tag(name = "物品管理controller层")
public class ItemController {
    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    private ItemService itemService;

    @Operation(description = "查询所有物品")
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
        return new JsonResult<>(200, null, itemPage1);
    }

    @Operation(description = "新增物品")
    @PostMapping("/save")
    public JsonResult<Void> addItem(@RequestBody Item item) {
        itemService.addItem(item);
        return new JsonResult<>(JsonResult.SUCCESS, null, null);
    }

    @Operation(description = "更新物品")
    @PutMapping("/updata")
    public JsonResult<?> updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
        return new JsonResult<>(JsonResult.SUCCESS, null, null);

    }

    @Operation(description = "根据id查询物品")
    @GetMapping("/select/{id}")
    public JsonResult<Item> select(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return new JsonResult<>(JsonResult.SUCCESS, null, item);
    }

    @Operation(description = "通过id删除物品")
    @DeleteMapping("/delete")
    public JsonResult<?> delete(@RequestParam Long id) {
        itemService.deleteItemById(id);
        return new JsonResult<>(JsonResult.SUCCESS, null, null);
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
            String imageUrl = "http://192.168.1.3:8080/img/" + file.getOriginalFilename();
            //成功响应
            return new JsonResult(JsonResult.SUCCESS, null, null);
        } catch (IOException e) {
            //失败响应
            return new JsonResult<>(500, "图片上传失败", null);
        }
    }
    @Operation(description = "图片查询")
    @GetMapping("/img-find")
    public JsonResult<byte[]> fingImg(String url) {
        try {
            String substring = url.substring(url.indexOf('/', 8));
            substring="D://"+substring;
            File file = new  File(substring);
            System.out.println(substring);
            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = bos.toByteArray();
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            bos.close();
            fis.close();
            return new JsonResult<>(JsonResult.SUCCESS, null, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult<>(500, "图片读取失败", null);
        }
    }
}