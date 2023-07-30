package selab.desktop.resource_management.model.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.model.domain.itemManagement.DamageRecord;
import selab.desktop.resource_management.model.mapper.itemManagement.DamageRecordMapper;
import selab.desktop.resource_management.model.service.itemManagement.DamageRecordService;

import java.util.List;
@Service
public class DamageRecordServiceImpl implements DamageRecordService {
    @Autowired
    private DamageRecordMapper damageRecordMapper;

    @Override
    public void addDamageRecord(DamageRecord damageRecord) {
        damageRecordMapper.insert(damageRecord);
    }

    @Override
    public DamageRecord findDamageRecordById(Long damageRecordId) {
        DamageRecord damageRecord = damageRecordMapper.selectById(damageRecordId);
        return damageRecord;
    }

    @Override
    public void updateIsRead(Long damageRecordId) {
        DamageRecord damageRecord = damageRecordMapper.selectById(damageRecordId);
        damageRecord.setDamageRecordIsread(true);
        damageRecordMapper.updateById(damageRecord);
    }

    @Override
    public List<DamageRecord> findAllDamageRecord() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("ishandle",false);
        List<DamageRecord> damageRecords = damageRecordMapper.selectList(queryWrapper);
        return damageRecords;
    }

    @Override
    public void deleteDamageRecord(Long damageRecordId) {
        damageRecordMapper.deleteById(damageRecordId);
    }

    @Override
    public void updateIsHandle(Long damageRecordId) {
        DamageRecord damageRecord = damageRecordMapper.selectById(damageRecordId);
        damageRecord.setDamageRecordIshandle(true);
        damageRecordMapper.updateById(damageRecord);
    }

    @Override
    public List<DamageRecord> getDamageRecordByUserName(String username) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);
        List<DamageRecord> damageRecords = damageRecordMapper.selectList(queryWrapper);
        return damageRecords;
    }
}
