package selab.desktop.resource_management.model.service.itemManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.ApplyItem;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ApplyItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.applynews.Vo.ApplyItemVo;

import java.util.List;


public interface ApplyItemService extends IService<ApplyItem> {

     /**
      *  申请上传
      * @param applyItemUpload 申请上传实体类
      * @param name   申请人名字
      */
     void saveApply(ApplyItemUpload applyItemUpload,String name);



     /**
      *  查询所有申请信息
      *  @return 所有申请集合
      */
     List<ApplyItem> selectAllApply();

     /**
      *  查询所有未读申请
      * @return
      */
     List<ApplyItem> selectAllUnreadApply();


}
