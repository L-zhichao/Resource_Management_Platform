package selab.desktop.resource_management.userManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import selab.desktop.resource_management.userManagement.domain.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {



}
