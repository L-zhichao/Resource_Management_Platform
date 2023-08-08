package selab.desktop.resource_management.domain.itemManagement.item;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@TableName("damage_record")
@AllArgsConstructor
@NoArgsConstructor
public class DamageRecord {
    @Schema(description = "物品损坏id")
    @TableId
    private Long damageRecordId;

    @Schema(description = "物品损毁描述")
    private String damageRecordDesc;

    @Schema(description = "物品损坏时间")
    private Date damageRecordTime;

    @Schema(description = "物品名字")
    private String itemName;

    @Schema(description = "用户名字")
    private String  userName;

    @Schema(description = "管理员是否已读")
    private Boolean damageRecordIsread;

    @Schema(description = "损坏物品照片")
    private String damageRecordImg;

    @Schema(description = "管理员是否已处理")
    private Boolean damageRecordIshandle;

}