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

    //查询所有物品损坏上报记录
    @GetMapping()
    public Result<List<DamageRecord>> all(){
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
    //添加物品上报记录
    @PostMapping
    public Result<?> add(DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new Result<>(200,"success",null);
    }

    //
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        damageRecordService.deleteDamageRecord(id);
        return new Result<>(200,"success",null);
    }
    //将物品上报记录处理的是否处理改为已处理
    @PutMapping()
    public Result<?> update(Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new Result<>(200,"success",null);
    }
    //通过用户姓名查询所有的上报记录
    @RequestMapping("/all")
    public Result<List<DamageRecord>> select(String username){
        List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
        return new Result<>(200,"success",damageRecordByUserName);
    }
}
