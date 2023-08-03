package selab.desktop.resource_management.domain.userManagement.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;
}
