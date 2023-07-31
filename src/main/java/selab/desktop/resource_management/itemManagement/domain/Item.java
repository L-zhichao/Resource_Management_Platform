package selab.desktop.resource_management.itemManagement.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Schema(description = "自增主键ID")
    @TableId

    private Long itemId;


    private Long id;

    @Schema(description = "物品名称")
    @TableField("item_name")

    private String itemname;

    @Schema(description = "数量")
    private Integer number;

    private Integer price;


    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "物品图片")

    private String imgs;

    @Schema(description = "物品视频")
    private String video;
}
