package selab.desktop.resource_management.controller.itemManagement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.itemManagement.item.DamageRecord;
import selab.desktop.resource_management.service.itemManagement.DamageRecordService;
import selab.desktop.resource_management.utils.JsonResult;
;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "物品损坏Controller层")
@RequestMapping("/damage/record")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    @Operation(summary = "查询所有损毁记录")
    @GetMapping("/findAll")
    public JsonResult<List<DamageRecord>> findAllDamageRecord() {
        List<DamageRecord> allDamageRecord = damageRecordService.findAllDamageRecord();
        List<DamageRecord> damageRecords = new ArrayList<>();
        for (DamageRecord damageRecord : allDamageRecord) {
            damageRecordService.updateIsRead(damageRecord.getDamageRecordId());
            DamageRecord newDamageRecord = new DamageRecord();
            BeanUtils.copyProperties(damageRecord, newDamageRecord);
            damageRecords.add(newDamageRecord);
        }
        return new JsonResult<>(JsonResult.SUCCESS, "success", damageRecords);
    }

    @Operation(summary = "新增物品损坏记录")
    @PostMapping("/add")
    public JsonResult<?> addDamageRecord(DamageRecord damageRecord) {
        damageRecordService.addDamageRecord(damageRecord);
        return new JsonResult<>(JsonResult.SUCCESS, "success", null);
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping("/{id}")
    public JsonResult<?> deleteDamageRecord(@PathVariable Long id) {
        damageRecordService.deleteDamageRecord(id);
        return new JsonResult<>(JsonResult.SUCCESS, "success", null);
    }

    @Operation(summary = "更新损坏记录")
    @PutMapping("/update")
    public JsonResult<?> updateDamageRecord(Long damageRecordId) {
        damageRecordService.updateIsHandle(damageRecordId);
        return new JsonResult<>(JsonResult.SUCCESS, "success", null);
    }

    @Operation(summary = "通过提交者查询物品损毁记录")
    @GetMapping("/get")
    public JsonResult<List<DamageRecord>> getDamageRecordByUsername(String username) {
        List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
        return new JsonResult<>(JsonResult.SUCCESS, "success", damageRecordByUserName);
    }
}
