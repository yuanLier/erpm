package edu.cqupt.mislab.erp.game.compete.basic;

import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;

/**
 * @author yuanyiwen
 * @create 2019-05-12 11:32
 * @description 周期推进模板方法
 */
public interface ModelAdvance {

    /**
     * 这个方法用来记录比赛期间各模块的历史数据
     * @param enterpriseBasicInfo
     * @return
     */
    boolean modelHistory(EnterpriseBasicInfo enterpriseBasicInfo);


    /**
     * 这个方法用来实现各模块自己的周期推进
     * @param enterpriseBasicInfo
     * @return
     */
    boolean modelAdvance(EnterpriseBasicInfo enterpriseBasicInfo);


    /**
     * 推进模板
     * @param enterpriseBasicInfo
     * @return
     */
    default boolean advance(EnterpriseBasicInfo enterpriseBasicInfo) {

        // 历史记录是否成功
        boolean historyBoolean = modelHistory(enterpriseBasicInfo);

        // 周期推进是否成功
        boolean advanceBoolean = modelAdvance(enterpriseBasicInfo);

        return historyBoolean && advanceBoolean;
    }
}
