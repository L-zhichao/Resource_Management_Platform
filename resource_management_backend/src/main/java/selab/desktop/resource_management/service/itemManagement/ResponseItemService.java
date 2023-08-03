package selab.desktop.resource_management.service.itemManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import selab.desktop.resource_management.domain.itemManagement.applynews.ResponseItem;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ResponseItemUpload;
import selab.desktop.resource_management.domain.itemManagement.applynews.Vo.ResponseItemVo;

import java.util.List;

public interface ResponseItemService extends IService<ResponseItem> {

    /**
     * 查询所有未读回应
     * @return
     */
    List<ResponseItemVo> selectAllUnreadResonse(String name);

    /**
     *   回应上传
     * @param responseItemUpload  回应上传类
     */
    void saveResonse(ResponseItemUpload responseItemUpload);

    /**
     *  查询所有回应
     * @return
     */
    List<ResponseItemVo> selectAllResponse();

    /**
     *     更改回应状态 为已读
     * @param applyId  申请id
     */
    void updateResponseStatus(Long applyId);
}
