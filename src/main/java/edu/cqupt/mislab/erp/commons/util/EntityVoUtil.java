package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;

/**
 * 用于转换Entity和VO的工具类，方法全部使用重载，第一个参数是Entity对象，第二个参数是VO对象
 */
public abstract class EntityVoUtil {

    public static void copyFieldsFromEntityToVo(UserStudentInfo userStudentInfo,UserStudentInfoBasicVo infoBasicVo){
        BeanCopyUtil.copyPropertiesSimple(userStudentInfo,infoBasicVo);
        if(userStudentInfo.getUserTeacherInfo() != null){
            infoBasicVo.setTeacherId(userStudentInfo.getUserTeacherInfo().getId());
        }
    }

    public static void copyFieldsFromEntityToVo(GameBasicInfo gameBasicInfo,GameDetailInfoVo detailInfoVo){

        BeanCopyUtil.copyPropertiesSimple(gameBasicInfo,detailInfoVo);

        detailInfoVo.setCreatorName(gameBasicInfo.getGameCreator().getStudentName());
        detailInfoVo.setPeriod(gameBasicInfo.getGameInitInfo().getPeriod());
        detailInfoVo.setTotalYear(gameBasicInfo.getGameInitInfo().getTotalYear());

        if(gameBasicInfo.getEnterpriseBasicInfos() == null){
            detailInfoVo.setEnterpriseNumber(0);
        }else {
            detailInfoVo.setEnterpriseNumber(gameBasicInfo.getEnterpriseBasicInfos().size());
        }
    }

    public static void copyFieldsFromEntityToVo(EnterpriseBasicInfo enterpriseBasicInfo,EnterpriseDetailInfoVo enterpriseDetailInfoVo){

        enterpriseDetailInfoVo.setId(enterpriseBasicInfo.getId());
        enterpriseDetailInfoVo.setCeoId(enterpriseBasicInfo.getEnterpriseCeo().getId());
        enterpriseDetailInfoVo.setCeoName(enterpriseBasicInfo.getEnterpriseCeo().getStudentName());
        enterpriseDetailInfoVo.setEnterpriseName(enterpriseBasicInfo.getEnterpriseName());
        enterpriseDetailInfoVo.setGameId(enterpriseBasicInfo.getGameInfo().getId());
        enterpriseDetailInfoVo.setEnterpriseStatus(enterpriseBasicInfo.getEnterpriseStatus());

        if(enterpriseBasicInfo.getEnterpriseMemberInfos() == null){
            enterpriseDetailInfoVo.setEnterpriseMemberNumber(0);
        }else {
            enterpriseDetailInfoVo.setEnterpriseMemberNumber(enterpriseBasicInfo.getEnterpriseMemberInfos().size());
        }
    }

    public static void copyFieldsFromEntityToVo(EnterpriseMemberInfo enterpriseMemberInfo,EnterpriseMemberDisplayVo displayVo){

        displayVo.setId(enterpriseMemberInfo.getId());
        displayVo.setStudentAccount(enterpriseMemberInfo.getStudentInfo().getStudentAccount());
        displayVo.setStudentName(enterpriseMemberInfo.getStudentInfo().getStudentName());
        displayVo.setCollege(enterpriseMemberInfo.getStudentInfo().getMajorInfo().getCollege().getCollege());
        displayVo.setMajor(enterpriseMemberInfo.getStudentInfo().getMajorInfo().getMajor());
        displayVo.setGameContributionRate(enterpriseMemberInfo.getGameContributionRate());
        displayVo.setAvatarLocation(enterpriseMemberInfo.getStudentInfo().getUserAvatarInfo().getAvatarLocation());
    }

    public static IsoDisplayVo copyFieldsFromEntityToVo(IsoDevelopInfo isoDevelopInfo) {
        IsoDisplayVo isoDisplayVo = new IsoDisplayVo();

        isoDisplayVo.setId(isoDevelopInfo.getId());

        // 每期维护费用
        isoDisplayVo.setIsoMaintainCost(isoDevelopInfo.getIsoBasicInfo().getIsoMaintainCost());
        // ISO认证名称
        isoDisplayVo.setIsoName(isoDevelopInfo.getIsoBasicInfo().getIsoName());
        // 每期认证费用
        isoDisplayVo.setIsoResearchCost(isoDevelopInfo.getIsoBasicInfo().getIsoResearchCost());
        // 需要认证的总期数
        isoDisplayVo.setIsoResearchPeriod(isoDevelopInfo.getIsoBasicInfo().getIsoResearchPeriod());

        // 认证状态
        isoDisplayVo.setIsoStatus(isoDevelopInfo.getIsoStatus());
        // 已经认证的期数
        isoDisplayVo.setResearchedPeriod(isoDevelopInfo.getResearchedPeriod());

        return isoDisplayVo;
    }

    public static MarketDisplayVo copyFieldsFromEntityToVo(MarketDevelopInfo marketDevelopInfo) {

        MarketDisplayVo marketDisplayVo = new MarketDisplayVo();

        // id同marketDevelopId
        marketDisplayVo.setId(marketDevelopInfo.getId());

        // 每期维护费用
        marketDisplayVo.setMarketMaintainCost(marketDevelopInfo.getMarketBasicInfo().getMarketMaintainCost());
        // 市场名称
        marketDisplayVo.setMarketName(marketDevelopInfo.getMarketBasicInfo().getMarketName());
        // 每期开拓费用
        marketDisplayVo.setMarketResearchCost(marketDevelopInfo.getMarketBasicInfo().getMarketResearchCost());
        // 完成开发所需要的总周期
        marketDisplayVo.setMarketResearchPeriod(marketDevelopInfo.getMarketBasicInfo().getMarketResearchPeriod());

        // 已经开拓的周期数
        marketDisplayVo.setResearchedPeriod(marketDevelopInfo.getResearchedPeriod());
        // 当前开拓状态
        marketDisplayVo.setMarketStatus(marketDevelopInfo.getMarketStatus());

        return marketDisplayVo;
    }

    public static ProductDisplayVo copyFieldsFromEntityToVo(ProductDevelopInfo productDevelopInfo) {

        ProductDisplayVo productDisplayVo = new ProductDisplayVo();

        productDisplayVo.setId(productDevelopInfo.getId());

        // 产品名称
        productDisplayVo.setProductName(productDevelopInfo.getProductBasicInfo().getProductName());
        // 每期研发费用
        productDisplayVo.setProductResearchCost(productDevelopInfo.getProductBasicInfo().getProductResearchCost());
        // 研发总期数
        productDisplayVo.setProductResearchPeriod(productDevelopInfo.getProductBasicInfo().getProductResearchPeriod());

        // 当前研发状态
        productDisplayVo.setProductDevelopStatus(productDevelopInfo.getProductDevelopStatus());
        // 已研发的周期数
        productDisplayVo.setDevelopedPeriod(productDevelopInfo.getDevelopedPeriod());

        return productDisplayVo;
    }

}
