package selab.desktop.resource_management.itemManagement.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Item {
    @TableId
    private Long itemId;

    private String itemname;
    private Integer number;
    private Integer status;
    private String imgs;
}
