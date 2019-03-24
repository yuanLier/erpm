package edu.cqupt.mislab.erp.game.compete.operation.produce.controller.factorymanagement;

import edu.cqupt.mislab.erp.game.compete.operation.produce.service.factorymanagement.FactoryManagementService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanyiwen
 * @create 2019-03-24 17:58
 * @description
 */
@Api
@CrossOrigin
@Validated
@RestController
@RequestMapping("/game/compete/operation/produce/factorymanagement")
public class FactoryManagementController {

    @Autowired
    private FactoryManagementService factoryManagementService;


}
