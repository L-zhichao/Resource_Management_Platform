package selab.desktop.resource_management.model.service.itemManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.model.domain.itemManagement.ApplyItem;
import selab.desktop.resource_management.model.domain.itemManagement.Vo.ApplyItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.Vo.ApplyItemVo;

import java.util.List;

@Service
public interface ApplyItemService extends IService<ApplyItem> {

     /**
      *  申请上传
      * @param applyItemUpload 申请上传实体类
      * @param username   申请人
      */
     void saveApply(ApplyItemUpload applyItemUpload,String username);



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


}
