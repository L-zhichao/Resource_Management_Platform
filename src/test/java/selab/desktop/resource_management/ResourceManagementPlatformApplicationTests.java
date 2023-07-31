package selab.desktop.resource_management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import selab.desktop.resource_management.itemManagement.controller.DamageRecordController;
import selab.desktop.resource_management.itemManagement.controller.ItemController;

@SpringBootTest
class ResourceManagementPlatformApplicationTests {

    @Autowired
    private ItemController itemController;
    @Autowired
    private DamageRecordController damageRecordController;
    @Test
    void contextLoads() {
//        Result<ItemPage> pageResult = itemController.selectAllItem(1, 5, "");
//        System.out.println(pageResult);
//        itemController.addItem(new Item(null,"地板",1,12,null));

//        itemController.updateItem(new Item(3,"打印机",5,12,null));
//itemController.deleteItemById(6L);
//        Result<Item> itemById = itemController.getItemById(3L);
//        System.out.println(itemById);
    }
    @Test
    void test(){
//        damageRecordController.deleteDamageRecord(4L);
//        damageRecordController.updateDamageRecord(3L);
//        Result<List<DamageRecord>> allDamageRecord = damageRecordController.findAllDamageRecord();
//        System.out.println(allDamageRecord);
    }

}
