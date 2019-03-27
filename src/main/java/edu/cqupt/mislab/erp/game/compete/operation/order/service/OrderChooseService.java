package edu.cqupt.mislab.erp.game.compete.operation.order.service;

public interface OrderChooseService {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/13 18:11
     * @Description: 加载数据库里面的订单选择信息到内存，防止后端应用断线造成的数据混乱
     **/
    void loadOrderChooseInfoFromDatabase();

    //
}
