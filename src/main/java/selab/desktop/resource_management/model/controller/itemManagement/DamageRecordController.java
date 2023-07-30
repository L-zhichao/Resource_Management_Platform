package selab.desktop.resource_management.model.controller.itemManagement;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.model.domain.itemManagement.DamageRecord;
import selab.desktop.resource_management.model.service.itemManagement.DamageRecordService;
import selab.desktop.resource_management.utils.Result;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/damage/record")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    @GetMapping()
    public Result<List<DamageRecord>> findAllDamageRecord(){
        List<DamageRecord> allDamageRecord = damageRecordService.findAllDamageRecord();
        List<DamageRecord> damageRecords =new ArrayList<>();
        for (DamageRecord damageRecord:allDamageRecord){
            damageRecordService.updateIsRead(damageRecord.getDamageRecordId());
            DamageRecord newDamageRecord = new DamageRecord();
            BeanUtils.copyProperties(damageRecord,newDamageRecord);
            damageRecords.add(newDamageRecord);
        }
        return new Result<>(200,"success",damageRecords);
    }
    @PostMapping
    public Result<?> addDamageRecord(DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new Result<>(200,"success",null);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteDamageRecord(@PathVariable Long id){
        damageRecordService.deleteDamageRecord(id);
        return new Result<>(200,"success",null);
    }
    @PutMapping()
    public Result<?> updateDamageRecord(Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new Result<>(200,"success",null);
    }
    @GetMapping()
    public Result<List<DamageRecord>> getDamageRecordByUsername(String username){
        List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
        return new Result<>(200,"success",damageRecordByUserName);
    }
}
