package selab.desktop.resource_management.model.domain.userManagement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    public final static int ADMAIN_USER = 0;
    public final static int ORDINARY_USER = 1;
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    private String  username;
    private String password;
    private String salt;//盐值

    private String name;
    private Integer userStatus;
    private String email;
    private Date registrationTime;

}
