package selab.desktop.resource_management.controller.itemManagement;


import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import selab.desktop.resource_management.domain.itemManagement.item.Item;
import selab.desktop.resource_management.exception.itemManagement.FileIploadException;
import selab.desktop.resource_management.service.itemManagement.ItemService;
import selab.desktop.resource_management.utils.ItemPage;
import selab.desktop.resource_management.utils.JsonResult;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:8080")
@RestController
@RequestMapping("/item")
@Tag(name = "物品管理controller层")
public class ItemController {

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
        return new JsonResult<>(200, null, itemPage1);
    }

    @Operation(summary = "新增物品")
    @PostMapping("/save")
    public JsonResult<Void> addItem(@RequestBody Item item) {
        itemService.addItem(item);
        return new JsonResult<>(JsonResult.SUCCESS, null, null);
    }

    @Operation(summary = "更新物品")
    @PutMapping("/updata")
    public JsonResult<?> updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
        return new JsonResult<>(JsonResult.SUCCESS, null, null);

    }

    @Operation(summary = "根据id查询物品")
    @GetMapping("/select/{id}")
    public JsonResult<Item> select(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return new JsonResult<>(JsonResult.SUCCESS, null, item);
    }

    @Operation(summary = "通过id删除物品")
    @DeleteMapping("/delete")
    public JsonResult<?> delete(@RequestParam Long id) {
        itemService.deleteItemById(id);
        return new JsonResult<>(JsonResult.SUCCESS, null, null);
    }

    @Operation(summary = "图片上传")
    @PostMapping("/img-upload")
    public JsonResult uploadImg(@RequestParam MultipartFile file) {

        try {
            if (file == null) {
                throw new FileIploadException("文件上传异常");
            }
            byte[] bytes = file.getBytes();
            // 读取文件前几个字节来判断图片格式
            byte[] b = new byte[4];
            for (int i = 0; i < b.length; i++) {
                b[i] = bytes[i];
            }
            String type = HexUtil.encodeHexStr(b).toUpperCase();
            System.out.println(type);
            if (type.contains("FFD8FF")) {
                type = "JPG";
            } else if (type.contains("89504E47")) {
                type = "png";
            } else if (type.contains("47494638")) {
                type = "GIF";
            } else if (type.contains("424D")) {
                type = "BMP";
            } else if (type.contains("0000001C")) {
                type = "mp4";
            } else {
                type = "jpeg";
            }
            // 设置文件地址
            ApplicationHome applicationHome = new ApplicationHome(this.getClass());
            String absolutePath = applicationHome.getDir().getParentFile()
                    .getParentFile().getAbsolutePath();
            String destDir = absolutePath + "\\imgs\\item";
            String fileName = UUID.randomUUID().toString();
            String path = StrUtil.format("{}/{}." + type, destDir, fileName);
            try {
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
                imageOutput.write(bytes, 0, bytes.length);
                imageOutput.close();
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
                ex.printStackTrace();
            }
            String filePath ="http://localhost:9090/item/img-find/"+fileName+"."+type;
//            //成功响应
            return new JsonResult<String>(JsonResult.SUCCESS, null,filePath);
        } catch (IOException e) {
            //失败响应
            return new JsonResult<>(50056, "图片或视频上传失败", null);
        }
    }
    @Operation(summary = "图片查询")
    @GetMapping("/img-find/{filePath}")
    public String fingImg(@PathVariable String filePath, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Access-Control-Allow-Origin","http://127.0.0.1:5500");
        response.setHeader("Access-Control-Allow-Credentials","true");
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String absolutePath = applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath();
        String path =  absolutePath.replaceAll("\\\\","/")+"/imgs/item/"+filePath;
        try {

            File file = new  File(path);

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

            String fileName=file.getName();
            int dotIndex = fileName.lastIndexOf('.');
            String extension = null;
            if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                extension = fileName.substring(dotIndex + 1).toLowerCase();
            }
            String base64Image;
            if ("png".equalsIgnoreCase(extension)) {
                base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
            } else if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
                base64Image = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(imageBytes);
            } else if ("mp4".equalsIgnoreCase(extension) || "avi".equalsIgnoreCase(extension) || "mov".equalsIgnoreCase(extension)) {
                base64Image = "data:image/video;base64," + Base64.getEncoder().encodeToString(imageBytes);
            } else {
                // 默认处理为普通文件类型
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
            }
            return base64Image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}