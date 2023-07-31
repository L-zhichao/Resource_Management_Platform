package selab.desktop.resource_management.domain.itemManagement.item;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @TableId
    private Long itemId;

    private String itemname;
    private Integer number;
    private Integer price;
    private String imgs;
}
