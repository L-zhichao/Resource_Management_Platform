package selab.desktop.resource_management.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.itemManagement.applynews.ApplyItem;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.exception.userManagment.UserInsertException;
import selab.desktop.resource_management.mapper.itemManagement.ApplyItemMapper;
import selab.desktop.resource_management.service.itemManagement.ApplyItemService;

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
    public List<ApplyItem> selectAllApply() {
        QueryWrapper<ApplyItem> applyItemQueryWrapper = new QueryWrapper<>();
        List<ApplyItem> applyItems = applyItemMapper.selectList(applyItemQueryWrapper);
        return applyItems;
    }

    @Override
    public List<ApplyItem> selectAllUnreadApply() {
        LambdaQueryWrapper<ApplyItem> applyItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        applyItemLambdaQueryWrapper.eq(ApplyItem::getStatus,ApplyItem.Status_UNREAD);
        List<ApplyItem> applyItems = applyItemMapper.selectList(applyItemLambdaQueryWrapper);
        return applyItems;
    }
}
