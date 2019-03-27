package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.*;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDevelopDetailVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDevelopDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductEnterpriseBasicVo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于转换Entity和VO的工具类，方法全部使用重载，第一个参数是Entity对象，第二个参数是VO对象
 */
public abstract class EntityVoUtil {

    public static void copyFieldsFromEntityToVo(UserStudentInfo userStudentInfo,UserStudentInfoBasicVo infoBasicVo){

        BeanCopyUtil.copyPropertiesSimple(userStudentInfo,infoBasicVo);

        infoBasicVo.setMajorInfo(userStudentInfo.getMajorInfo());

        infoBasicVo.setUserAvatarInfo(userStudentInfo.getUserAvatarInfo());

        if(userStudentInfo.getUserTeacherInfo() != null){
            infoBasicVo.setTeacherId(userStudentInfo.getUserTeacherInfo().getId());
        }
    }

    public static void copyFieldsFromEntityToVo(GameBasicInfo gameBasicInfo,GameDetailInfoVo detailInfoVo){

        detailInfoVo.setId(gameBasicInfo.getId());
        detailInfoVo.setTotalYear(gameBasicInfo.getGameInitBasicInfo().getTotalYear());
        detailInfoVo.setPeriod(gameBasicInfo.getGameInitBasicInfo().getPeriodOfOneYear());
        detailInfoVo.setGameStatus(gameBasicInfo.getGameStatus());
        detailInfoVo.setGameName(gameBasicInfo.getGameName());
        detailInfoVo.setCreatorName(gameBasicInfo.getUserStudentInfo().getStudentName());
        detailInfoVo.setGameCreateTime(gameBasicInfo.getGameCreateTime());

        if(gameBasicInfo.getEnterpriseBasicInfos() == null){

            detailInfoVo.setEnterpriseNumber(0);
        }else {

            detailInfoVo.setEnterpriseNumber(gameBasicInfo.getEnterpriseBasicInfos().size());
        }
    }

    public static void copyFieldsFromEntityToVo(EnterpriseBasicInfo enterpriseBasicInfo,EnterpriseDetailInfoVo enterpriseDetailInfoVo){

        enterpriseDetailInfoVo.setId(enterpriseBasicInfo.getId());
        enterpriseDetailInfoVo.setCeoId(enterpriseBasicInfo.getUserStudentInfo().getId());
        enterpriseDetailInfoVo.setCeoName(enterpriseBasicInfo.getUserStudentInfo().getStudentName());
        enterpriseDetailInfoVo.setEnterpriseName(enterpriseBasicInfo.getEnterpriseName());
        enterpriseDetailInfoVo.setGameId(enterpriseBasicInfo.getGameBasicInfo().getId());
        enterpriseDetailInfoVo.setEnterpriseStatus(enterpriseBasicInfo.getEnterpriseStatus());

        if(enterpriseBasicInfo.getEnterpriseMemberInfos() == null){
            enterpriseDetailInfoVo.setEnterpriseMemberNumber(0);
        }else {
            enterpriseDetailInfoVo.setEnterpriseMemberNumber(enterpriseBasicInfo.getEnterpriseMemberInfos().size());
        }
    }

