package selab.desktop.resource_management.itemManagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.itemManagement.domain.DamageRecord;
import selab.desktop.resource_management.itemManagement.mapper.DamageRecordMapper;
import selab.desktop.resource_management.itemManagement.service.DamageRecordService;

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
