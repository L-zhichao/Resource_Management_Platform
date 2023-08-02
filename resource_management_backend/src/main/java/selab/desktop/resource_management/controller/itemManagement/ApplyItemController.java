package selab.desktop.resource_management.controller.itemManagement;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.itemManagement.applynews.ApplyItem;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.service.itemManagement.ApplyItemService;
import selab.desktop.resource_management.utils.JsonResult;
import java.util.List;

//@Tag(name="物品申请回应",description = "完成物品申请和回应业务")
@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ApplyItemController {
    private final ApplyItemService applyItemService;


    @Operation(summary = "物品申请上传模块")
    @PostMapping("/apply")
    public JsonResult<Void> apply(@RequestBody ApplyItemUpload applyItemUpload){
         applyItemService.saveApply(applyItemUpload);
         return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(summary = "所有物品申请信息展示模块")
    @PostMapping("/showApply")
    public JsonResult<List<ApplyItem>> showApply(){
        List<ApplyItem> applyItems = applyItemService.selectAllApply();
        JsonResult<List<ApplyItem>> applyItemVosJsonResult = new JsonResult<>(JsonResult.SUCCESS,null,applyItems);
        return applyItemVosJsonResult;
    }

   @Operation(summary = "所有未处理申请信息提醒")
   @PostMapping("/readApply")
    public JsonResult<List<ApplyItem>> applyRemind(HttpServletRequest request){
       List<ApplyItem> applyItems = applyItemService.selectAllUnreadApply();
       return new JsonResult<>(JsonResult.SUCCESS,null,applyItems);
   }

}
