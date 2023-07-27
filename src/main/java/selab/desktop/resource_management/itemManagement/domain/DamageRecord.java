package selab.desktop.resource_management.itemManagement.domain;

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
    private Boolean damageRecordIsread;
    private String damageRecordImg;
    private Boolean damageRecordIshandle;


}
