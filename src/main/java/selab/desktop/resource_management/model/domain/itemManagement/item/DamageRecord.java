package selab.desktop.resource_management.model.domain.itemManagement.item;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@TableName("damage_record")
@AllArgsConstructor
@NoArgsConstructor
public class DamageRecord {
    @TableId
    private Long damageRecordId;
    private String damageRecordDesc;

    private Date damageRecordTime;
    private String itemName;
    private String  userName;
    private Boolean damageRecordIsread;//管理员是否已读
    private String damageRecordImg;
    private Boolean damageRecordIshandle;//管理员是否已处理


}