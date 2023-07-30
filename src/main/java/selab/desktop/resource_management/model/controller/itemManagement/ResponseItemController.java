package selab.desktop.resource_management.itemManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selab.desktop.resource_management.itemManagement.domain.Vo.ResponseItemUpload;
import selab.desktop.resource_management.itemManagement.domain.Vo.ResponseItemVo;
import selab.desktop.resource_management.itemManagement.service.ResponseItemService;
import selab.desktop.resource_management.utils.JsonResult;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ResponseItemController {
    private final ResponseItemService responseItemService;


    @Operation(summary = "物品回应上传模块")
    @PostMapping("/response")
    public JsonResult<Void> response(@RequestBody ResponseItemUpload responseItemUpload, HttpServletRequest request){
        String username = (String)request.getSession().getAttribute("username");
        responseItemService.saveResonse(responseItemUpload,username);
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
        String username = (String)request.getSession().getAttribute("username");
        List<ResponseItemVo> responseItemVos = responseItemService.selectAllUnreadResonse(username);
        JsonResult<List<ResponseItemVo>> listJsonResult = new JsonResult<>(JsonResult.SUCCESS,null,responseItemVos);
        return listJsonResult;
    }
}
