package selab.desktop.resource_management.userManagement.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import selab.desktop.resource_management.userManagement.domain.User;

@Mapper
public interface UserMapper{


    @Insert("""
            insert into user (username,password,salt,name,email,registration_time) VALUES (#{username},#{password},#{salt},#{name},#{email},#{registrationTime});
            """)
    Integer insertUser(User user);


    @Select("""
            SELECT user_id,username,password,salt,name,user_status,email,registration_time FROM user WHERE username = #{username};
            """)
    User selectByUsername(String username);

}
