package selab.desktop.resource_management.itemManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import selab.desktop.resource_management.itemManagement.domain.ApplyItem;
import selab.desktop.resource_management.itemManagement.domain.ResponseItem;
import selab.desktop.resource_management.itemManagement.domain.Vo.ResponseItemUpload;
import selab.desktop.resource_management.itemManagement.domain.Vo.ResponseItemVo;
import selab.desktop.resource_management.itemManagement.mapper.ApplyItemMapper;
import selab.desktop.resource_management.itemManagement.mapper.ResponseItemMapper;
import selab.desktop.resource_management.itemManagement.service.ResponseItemService;
import selab.desktop.resource_management.userManagement.exception.InsertException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseItemServiceImpl extends ServiceImpl<ResponseItemMapper, ResponseItem> implements ResponseItemService {
    private final ResponseItemMapper responseItemMapper;
    private final ApplyItemMapper applyItemMapper;
    @Override
    public List<ResponseItemVo> selectAllUnreadResonse(String username) {

        LambdaQueryWrapper<ResponseItem> responseItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        responseItemLambdaQueryWrapper.eq(ResponseItem::getStatus,ResponseItem.Response_UNREAD).eq(ResponseItem::getApplyUser,username);
        List<ResponseItem> responseItems = responseItemMapper.selectList(responseItemLambdaQueryWrapper);
        List<ResponseItemVo> responseItemVos = new ArrayList<>();
        responseItems.forEach(responseItem -> {
            ResponseItemVo responseItemVo = responseItemToresponseItemVo(responseItem);
            responseItemVos.add(responseItemVo);
        });
        return responseItemVos;
    }

    @Override
    public void saveResonse(ResponseItemUpload responseItemUpload, String username) {

        ResponseItem responseItem = responseItemUploadToResponseItem(responseItemUpload, username);
        int rows = responseItemMapper.insert(responseItem);
        if(rows != 1){
            throw new InsertException("回应上传未知异常");
        }
    }

    @Override
    public List<ResponseItemVo> selectAllResponse() {
        QueryWrapper<ResponseItem> responseItemQueryWrapper = new QueryWrapper<>();
        List<ResponseItem> responseItems = responseItemMapper.selectList(responseItemQueryWrapper);
        List<ResponseItemVo> responseItemVos = new ArrayList<>();
        responseItems.forEach(responseItem -> {
            ResponseItemVo responseItemVo = responseItemToresponseItemVo(responseItem);
            responseItemVos.add(responseItemVo);
        });
        return responseItemVos;
    }
    private ResponseItem responseItemUploadToResponseItem(ResponseItemUpload responseItemUpload,String username){
        ResponseItem responseItem = new ResponseItem();
        responseItem.setApplyId(responseItemUpload.getApplyId());
        responseItem.setResult(responseItemUpload.getResult());
        responseItem.setResponseUser(username);
        responseItem.setReason(responseItem.getReason());
        responseItem.setResponseTime(responseItemUpload.getResponseTime());
        responseItem.setApplyUser(responseItemUpload.getUsername());
        return responseItem;
    }
    private ResponseItemVo responseItemToresponseItemVo(ResponseItem responseItem){
        ResponseItemVo responseItemVo = new ResponseItemVo();
        responseItemVo.setApplyId(responseItem.getApplyId());
        responseItemVo.setStatus(responseItem.getStatus());
        responseItemVo.setResponseTime(responseItem.getResponseTime());
        responseItemVo.setResult(responseItem.getResult());
        responseItemVo.setResonseUser(responseItem.getResponseUser());
        responseItemVo.setReason(responseItem.getReason());
        return responseItemVo;
    }
}
