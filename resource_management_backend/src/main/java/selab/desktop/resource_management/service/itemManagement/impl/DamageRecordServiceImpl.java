package selab.desktop.resource_management.service.itemManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.itemManagement.DemageRecord.DamageRecord;
import selab.desktop.resource_management.mapper.itemManagement.DamageRecordMapper;
import selab.desktop.resource_management.service.itemManagement.DamageRecordService;

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
        damageRecord.setDamageRecordIsHandle(true);
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
