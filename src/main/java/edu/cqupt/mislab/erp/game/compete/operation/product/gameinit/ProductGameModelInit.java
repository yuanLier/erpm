package edu.cqupt.mislab.erp.game.compete.operation.product.gameinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import org.springframework.stereotype.Component;

@Component
public class ProductGameModelInit implements GameModelInit {

    /**
     * 初始化一个比赛里面的产品模块数据
     * @param gameId：初始化哪一个比赛
     * @return
     */
    @Override
    public boolean initGameModel(Long gameId){
        return true;
    }
}
