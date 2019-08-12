package edu.cqupt.mislab.erp.game.manage.model.entity;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/5 19:15
 * @Description: 企业状态信息
 */

public enum EnterpriseStatusEnum {

    /*
    企业创建并等待成员加入状态
     */
    CREATE,
    /*
    企业准备开始比赛
     */
    SURE,
    /*
    企业正常运营，这个阶段CEO可以直接添加成员进来
     */
    PLAYING,
    /*
    企业破产
     */
    BANKRUPT,
    /*
    企业正常完成所有的周期完结
     */
    OVER,
    ;
}
