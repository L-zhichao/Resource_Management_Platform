package selab.desktop.resource_management.controller.itemManagement;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import selab.desktop.resource_management.domain.itemManagement.DemageRecord.DamageRecord;
import selab.desktop.resource_management.exception.itemManagement.FileIploadException;
import selab.desktop.resource_management.service.itemManagement.DamageRecordService;
import selab.desktop.resource_management.utils.JsonResult;

import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/damage/record")
@Tag(name = "物品损坏上报记录controller层")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    @Operation(summary = "查询所有的上报记录")
    @GetMapping("/query")
    public JsonResult<List<DamageRecord>> findAllDamageRecord(@RequestParam(defaultValue = "") String username){
        if(username.equals("")){
            List<DamageRecord> allDamageRecord = damageRecordService.findAllDamageRecord();
            List<DamageRecord> damageRecords =new ArrayList<>();
            for (DamageRecord damageRecord:allDamageRecord){
                damageRecordService.updateIsRead(damageRecord.getDamageRecordId());
                DamageRecord newDamageRecord = new DamageRecord();
                BeanUtils.copyProperties(damageRecord,newDamageRecord);
                damageRecords.add(newDamageRecord);
            }
            return new JsonResult<>(JsonResult.SUCCESS,"success",allDamageRecord);
        }else{
            List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
            return new JsonResult<>(JsonResult.SUCCESS,"success",damageRecordByUserName);
        }
    }
    @Operation(summary = "新增损坏记录")
    @PostMapping("/save")
    public JsonResult<?> addDamageRecord(@RequestBody DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(summary = "删除一项损坏记录")
    @DeleteMapping("/delete")
    public JsonResult<?> deleteDamageRecord(@RequestParam Long id){
        damageRecordService.deleteDamageRecord(id);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(summary = "更新损坏记录")
    @GetMapping("/update")
    public JsonResult<Void> updateDamageRecord(@RequestParam Long id){
        damageRecordService.updateIsHandle(id);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(description = "图片上传")
    @PostMapping("/img-upload")
    public JsonResult uploadImg(MultipartFile file) {

        try {
            if (file == null) {
                throw new FileIploadException("文件上传异常");
            }
            byte[] bytes = file.getBytes();

            // 设置文件地址
            ApplicationHome applicationHome = new ApplicationHome(this.getClass());
            String absolutePath = applicationHome.getDir().getParentFile()
                    .getParentFile().getAbsolutePath();
            String destDir = absolutePath + "\\imgs\\damage";
            String fileName = UUID.randomUUID().toString();


                // 读取文件前几个字节来判断图片格式
                byte[] b = new byte[4];
                for (int i = 0; i < b.length; i++) {
                    b[i] = bytes[i];
                }
                String type = HexUtil.encodeHexStr(b).toUpperCase();
                if (type.contains("FFD8FF")) {
                    type = "JPG";
                } else if (type.contains("89504E47")) {
                    type = "png";
                } else if (type.contains("47494638")) {
                    type = "GIF";
                } else if (type.contains("424D")) {
                    type = "BMP";
                } else {
                    type = "jpeg";
                }


            String path = StrUtil.format("{}/{}." + type, destDir, fileName);
            try {
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
                imageOutput.write(bytes, 0, bytes.length);
                imageOutput.close();
            } catch (Exception ex) {
                System.out.println("Exception: " + ex);
                ex.printStackTrace();
            }
          String  filePath ="http://localhost:9090/damage/record/img-find/"+fileName+"."+type;
//            //成功响应
            return new JsonResult<String>(JsonResult.SUCCESS, null,filePath);
        } catch (IOException e) {
            //失败响应
            return new JsonResult<>(50056, "图片上传失败", null);
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
        String path =  absolutePath.replaceAll("\\\\","/")+"/imgs/damage/"+filePath;
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