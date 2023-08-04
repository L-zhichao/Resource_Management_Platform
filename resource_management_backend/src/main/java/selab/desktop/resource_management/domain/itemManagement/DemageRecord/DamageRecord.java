package selab.desktop.resource_management.domain.itemManagement.DemageRecord;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@TableName("damage_record")
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "物品损毁模型")
public class DamageRecord {
    @Schema(description = "主键id")
    @TableId
    private Long damageRecordId;
    @Schema(description = "损坏物品描述")
    private String damageRecordDesc;
    @Schema(description = "记录时间")
    private Date damageRecordTime;
    @Schema(description = "物品名称")
    @TableField("itemname")
    private String itemName;
    @Schema(description = "上传者")
    @TableField("username")
    private String userName;
    @Schema(description = "管理员是否已读")
    private Boolean damageRecordIsread;
    @Schema(description = "上传图片")
    private String damageRecordImg;
    @Schema(description = "管理员是否已处理")
    private Boolean damageRecordIshandle;


}