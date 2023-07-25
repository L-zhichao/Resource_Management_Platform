package selab.desktop.resource_management.itemManagement.controller;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.itemManagement.domain.DamageRecord;
import selab.desktop.resource_management.itemManagement.service.DamageRecordService;
import selab.desktop.resource_management.itemManagement.utils.Result;

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
        return new Result<>(20000,"查询成功",damageRecords);
    }
    @PostMapping
    public Result<?> addDamageRecord(DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new Result<>(20000,"上报成功",null);
    }

    @DeleteMapping()
    public Result<?> deleteDamageRecord(Long damageRecordId){
        damageRecordService.deleteDamageRecord(damageRecordId);
        return new Result<>(20000,"删除成功",null);
    }
    @PutMapping()
    public Result<?> updateDamageRecord(Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new Result<>(20000,"修改成功",null);
    }
}
