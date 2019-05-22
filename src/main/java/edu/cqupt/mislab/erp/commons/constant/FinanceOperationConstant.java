package edu.cqupt.mislab.erp.commons.constant;

/**
 * @author yuanyiwen
 * @create 2019-05-21 22:55
 * @description 造成金额改变的操作
 *      所有操作必须在这里注明 不允许出现魔法值
 */
public interface FinanceOperationConstant {

    String INIT_AMOUNT = "系统分配的初始金额";

    String ISO_DEVELOP = "iso每周期认证所需要支付的金额";

    String ISO_MAINTAIN = "认证结束后，iso每周期维护所需要支付的金额";

    String MARKET_DEVELOP = "市场每周期开拓所需要支付的金额";

    String MARKET_MAINTAIN = "开拓结束后，市场每周期维护所需要支付的金额";

    String PRODUCT_DEVELOP = "产品每周期研发所需要支付的金额";




}
