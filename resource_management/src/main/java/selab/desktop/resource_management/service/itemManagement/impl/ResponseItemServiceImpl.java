package selab.desktop.resource_management.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.itemManagement.applynews.ResponseItem;
import selab.desktop.resource_management.domain.itemManagement.applynews.DTO.ResponseItemDTO;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ResponseItemVo;
import selab.desktop.resource_management.exception.itemManagement.UpdateResponseStatusException;
import selab.desktop.resource_management.exception.userManagment.UserInsertException;
import selab.desktop.resource_management.mapper.itemManagement.ResponseItemMapper;
import selab.desktop.resource_management.service.itemManagement.ApplyItemService;
import selab.desktop.resource_management.service.itemManagement.ResponseItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseItemServiceImpl extends ServiceImpl<ResponseItemMapper, ResponseItem> implements ResponseItemService {
    private final ResponseItemMapper responseItemMapper;
    private final ApplyItemService applyItemService;
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
    public void saveResonse(ResponseItemDTO responseItemDTO) {
        applyItemService.updateApplyStatus(responseItemDTO.getApplyId());
        ResponseItem responseItem = responseItemUploadToResponseItem(responseItemDTO);
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

    @Override
    public void updateResponseStatus(Long applyId) {
        LambdaQueryWrapper<ResponseItem> responseItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        responseItemLambdaQueryWrapper.eq(ResponseItem::getApplyId,applyId);
        ResponseItem responseItem = responseItemMapper.selectOne(responseItemLambdaQueryWrapper);
        responseItem.setStatus(ResponseItem.Response_READED);
        int rows = responseItemMapper.updateById(responseItem);
        if(rows != 1){
            throw new UpdateResponseStatusException("更改回应状态未知异常");
        }
    }

    private ResponseItem responseItemUploadToResponseItem(ResponseItemDTO responseItemDTO){
        ResponseItem responseItem = new ResponseItem();
        responseItem.setApplyId(responseItemDTO.getApplyId());
        responseItem.setResult(responseItemDTO.getResult());
        responseItem.setResponseUser(responseItemDTO.getResponseName());
        responseItem.setReason(responseItemDTO.getReason());
        responseItem.setResponseTime(responseItemDTO.getResponseTime());
        responseItem.setApplyUser(responseItemDTO.getApplyName());
        return responseItem;
    }
    private ResponseItemVo responseItemToresponseItemVo(ResponseItem responseItem){
        ResponseItemVo responseItemVo = new ResponseItemVo();
        responseItemVo.setApplyId(responseItem.getApplyId().toString());
        responseItemVo.setStatus(responseItem.getStatus());
        responseItemVo.setResponseTime(responseItem.getResponseTime());
        responseItemVo.setResult(responseItem.getResult());
        responseItemVo.setResponseUser(responseItem.getResponseUser());
        responseItemVo.setReason(responseItem.getReason());
        return responseItemVo;
    }
}
