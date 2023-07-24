package selab.desktop.resource_management.itemManagement.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class DamageRecord {
    @TableId
    private Long damageRecordId;
    private String damageRecordDesc;

    private Date damageRecordTime;
    private Long itemId;
    private Long UserId;
    private Boolean damageRecordIsRead;
    private String damageRecordImg;
    private Boolean damageRecordIsHandle;


}
