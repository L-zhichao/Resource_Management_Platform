package selab.desktop.resource_management.itemManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.itemManagement.domain.ApplyItem;
import selab.desktop.resource_management.itemManagement.domain.Vo.ApplyItemUpload;
import selab.desktop.resource_management.itemManagement.domain.Vo.ApplyItemVo;
import selab.desktop.resource_management.itemManagement.domain.Vo.ResponseItemUpload;
import selab.desktop.resource_management.itemManagement.domain.Vo.ResponseItemVo;
import selab.desktop.resource_management.itemManagement.mapper.ApplyItemMapper;

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
