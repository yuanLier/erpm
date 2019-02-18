package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import org.springframework.stereotype.Service;

@Service
public class GameModelInitServiceImpl implements GameModelInit {
    @Override
    public boolean initGameModel(Long gameId){
        return false;
    }
}
