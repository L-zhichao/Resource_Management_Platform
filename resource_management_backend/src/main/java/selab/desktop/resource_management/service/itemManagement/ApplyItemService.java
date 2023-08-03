package selab.desktop.resource_management.service.itemManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.itemManagement.applynews.ApplyItem;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ApplyItemVo;

import java.util.List;

@Service
public interface ApplyItemService extends IService<ApplyItem> {

     /**
      *  申请上传
      * @param applyItemUpload 申请上传实体类
      */
     void saveApply(ApplyItemUpload applyItemUpload);



     /**
      *  查询所有申请信息
      *  @return 所有申请集合
      */
     List<ApplyItemVo> selectAllApply();

     /**
      *  查询所有未读申请
      * @return
      */
     List<ApplyItemVo> selectAllUnreadApply();

     /**
      *  更改apply的状态
      * @param applyId  申请id
      */
   void updateApplyStatus(Long applyId);

}
