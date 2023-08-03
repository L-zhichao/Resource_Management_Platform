package selab.desktop.resource_management.domain.userManagement.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@TableName("user_log")
@Data
public class UserLog {

    @TableId
    private Long logId;
    private Long userId;
    private String username;
    private String ip;     // 用户ip

    private Date loginTime;
}
