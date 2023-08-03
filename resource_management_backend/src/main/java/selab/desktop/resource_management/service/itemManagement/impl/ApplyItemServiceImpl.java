package selab.desktop.resource_management.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.itemManagement.applynews.ApplyItem;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ApplyItemVo;
import selab.desktop.resource_management.exception.itemManagement.ApplyUpdateException;
import selab.desktop.resource_management.exception.userManagment.UserInsertException;
import selab.desktop.resource_management.mapper.itemManagement.ApplyItemMapper;
import selab.desktop.resource_management.service.itemManagement.ApplyItemService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplyItemServiceImpl extends ServiceImpl<ApplyItemMapper, ApplyItem> implements ApplyItemService {

    private final ApplyItemMapper applyItemMapper;

    private ApplyItem applyIyemUploadToApplyItem(ApplyItemUpload applyItemUpload){
        ApplyItem applyItem = new ApplyItem();
        applyItem.setApplyUser(applyItemUpload.getName());
        applyItem.setContent(applyItemUpload.getContent());
        applyItem.setMoney(applyItemUpload.getMoney());
        applyItem.setApplyTime(new Date());
        return applyItem;
    }
    @Override
    public void saveApply(ApplyItemUpload applyItemUpload) {
        ApplyItem applyItem = applyIyemUploadToApplyItem(applyItemUpload);
        int rows = applyItemMapper.insert(applyItem);
        if(rows != 1){
            throw new UserInsertException("申请上传未知异常");
        }
    }

    @Override
    public List<ApplyItemVo> selectAllApply() {
        QueryWrapper<ApplyItem> applyItemQueryWrapper = new QueryWrapper<>();
        List<ApplyItem> applyItems = applyItemMapper.selectList(applyItemQueryWrapper);
        List<ApplyItemVo> applyItemVos = new ArrayList<>();
        applyItems.forEach(applyItem -> {
            ApplyItemVo applyItemVo = applyItemToApplyItemVo(applyItem);
            applyItemVos.add(applyItemVo);
        });
        return applyItemVos;
    }

    private ApplyItemVo applyItemToApplyItemVo(ApplyItem applyItem) {
        ApplyItemVo applyItemVo = new ApplyItemVo();
        applyItemVo.setApplyId(applyItem.getApplyId().toString());
        applyItemVo.setApplyUser(applyItem.getApplyUser());
        applyItemVo.setApplyTime(applyItem.getApplyTime());
        applyItemVo.setMoney(applyItem.getMoney());
        applyItemVo.setContent(applyItem.getContent());
        applyItemVo.setStatus(applyItem.getStatus());
        return applyItemVo;
    }

    @Override
    public List<ApplyItemVo> selectAllUnreadApply() {
        LambdaQueryWrapper<ApplyItem> applyItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        applyItemLambdaQueryWrapper.eq(ApplyItem::getStatus,ApplyItem.Status_UNREAD);
        List<ApplyItem> applyItems = applyItemMapper.selectList(applyItemLambdaQueryWrapper);
        List<ApplyItemVo> applyItemVos = new ArrayList<>();
        applyItems.forEach(applyItem -> {
            ApplyItemVo applyItemVo = applyItemToApplyItemVo(applyItem);
            applyItemVos.add(applyItemVo);
        });
        return applyItemVos;
    }

    @Override
    public void updateApplyStatus(Long applyId) {
        LambdaQueryWrapper<ApplyItem> applyItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        applyItemLambdaQueryWrapper.eq(ApplyItem::getApplyId,applyId);
        ApplyItem applyItem = applyItemMapper.selectOne(applyItemLambdaQueryWrapper);
        applyItem.setStatus(ApplyItem.Status_READED);
        int rows = applyItemMapper.updateById(applyItem);
        if(rows != 1){
            throw new ApplyUpdateException("申请状态更改未知异常");
        }
    }
}
