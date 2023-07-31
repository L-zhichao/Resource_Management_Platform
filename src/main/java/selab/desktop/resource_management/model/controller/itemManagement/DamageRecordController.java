package selab.desktop.resource_management.model.controller.itemManagement;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.model.domain.itemManagement.item.DamageRecord;
import selab.desktop.resource_management.model.service.itemManagement.DamageRecordService;
import selab.desktop.resource_management.model.utils.JsonResult;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/damage/record")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    @GetMapping()
    public JsonResult<List<DamageRecord>> findAllDamageRecord(){
        List<DamageRecord> allDamageRecord = damageRecordService.findAllDamageRecord();
        List<DamageRecord> damageRecords =new ArrayList<>();
        for (DamageRecord damageRecord:allDamageRecord){
            damageRecordService.updateIsRead(damageRecord.getDamageRecordId());
            DamageRecord newDamageRecord = new DamageRecord();
            BeanUtils.copyProperties(damageRecord,newDamageRecord);
            damageRecords.add(newDamageRecord);
        }
        return new JsonResult<>(JsonResult.SUCCESS,"success",damageRecords);
    }
    @PostMapping
    public JsonResult<?> addDamageRecord(DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new JsonResult<>(JsonResult.SUCCESS,"success",null);
    }

    @DeleteMapping("/{id}")
    public JsonResult<?> deleteDamageRecord(@PathVariable Long id){
        damageRecordService.deleteDamageRecord(id);
        return new JsonResult<>(JsonResult.SUCCESS,"success",null);
    }
    @PutMapping()
    public JsonResult<?> updateDamageRecord(Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new JsonResult<>(JsonResult.SUCCESS,"success",null);
    }
    @GetMapping()
    public JsonResult<List<DamageRecord>> getDamageRecordByUsername(String username){
        List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
        return new JsonResult<>(JsonResult.SUCCESS,"success",damageRecordByUserName);
    }
}
