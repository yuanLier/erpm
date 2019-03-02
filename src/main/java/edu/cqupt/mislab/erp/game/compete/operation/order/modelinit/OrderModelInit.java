package edu.cqupt.mislab.erp.game.compete.operation.order.modelinit;

import edu.cqupt.mislab.erp.commons.basic.ModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import org.springframework.stereotype.Component;

@Component
public class OrderModelInit implements ModelInit {

    @Override
    public boolean init(){
        return true;
    }
}
