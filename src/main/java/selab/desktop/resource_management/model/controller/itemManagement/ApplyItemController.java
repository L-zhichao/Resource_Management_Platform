package selab.desktop.resource_management.model.controller.itemManagement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.ApplyItem;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ApplyItemVo;
import selab.desktop.resource_management.model.service.itemManagement.ApplyItemService;
import selab.desktop.resource_management.model.utils.JsonResult;

import java.util.List;

//@Tag(name="物品申请回应",description = "完成物品申请和回应业务")
@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
@Tag(name = "物品申请controller层")
public class ApplyItemController {

    @Autowired
    private final ApplyItemService applyItemService;


    @Operation(summary = "物品申请上传模块")
    @PostMapping("/apply")
    public JsonResult<Void> apply(@RequestBody ApplyItemUpload applyItemUpload, HttpServletRequest request){
        String name = (String)request.getSession().getAttribute("name");
         applyItemService.saveApply(applyItemUpload,name);
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
