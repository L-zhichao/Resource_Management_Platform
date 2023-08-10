package selab.desktop.resource_management.controller.itemManagement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.itemManagement.applynews.DTO.ResponseItemDTO;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ResponseItemVo;
import selab.desktop.resource_management.service.itemManagement.ResponseItemService;
import selab.desktop.resource_management.utils.JsonResult;


import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
@Tag(name = "物品回应Controller层")
public class ResponseItemController {
    private final ResponseItemService responseItemService;


    @Operation(summary = "物品回应上传模块")
    @PostMapping("/response")
    public JsonResult<Void> response(@RequestBody ResponseItemDTO responseItemDTO){
        responseItemService.saveResonse(responseItemDTO);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(summary = "所有物品回应信息展示模块")
    @PostMapping("/showResponse")
    public JsonResult<List<ResponseItemVo>> showResponse(){
        List<ResponseItemVo> responseItemVos = responseItemService.selectAllResponse();
        JsonResult<List<ResponseItemVo>> listJsonResult = new JsonResult<>(JsonResult.SUCCESS, null, responseItemVos);
        return listJsonResult;
    }

    @Operation(summary = "本人所有未读申请回应信息")
    @PostMapping("/readResponse")
    public JsonResult<List<ResponseItemVo>> responseRemind(HttpServletRequest request){
        String name = (String)request.getSession().getAttribute("name");
        List<ResponseItemVo> responseItemVos = responseItemService.selectAllUnreadResonse(name);
        JsonResult<List<ResponseItemVo>> listJsonResult = new JsonResult<>(JsonResult.SUCCESS,null,responseItemVos);
        return listJsonResult;
    }

    @Operation(summary = "读取响应")
    @GetMapping("/readed")
    public JsonResult<Void> readResponse(@RequestParam Long applyId){
      responseItemService.updateResponseStatus(applyId);
      return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }
}
