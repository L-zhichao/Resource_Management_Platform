package selab.desktop.resource_management.cloudresourceManagement.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String  username;
    private String password;
    private String name;
    private Integer userStatus;
    private String email;
    private Date registrationTime;

}
