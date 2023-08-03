package selab.desktop.resource_management.mapper.userManagement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import selab.desktop.resource_management.domain.userManagement.log.UserLog;

@Mapper
public interface UserLogMapper extends BaseMapper<UserLog> {
}
