package selab.desktop.resource_management.model.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.model.domain.itemManagement.ApplyItem;
import selab.desktop.resource_management.model.domain.itemManagement.Vo.ApplyItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.Vo.ApplyItemVo;
import selab.desktop.resource_management.model.mapper.itemManagement.ApplyItemMapper;
import selab.desktop.resource_management.model.service.itemManagement.ApplyItemService;
import selab.desktop.resource_management.model.exception.InsertException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplyItemServiceImpl extends ServiceImpl<ApplyItemMapper, ApplyItem> implements ApplyItemService {

    private final ApplyItemMapper applyItemMapper;

    private ApplyItem applyIyemUploadToApplyItem(ApplyItemUpload applyItemUpload,String username){
        ApplyItem applyItem = new ApplyItem();
        applyItem.setApplyUser(username);
        applyItem.setContent(applyItemUpload.getContent());
        applyItem.setMoney(applyItemUpload.getMoney());
        applyItem.setApplyTime(new Date());
        return applyItem;
    }
    private ApplyItemVo applyIyemToApplyItemVo(ApplyItem applyItem){
        ApplyItemVo applyItemVo = new ApplyItemVo();
        applyItemVo.setApplyUser(applyItem.getApplyUser());
        applyItemVo.setStatus(applyItem.getStatus());
        applyItemVo.setMoney(applyItem.getMoney());
        applyItemVo.setContent(applyItem.getContent());
        applyItemVo.setApplyTime(applyItem.getApplyTime());
        return applyItemVo;
    }

    @Override
    public void saveApply(ApplyItemUpload applyItemUpload, String username) {
        ApplyItem applyItem = applyIyemUploadToApplyItem(applyItemUpload, username);
        int rows = applyItemMapper.insert(applyItem);
        if(rows != 1){
            throw new InsertException("申请上传未知异常");
        }
    }

    @Override
    public List<ApplyItemVo> selectAllApply() {
        QueryWrapper<ApplyItem> applyItemQueryWrapper = new QueryWrapper<>();
        List<ApplyItem> applyItems = applyItemMapper.selectList(applyItemQueryWrapper);
        List<ApplyItemVo> applyItemVos = new ArrayList<>();
        applyItems.forEach(applyItem -> {
            ApplyItemVo applyItemVo = applyIyemToApplyItemVo(applyItem);
            applyItemVos.add(applyItemVo);
        });
        return applyItemVos;
    }

    @Override
    public List<ApplyItemVo> selectAllUnreadApply() {
        LambdaQueryWrapper<ApplyItem> applyItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        applyItemLambdaQueryWrapper.eq(ApplyItem::getStatus,ApplyItem.Status_UNREAD);
        List<ApplyItem> applyItems = applyItemMapper.selectList(applyItemLambdaQueryWrapper);
        List<ApplyItemVo> applyItemVos = new ArrayList<>();
        applyItems.forEach(applyItem -> {
            ApplyItemVo applyItemVo = applyIyemToApplyItemVo(applyItem);
            applyItemVos.add(applyItemVo);
        });
        return applyItemVos;
    }
}
