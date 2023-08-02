package selab.desktop.resource_management.domain.userManagement.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@TableName("user_log")
@Data
public class UserLog {

    @Schema(description = "登录ID")
    @TableId
    private Long logId;

    @Schema(description = "用户DI")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户名")
    private String ip;     // 用户ip

    @Schema(description = "登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;
}
