package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.game.compete.basic.ModelAdvance;
import org.springframework.stereotype.Service;

@Service
public class ModelAdvanceServiceImpl implements ModelAdvance {

    @Override
    public boolean advance(Long gameId){
        return false;
    }
}
