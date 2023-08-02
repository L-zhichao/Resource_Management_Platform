package selab.desktop.resource_management.controller.itemManagement;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.itemManagement.item.DamageRecord;
import selab.desktop.resource_management.service.itemManagement.DamageRecordService;
import selab.desktop.resource_management.utils.JsonResult;
;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/damage/record")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

    @GetMapping("/find")
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
    @PostMapping("/ADD")
    public JsonResult<?> addDamageRecord(DamageRecord damageRecord){
        damageRecordService.addDamageRecord(damageRecord);
        return new JsonResult<>(JsonResult.SUCCESS,"success",null);
    }

    @DeleteMapping("/{id}")
    public JsonResult<?> deleteDamageRecord(@PathVariable Long id){
        damageRecordService.deleteDamageRecord(id);
        return new JsonResult<>(JsonResult.SUCCESS,"success",null);
    }
    @PutMapping("/update")
    public JsonResult<?> updateDamageRecord(Long damageRecordId){
        damageRecordService.updateIsHandle(damageRecordId);
        return new JsonResult<>(JsonResult.SUCCESS,"success",null);
    }
    @GetMapping("/get")
    public JsonResult<List<DamageRecord>> getDamageRecordByUsername(String username){
        List<DamageRecord> damageRecordByUserName = damageRecordService.getDamageRecordByUserName(username);
        return new JsonResult<>(JsonResult.SUCCESS,"success",damageRecordByUserName);
    }
}
