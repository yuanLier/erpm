package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineDisplayVo;
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

import java.util.List;

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

    public static FactoryDisplayVo copyFieldsFromEntityToVo(FactoryDevelopInfo factoryDevelopInfo, List<ProdlineDisplayVo> prodlineDisplayVoList) {

        FactoryDisplayVo factoryDisplayVo = new FactoryDisplayVo();

        FactoryBasicInfo factoryBasicInfo = factoryDevelopInfo.getFactoryHoldingInfo().getFactoryBasicInfo();

        factoryDisplayVo.setId(factoryDevelopInfo.getId());

        // 厂房最大容量
        factoryDisplayVo.setFactoryCapacity(factoryBasicInfo.getFactoryCapacity());
        // 厂房拥有状态（租赁or其他）
        factoryDisplayVo.setFactoryHoldingStatus(factoryDevelopInfo.getFactoryHoldingInfo().getFactoryHoldingStatus());
        // 厂房编号，取的是厂房id后三位，不足位补0
        factoryDisplayVo.setFactoryNumber(NumberFormatUtil.factoryNumberFormat(factoryDevelopInfo.getId()));
        // 厂房修建状态 todo 若厂房是租来的  这个值要怎么处理？--新建Vo处理 生产计划中只允许使用FactoryDevelopInfo
        factoryDisplayVo.setFactoryDevelopStatus(factoryDevelopInfo.getFactoryDevelopStatus());
        // 厂房类型
        factoryDisplayVo.setFactoryType(factoryBasicInfo.getFactoryType());
        // 厂房中的生产线
        factoryDisplayVo.setProdlineDisplayVoList(prodlineDisplayVoList);

        return factoryDisplayVo;
    }

    /**
     * @author yuanyiwen
     * @description flag为返回类型，防止重载冲突
     * @date 10:41 2019/3/16
     **/
    public static ProdlineDisplayVo copyFieldsFromEntityToVo(ProdlineProduceInfo prodlineProduceInfo, ProdlineDisplayVo flag) {
        ProdlineDisplayVo prodlineDisplayVo = new ProdlineDisplayVo();

        prodlineDisplayVo.setId(prodlineProduceInfo.getId());

        // 生产状态
        prodlineDisplayVo.setProdlineProduceStatus(prodlineProduceInfo.getProdlineProduceStatus());
        // 生产线类型
        prodlineDisplayVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());
        // 已经生生产的周期数
        prodlineDisplayVo.setProducedPeriod(prodlineProduceInfo.getProducedPeriod());
        // 生产线当前可生产的产品名称
        prodlineDisplayVo.setProductName(prodlineProduceInfo.getProductDevelopInfo().getProductBasicInfo().getProductName());

        return prodlineDisplayVo;
    }

    public static ProdlineDetailVo copyFieldsFromEntityToVo(ProdlineProduceInfo prodlineProduceInfo, ProdlineDetailVo flag) {
        ProdlineDetailVo prodlineDetailVo = new ProdlineDetailVo();

        prodlineDetailVo.setId(prodlineProduceInfo.getId());

        ProdlineBasicInfo prodlineBasicInfo = prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo();
        // 该生产线对产品生产周期的影响情况
        prodlineDetailVo.setExtraPeriod(prodlineBasicInfo.getExtraPeriod());
        // 转产所需费用
        prodlineDetailVo.setProdlineChangeCost(prodlineBasicInfo.getProdlineChangeCost());
        // 转产周期
        prodlineDetailVo.setProdlineChangePeriod(prodlineBasicInfo.getProdlineChangePeriod());
        // 生产线投入使用后，每期折旧的价值。完工当期不折旧
        prodlineDetailVo.setProdlineDepreciation(prodlineBasicInfo.getProdlineDepreciation());
        // 生产线每期的维修费用
        prodlineDetailVo.setProdlineMainCost(prodlineBasicInfo.getProdlineMainCost());
        // 生产线安装周期
        prodlineDetailVo.setProdlineSetupPeriod(prodlineBasicInfo.getProdlineSetupPeriod());
        // 生产线安装费用
        prodlineDetailVo.setProdlineSetupPeriodPrice(prodlineBasicInfo.getProdlineSetupPeriodPrice());
        // 生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值
        prodlineDetailVo.setProdlineStumpcost(prodlineBasicInfo.getProdlineStumpcost());
        // 生产线类型
        prodlineDetailVo.setProdlineType(prodlineBasicInfo.getProdlineType());
        // 该生产线所生产的产品名称
        prodlineDetailVo.setProductType(prodlineProduceInfo.getProductDevelopInfo().getProductBasicInfo().getProductName());

        return prodlineDetailVo;
    }

}
