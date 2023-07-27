package selab.desktop.resource_management.itemManagement.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import selab.desktop.resource_management.itemManagement.domain.Item;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPage {
    private Long total;
    private Long totalPage;
    private List<Item> items;

}
