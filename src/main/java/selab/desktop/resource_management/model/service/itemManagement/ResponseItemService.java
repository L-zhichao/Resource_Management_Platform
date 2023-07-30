package selab.desktop.resource_management.model.service.itemManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import selab.desktop.resource_management.model.domain.itemManagement.ResponseItem;
import selab.desktop.resource_management.model.domain.itemManagement.Vo.ResponseItemUpload;
import selab.desktop.resource_management.model.domain.itemManagement.Vo.ResponseItemVo;

import java.util.List;

public interface ResponseItemService extends IService<ResponseItem> {

    /**
     * 查询所有未读回应
     * @return
     */
    List<ResponseItemVo> selectAllUnreadResonse(String username);

    /**
     *   回应上传
     * @param responseItemUpload  回应上传类
     * @param username  回应人
     */
    void saveResonse(ResponseItemUpload responseItemUpload, String username);

    /**
     *  查询所有回应
     * @return
     */
    List<ResponseItemVo> selectAllResponse();
}