    public static void copyFieldsFromEntityToVo(EnterpriseMemberInfo enterpriseMemberInfo,EnterpriseMemberDisplayVo displayVo){

        displayVo.setId(enterpriseMemberInfo.getId());
        displayVo.setUserStudentId(enterpriseMemberInfo.getUserStudentInfo().getId());
        displayVo.setStudentAccount(enterpriseMemberInfo.getUserStudentInfo().getStudentAccount());
        displayVo.setStudentName(enterpriseMemberInfo.getUserStudentInfo().getStudentName());
        displayVo.setCollege(enterpriseMemberInfo.getUserStudentInfo().getMajorInfo().getCollege().getCollege());
        displayVo.setMajor(enterpriseMemberInfo.getUserStudentInfo().getMajorInfo().getMajor());
        displayVo.setGameContributionRate(enterpriseMemberInfo.getGameContributionRate());

        //不一定存在
        if(enterpriseMemberInfo.getUserStudentInfo().getUserAvatarInfo() != null){

            displayVo.setAvatarLocation(enterpriseMemberInfo.getUserStudentInfo().getUserAvatarInfo().getAvatarLocation());
        }
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


    /**
     * @author yuanyiwen
     * @description flag为返回类型，防止重载冲突
     * @date 10:41 2019/3/16
     **/
    public static ProdlineProduceDisplayVo copyFieldsFromEntityToVo(ProdlineProduceInfo prodlineProduceInfo, ProdlineProduceDisplayVo flag) {
        ProdlineProduceDisplayVo prodlineProduceDisplayVo = new ProdlineProduceDisplayVo();

        prodlineProduceDisplayVo.setId(prodlineProduceInfo.getId());

        // 生产状态
        prodlineProduceDisplayVo.setProdlineProduceStatus(prodlineProduceInfo.getProdlineProduceStatus());
        // 生产线类型
        prodlineProduceDisplayVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());
        // 已经生生产的周期数
        prodlineProduceDisplayVo.setProducedPeriod(prodlineProduceInfo.getProducedPeriod());
        // 生产线当前可生产的产品名称
        prodlineProduceDisplayVo.setProductName(prodlineProduceInfo.getProductDevelopInfo().getProductBasicInfo().getProductName());

        return prodlineProduceDisplayVo;
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


    public static ProdlineDevelopDisplayVo copyFieldsFromEntityToVo(ProdlineDevelopInfo prodlineDevelopInfo) {
        ProdlineDevelopDisplayVo prodlineDevelopDisplayVo = new ProdlineDevelopDisplayVo();

        prodlineDevelopDisplayVo.setId(prodlineDevelopInfo.getId());

        // 已经安装的周期数
        prodlineDevelopDisplayVo.setDevelopedPeriod(prodlineDevelopInfo.getDevelopedPeriod());
        // 当前安装状态
        prodlineDevelopDisplayVo.setProdlineDevelopStatus(prodlineDevelopInfo.getProdlineDevelopStatus());
        // 安装所需的总周期数
        prodlineDevelopDisplayVo.setProdlineSetupPeriod(prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineSetupPeriod());
        // 生产线类型
        prodlineDevelopDisplayVo.setProdlineType(prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());
        // 该生产线上生产的产品名称
        prodlineDevelopDisplayVo.setProductName(prodlineDevelopInfo.getProductDevelopInfo().getProductBasicInfo().getProductName());

        return prodlineDevelopDisplayVo;
    }


    public static FactoryDisplayVo copyFieldsFromEntityToVo(FactoryHoldingInfo factoryHoldingInfo, List<ProdlineProduceDisplayVo> prodlineProduceDisplayVoList, List<ProdlineDevelopDisplayVo> prodlineDevelopDisplayVoList) {

        FactoryDisplayVo factoryDisplayVo = new FactoryDisplayVo();

        FactoryBasicInfo factoryBasicInfo = factoryHoldingInfo.getFactoryBasicInfo();

        factoryDisplayVo.setId(factoryHoldingInfo.getId());

        // 厂房最大容量
        factoryDisplayVo.setFactoryCapacity(factoryBasicInfo.getFactoryCapacity());
        // 厂房的当前容量
        factoryDisplayVo.setCurrentCapacity(prodlineDevelopDisplayVoList.size()+prodlineProduceDisplayVoList.size());
        // 厂房拥有状态（自建的：拥有中 / 租来的：租赁中）
        factoryDisplayVo.setFactoryHoldingStatus(factoryHoldingInfo.getFactoryHoldingStatus());
        // 厂房编号，取的是厂房id后三位，不足位补0
        factoryDisplayVo.setFactoryNumber(NumberFormatUtil.factoryNumberFormat(factoryHoldingInfo.getId()));
        // 厂房拥有状态（已修建or已出售 / 租赁中or租赁）
        factoryDisplayVo.setDevelopStatus(factoryHoldingInfo.isEnable());
        // 厂房类型
        factoryDisplayVo.setFactoryType(factoryBasicInfo.getFactoryType());
        // 生产中的生产线
        factoryDisplayVo.setProdlineProduceDisplayVoList(prodlineProduceDisplayVoList);
        // 修建中的生产线
        factoryDisplayVo.setProdlineDevelopDisplayVoList(prodlineDevelopDisplayVoList);

        return factoryDisplayVo;
    }


    public static FactoryDetailVo copyFieldsFromEntityToVo(FactoryHoldingInfo factoryHoldingInfo) {
        FactoryDetailVo factoryDetailVo = new FactoryDetailVo();

        FactoryBasicInfo factoryBasicInfo = factoryHoldingInfo.getFactoryBasicInfo();

        factoryDetailVo.setId(factoryHoldingInfo.getId());

        // 展示为厂房状态，实际为自建的厂房状态 / 租来的厂房状态（true为拥有中 / 租赁中，false为已出售 / 暂停租赁）
        factoryDetailVo.setDevelopStatus(factoryHoldingInfo.isEnable());
        // 厂房容量
        factoryDetailVo.setFactoryCapacity(factoryBasicInfo.getFactoryCapacity());
        // 厂房建造完成后每期的折旧费用
        factoryDetailVo.setFactoryDepreciation(factoryBasicInfo.getFactoryDepreciation());
        // 厂房的拥有状态
        factoryDetailVo.setFactoryHoldingStatus(factoryHoldingInfo.getFactoryHoldingStatus());
        // 厂房修建完成后每期的维护费用
        factoryDetailVo.setFactoryMaintainCost(factoryBasicInfo.getFactoryMaintainCost());
        // 修建该厂房每期所需要的费用
        factoryDetailVo.setFactoryMakeCost(factoryBasicInfo.getFactoryMakeCost());
        // 修建该厂房所需要的周期数
        factoryDetailVo.setFactoryMakePeriod(factoryBasicInfo.getFactoryMakePeriod());
        // 厂房编号
        factoryDetailVo.setFactoryNumber(NumberFormatUtil.factoryNumberFormat(factoryHoldingInfo.getId()));
        // 厂房类型
        factoryDetailVo.setFactoryType(factoryBasicInfo.getFactoryType());

        return factoryDetailVo;
    }


    /**
     * @author yuanyiwen
     * @description flag为返回类型，防止重载冲突
     * @date 15:34 2019/3/24
     **/
    public static FactoryDevelopDisplayVo copyFieldsFromEntityToVo(FactoryDevelopInfo factoryDevelopInfo, FactoryDevelopDisplayVo flag) {
        FactoryDevelopDisplayVo factoryDevelopDisplayVo = new FactoryDevelopDisplayVo();

        factoryDevelopDisplayVo.setId(factoryDevelopInfo.getId());

        // 厂房类型
        factoryDevelopDisplayVo.setFactoryType(factoryDevelopInfo.getFactoryBasicInfo().getFactoryType());
        // 厂房临时编号
        factoryDevelopDisplayVo.setFactoryNumber(NumberFormatUtil.factoryNumberFormat(factoryDevelopInfo.getId()));
        // 厂房容量
        factoryDevelopDisplayVo.setFactoryCapacity(factoryDevelopInfo.getFactoryBasicInfo().getFactoryCapacity());
        // 厂房的修建状态（true为修建中 false为暂停修建）
        factoryDevelopDisplayVo.setDevelopStatus(factoryDevelopInfo.isEnable());

        return factoryDevelopDisplayVo;
    }


    public static FactoryDevelopDetailVo copyFieldsFromEntityToVo(FactoryDevelopInfo factoryDevelopInfo, FactoryDevelopDetailVo flag) {
        FactoryDevelopDetailVo factoryDevelopDetailVo = new FactoryDevelopDetailVo();

        factoryDevelopDetailVo.setId(factoryDevelopInfo.getId());

        // 开始建造的周期
        factoryDevelopDetailVo.setBeginPeriod(factoryDevelopInfo.getBeginPeriod());
        // 已经建造的周期
        factoryDevelopDetailVo.setDevelopedPeriod(factoryDevelopInfo.getDevelopedPeriod());
        // 厂房容量
        factoryDevelopDetailVo.setFactoryCapacity(factoryDevelopDetailVo.getFactoryCapacity());
        // 即非租赁
        factoryDevelopDetailVo.setFactoryHoldingStatus(FactoryHoldingStatus.HOLDING);
        // 厂房建造所需要的总周期
        factoryDevelopDetailVo.setFactoryMakePeriod(factoryDevelopInfo.getFactoryBasicInfo().getFactoryMakePeriod());
        // 厂房的修建情况（true修建中 false暂停修建）
        factoryDevelopDetailVo.setDevelopStatus(factoryDevelopInfo.isEnable());
        // 厂房类型
        factoryDevelopDetailVo.setFactoryType(factoryDevelopInfo.getFactoryBasicInfo().getFactoryType());

        return factoryDevelopDetailVo;
    }


    /******************* 下面本来是给ProductionPlanServiceImpl自用的，但是由于某种不可抗力被挪到这里来了 ************************/

    public static List<ProdlineTypeVo> copyFieldsFromEntityToVo(List<ProdlineProduceInfo> prodlineProduceInfoList, ProdlineTypeVo flag) {

        if(prodlineProduceInfoList == null) {
            return new ArrayList<>();
        }

        List<ProdlineTypeVo> prodlineTypeVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            ProdlineTypeVo prodlineTypeVo = new ProdlineTypeVo();

            prodlineTypeVo.setId(prodlineProduceInfo.getId());
            prodlineTypeVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());

            prodlineTypeVoList.add(prodlineTypeVo);
        }

