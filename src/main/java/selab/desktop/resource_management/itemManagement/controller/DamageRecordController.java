package selab.desktop.resource_management.itemManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selab.desktop.resource_management.itemManagement.domain.DamageRecord;
import selab.desktop.resource_management.itemManagement.service.DamageRecordService;

@RestController
@RequestMapping("/damage/record")
public class DamageRecordController {
    @Autowired
    private DamageRecordService damageRecordService;

}
