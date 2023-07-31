package selab.desktop.resource_management.model.domain.itemManagement.item;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Schema(description = "主键物品id")
    @TableId
    private Long itemId;
    @Schema(description = "物品名")
    private String itemname;
    @Schema(description = "数量")
    private Integer number;
    @Schema(description = "价格")
    private Integer price;
    @Schema(description = "照片")
    private String imgs;
}
