package selab.desktop.resource_management.itemManagement.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.itemManagement.domain.DamageRecord;
import selab.desktop.resource_management.itemManagement.service.DamageRecordService;
import selab.desktop.resource_management.itemManagement.utils.JsonResult;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/damage/record")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    //查询所有物品损坏上报记录
    @GetMapping()
    public JsonResult<List<DamageRecord>> all(){
        List<DamageRecord> allDamageRecord = damageRecordService.findAllDamageRecord();
        List<DamageRecord> damageRecords =new ArrayList<>();
        for (DamageRecord damageRecord:allDamageRecord){
            damageRecordService.updateIsRead(damageRecord.getDamageRecordId());
            DamageRecord newDamageRecord = new DamageRecord();
            BeanUtils.copyProperties(damageRecord,newDamageRecord);
            damageRecords.add(newDamageRecord);
        }
        return new JsonResult<>(200,"success",damageRecords);
    }
    //添加物品上报记录
    @PostMapping
    public JsonResult<?> add(DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new JsonResult<>(200,"success",null);
    }

    //
    @DeleteMapping("/{id}")
    public JsonResult<?> delete(@PathVariable Long id){
        damageRecordService.deleteDamageRecord(id);
        return new JsonResult<>(200,"success",null);
    }
    //将物品上报记录处理的是否处理改为已处理
    @PutMapping()
    public JsonResult<?> update(Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new JsonResult<>(200,"success",null);
    }
    //通过用户姓名查询所有的上报记录
    @RequestMapping("/all")
    public JsonResult<List<DamageRecord>> select(String username){
        List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
        return new JsonResult<>(200,"success",damageRecordByUserName);
    }
}
