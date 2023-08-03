package selab.desktop.resource_management.service.userManagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.userManagement.log.UserLog;
import selab.desktop.resource_management.mapper.userManagement.UserLogMapper;

import java.util.List;
@Service
public interface UserLogService extends IService<UserLog>{

    void insertLog(UserLog userLog);



    /**
     *   查询日志条数
     *    @return 日志条数
     */
    Long queryForCount();

    /**
     *  分页查询登录日志
     * @param current  当前页
     * @param size     每页展示数量
     * @return        此页数据
     */

    List<UserLog> queryAll(Long current,Long size);
}
