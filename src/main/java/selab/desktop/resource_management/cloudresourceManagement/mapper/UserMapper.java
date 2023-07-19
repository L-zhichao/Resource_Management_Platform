package selab.desktop.resource_management.cloudresourceManagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import selab.desktop.resource_management.cloudresourceManagement.domain.User;

@Mapper
public interface UserMapper{


    @Insert("""
            insert into user (username,password,name,email,registration_time) VALUES (#{username},#{password},#{name},#{email},#{registrationTime});
            """)
    Integer insertUser(User user);


    @Select("""
            SELECT id,username,password,name,user_status,email,registration_time FROM user;
            """)
    User selectByUsername(String username);

}
