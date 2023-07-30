package selab.desktop.resource_management.model.service.itemManagement;

import selab.desktop.resource_management.model.domain.itemManagement.DamageRecord;

import java.util.List;

public interface DamageRecordService {
    void addDamageRecord(DamageRecord damageRecord);

    DamageRecord findDamageRecordById(Long damageRecordId);

    void updateIsRead(Long damageRecordId);

    List<DamageRecord> findAllDamageRecord();

    void deleteDamageRecord(Long damageRecordId);
    void updateIsHandle(Long damageRecordId);
    List<DamageRecord> getDamageRecordByUserName(String username);


}
