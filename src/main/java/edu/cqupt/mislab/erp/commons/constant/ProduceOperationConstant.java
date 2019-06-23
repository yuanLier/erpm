package edu.cqupt.mislab.erp.commons.constant;

/**
 * @author yuanyiwen
 * @create 2019-06-02 18:45
 * @description 生产变动过程中所进行的操作
 */
public interface ProduceOperationConstant {

    String FACTORY_DEVELOPED = "修建";
    String FACTORY_SOLD = "出售";
    String FACTORY_LEASE = "租赁";
    String FACTORY_LEASE_PAUSE = "停租";


    String PRODLINE_DEVELOPED = "安装";
    String PRODLINE_SOLD = "出售";
    String PRODLINE_NOT_USABLE = "厂房停租造成的生产线不可用";


    /**
     * 包括企业破产和比赛结束
     */
    String BUSINESS_END = "企业结束经营";
}
