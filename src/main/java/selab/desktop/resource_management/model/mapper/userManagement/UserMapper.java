package selab.desktop.resource_management.model.mapper.userManagement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import selab.desktop.resource_management.model.domain.userManagement.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
