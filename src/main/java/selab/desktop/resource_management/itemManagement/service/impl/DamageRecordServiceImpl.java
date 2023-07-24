package selab.desktop.resource_management.itemManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import selab.desktop.resource_management.itemManagement.mapper.DamageRecordMapper;
import selab.desktop.resource_management.itemManagement.service.DamageRecordService;

public class DamageRecordServiceImpl implements DamageRecordService {
    @Autowired
    private DamageRecordMapper damageRecordMapper;
}
