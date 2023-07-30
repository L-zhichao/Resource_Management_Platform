package selab.desktop.resource_management.model.domain.itemManagement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("item_apply")
public class ApplyItem {
     public static  final Integer Status_UNREAD = -1;
     public static  final Integer Status_READED = 1;
     @TableId(type= IdType.ASSIGN_ID)
    private Long applyId;
    private String applyUser;

    @TableField("apply_content")
    private String Content;
    @TableField("apply_money")
    private Double money;
    @TableField("apply_status")
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;

}
