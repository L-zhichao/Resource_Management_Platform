package selab.desktop.resource_management.itemManagement.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
@TableName("item_resonse")
public class ResponseItem {
    public static final Integer Response_READED = 1;
    public static final Integer Response_UNREAD = -1;
    public static final Integer Response_AGREE = 2;
    public static final Integer Response_DISAGREE = 0;
    @TableId(type = IdType.ASSIGN_ID)

    private Long resonseId;
    private Long applyId;
    private String applyUser;
    private String responseUser;
    @TableField("response_status")
    private Integer status;
    @TableField("resonse_result")
    private Integer result;
    @TableField("result_reason")
    private String reason;
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date responseTime;


}
