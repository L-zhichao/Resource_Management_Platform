package selab.desktop.resource_management.controller.itemManagement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.itemManagement.item.DamageRecord;
import selab.desktop.resource_management.service.itemManagement.DamageRecordService;
import selab.desktop.resource_management.utils.JsonResult;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/damage/record")
@Tag(name = "物品损坏上报记录controller层")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    @Operation(description = "查询所有的上报记录")
    @GetMapping("/all")
    public JsonResult<List<DamageRecord>> findAllDamageRecord(@RequestParam(defaultValue = "") String username){
        if(username.equals("")){
            List<DamageRecord> allDamageRecord = damageRecordService.findAllDamageRecord();
            List<DamageRecord> damageRecords =new ArrayList<>();
            for (DamageRecord damageRecord:allDamageRecord){
                damageRecordService.updateIsRead(damageRecord.getDamageRecordId());
                DamageRecord newDamageRecord = new DamageRecord();
                BeanUtils.copyProperties(damageRecord,newDamageRecord);
                damageRecords.add(newDamageRecord);
            }
            return new JsonResult<>(JsonResult.SUCCESS,"success",damageRecords);
        }else{
            List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
            return new JsonResult<>(JsonResult.SUCCESS,"success",damageRecordByUserName);
        }
    }
    @Operation(description = "新增损坏记录")
    @PostMapping("/save")
    public JsonResult<?> addDamageRecord(@RequestBody DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(description = "删除一项损坏记录")
    @DeleteMapping("/delete")
    public JsonResult<?> deleteDamageRecord(@RequestParam Long id){
        damageRecordService.deleteDamageRecord(id);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }

    @Operation(description = "更新损坏记录")
    @GetMapping("/update")
    public JsonResult<?> updateDamageRecord(@RequestParam Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new JsonResult<>(JsonResult.SUCCESS,null,null);
    }


}