        return prodlineTypeVoList;
    }


    public static List<ProdlineProduceDisplayVo> copyFieldsFromEntityToVo(List<ProdlineProduceInfo> prodlineProduceInfoList, ProdlineProduceDisplayVo flag) {

        if(prodlineProduceInfoList == null) {
            return new ArrayList<>();
        }

        List<ProdlineProduceDisplayVo> prodlineProduceDisplayVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            prodlineProduceDisplayVoList.add(copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineProduceDisplayVo()));
        }
        return prodlineProduceDisplayVoList;
    }


    public static List<ProdlineDevelopDisplayVo> copyFieldsFromEntityToVo(List<ProdlineDevelopInfo> prodlineDevelopInfoList, ProdlineDevelopDisplayVo flag) {
        if(prodlineDevelopInfoList == null) {
            return new ArrayList<>();
        }

        List<ProdlineDevelopDisplayVo> prodlineDevelopDisplayVoList = new ArrayList<>();
        for(ProdlineDevelopInfo prodlineDevelopInfo : prodlineDevelopInfoList) {
            prodlineDevelopDisplayVoList.add(copyFieldsFromEntityToVo(prodlineDevelopInfo));
        }

        return prodlineDevelopDisplayVoList;
    }


    public static ProductProduceVo copyFieldsFromEntityToVo(ProdlineProduceInfo prodlineProduceInfo) {

        ProductProduceVo productProduceVo = new ProductProduceVo();

        productProduceVo.setId(prodlineProduceInfo.getId());
        productProduceVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineType());
        productProduceVo.setFactoryNumber(NumberFormatUtil.factoryNumberFormat(prodlineProduceInfo.getProdlineHoldingInfo().getFactoryHoldingInfo().getId()));

        return productProduceVo;
    }

    /******************************* 不可抗力影响结束；最后还是统一成了重载，虽然改的比较粗糙 ***********************************/

}
