package selab.desktop.resource_management.domain.itemManagement.applynews;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("item_apply")
@Schema(description = "申请物品信息展示模型")
public class ApplyItem {
     public static  final Integer Status_UNREAD = -1;
     public static  final Integer Status_READED = 1;
     @Schema(description = "主键id")
     @TableId(type= IdType.ASSIGN_ID)
    private Long applyId;
     @Schema(description = "申请人")
    private String applyUser;
     @Schema(description = "申请内容")
    @TableField("apply_content")
    private String Content;
     @Schema(description = "申请金额")
    @TableField("apply_money")
    private Double money;
     @Schema(description = "申请状态")
    @TableField("apply_status")
    private Integer status;
     @Schema(description = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;

}
