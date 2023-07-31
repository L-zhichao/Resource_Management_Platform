package selab.desktop.resource_management.model.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.ResponseItem;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ResponseItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ResponseItemVo;
import selab.desktop.resource_management.model.mapper.itemManagement.ApplyItemMapper;
import selab.desktop.resource_management.model.mapper.itemManagement.ResponseItemMapper;
import selab.desktop.resource_management.model.service.itemManagement.ResponseItemService;
import selab.desktop.resource_management.model.exception.userManagment.UserInsertException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseItemServiceImpl extends ServiceImpl<ResponseItemMapper, ResponseItem> implements ResponseItemService {
    private final ResponseItemMapper responseItemMapper;
    private final ApplyItemMapper applyItemMapper;
    @Override
    public List<ResponseItemVo> selectAllUnreadResonse(String name) {

        LambdaQueryWrapper<ResponseItem> responseItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        responseItemLambdaQueryWrapper.eq(ResponseItem::getStatus,ResponseItem.Response_UNREAD).eq(ResponseItem::getApplyUser,name);
        List<ResponseItem> responseItems = responseItemMapper.selectList(responseItemLambdaQueryWrapper);
        List<ResponseItemVo> responseItemVos = new ArrayList<>();
        responseItems.forEach(responseItem -> {
            ResponseItemVo responseItemVo = responseItemToresponseItemVo(responseItem);
            responseItemVos.add(responseItemVo);
        });
        return responseItemVos;
    }

    @Override
    public void saveResonse(ResponseItemUpload responseItemUpload, String name) {

        ResponseItem responseItem = responseItemUploadToResponseItem(responseItemUpload, name);
        int rows = responseItemMapper.insert(responseItem);
        if(rows != 1){
            throw new UserInsertException("回应上传未知异常");
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
        responseItem.setApplyUser(responseItemUpload.getName());
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
