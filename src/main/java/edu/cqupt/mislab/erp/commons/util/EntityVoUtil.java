package edu.cqupt.mislab.erp.commons.util;

import edu.cqupt.mislab.erp.game.compete.operation.finance.model.entity.LoanEnterpriseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.finance.model.vo.LoanEnterpriseDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.vo.MarketBasicTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.GameOrderVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.OrderDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHoldingStatus;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.GameFactoryBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.GameProdlineBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineProduceInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.*;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductTypeVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.GameTransportBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.MaterialStockInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.entity.ProductStockInfo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.MaterialOrderDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.MaterialStockDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.ProductStockDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.stock.model.vo.TransportMethodDisplayVo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseMemberInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseMemberDisplayVo;
import edu.cqupt.mislab.erp.game.manage.model.vo.GameDetailInfoVo;
import edu.cqupt.mislab.erp.user.model.entity.UserStudentInfo;
import edu.cqupt.mislab.erp.user.model.vo.UserStudentInfoBasicVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chuyunfei
 * @description 用于转换Entity和VO的工具类，方法全部使用重载，第一个参数是Entity对象，第二个参数是VO对象
 * @date 19:36 2019/5/3
 **/
public abstract class EntityVoUtil {

    public static void copyFieldsFromEntityToVo(UserStudentInfo userStudentInfo,UserStudentInfoBasicVo infoBasicVo){

        BeanCopyUtil.copyPropertiesSimple(userStudentInfo,infoBasicVo);

        infoBasicVo.setMajorBasicInfo(userStudentInfo.getMajorBasicInfo());

        infoBasicVo.setUserAvatarInfo(userStudentInfo.getUserAvatarInfo());

        if(userStudentInfo.getUserTeacherInfo() != null){
            infoBasicVo.setTeacherId(userStudentInfo.getUserTeacherInfo().getId());
        }
    }

    public static void copyFieldsFromEntityToVo(GameBasicInfo gameBasicInfo,GameDetailInfoVo detailInfoVo){

        detailInfoVo.setId(gameBasicInfo.getId());
        detailInfoVo.setTotalYear(gameBasicInfo.getGameInitBasicInfo().getTotalYear());
        detailInfoVo.setPeriod(gameBasicInfo.getGameInitBasicInfo().getPeriodOfOneYear());
        detailInfoVo.setGameStatusEnum(gameBasicInfo.getGameStatus());
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
        enterpriseDetailInfoVo.setEnterpriseStatusEnum(enterpriseBasicInfo.getEnterpriseStatus());

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
        displayVo.setCollege(enterpriseMemberInfo.getUserStudentInfo().getMajorBasicInfo().getCollege().getCollege());
        displayVo.setMajor(enterpriseMemberInfo.getUserStudentInfo().getMajorBasicInfo().getMajor());
        displayVo.setGameContributionRate(enterpriseMemberInfo.getGameContributionRate());

        //不一定存在
        if(enterpriseMemberInfo.getUserStudentInfo().getUserAvatarInfo() != null){

            displayVo.setAvatarLocation(enterpriseMemberInfo.getUserStudentInfo().getUserAvatarInfo().getAvatarLocation());
        }
    }


    public static OrderDisplayVo copyFieldsFromEntityToVo(GameOrderInfo gameOrderInfo) {
        OrderDisplayVo orderDisplayVo = new OrderDisplayVo();

        orderDisplayVo.setId(gameOrderInfo.getId());

        // 所在市场
        MarketBasicTypeVo marketBasicTypeVo = new MarketBasicTypeVo();
        BeanCopyUtil.copyPropertiesSimple(gameOrderInfo.getMarketBasicInfo(), marketBasicTypeVo);
        orderDisplayVo.setMarketType(marketBasicTypeVo);
        // 所需产品
        ProductTypeVo productTypeVo = new ProductTypeVo();
        BeanCopyUtil.copyPropertiesSimple(gameOrderInfo.getProductBasicInfo(), productTypeVo);
        orderDisplayVo.setProductType(productTypeVo);

        orderDisplayVo.setDeliveryPeriod(gameOrderInfo.getDeliveryPeriod());
        orderDisplayVo.setOrderNumber(NumberFormatUtil.numberFormat(gameOrderInfo.getId(), 5));
        orderDisplayVo.setOrderStatus(gameOrderInfo.isOrderStatus());
        orderDisplayVo.setProductNumber(gameOrderInfo.getProductNumber());
        orderDisplayVo.setProductPrice(gameOrderInfo.getPrice());
        orderDisplayVo.setTotalPrice(gameOrderInfo.getPrice()*gameOrderInfo.getProductNumber());
        orderDisplayVo.setYear(gameOrderInfo.getYear());

        return orderDisplayVo;
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
        prodlineProduceDisplayVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineType());
        // 已经生生产的周期数
        prodlineProduceDisplayVo.setProducedPeriod(prodlineProduceInfo.getProducedPeriod());
        // 生产线当前可生产的产品名称
        prodlineProduceDisplayVo.setProductName(prodlineProduceInfo.getProductDevelopInfo().getProductBasicInfo().getProductName());

        return prodlineProduceDisplayVo;
    }


    public static ProdlineDetailVo copyFieldsFromEntityToVo(ProdlineProduceInfo prodlineProduceInfo, ProdlineDetailVo flag) {
        ProdlineDetailVo prodlineDetailVo = new ProdlineDetailVo();

        prodlineDetailVo.setId(prodlineProduceInfo.getId());

        GameProdlineBasicInfo prodlineBasicInfo = prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo();
        // 该生产线对产品生产周期的影响情况
        prodlineDetailVo.setExtraPeriod(prodlineBasicInfo.getProdlineBasicInfo().getExtraPeriod());
        // 转产所需费用
        prodlineDetailVo.setProdlineChangeCost(prodlineBasicInfo.getProdlineBasicInfo().getProdlineChangeCost());
        // 转产周期
        prodlineDetailVo.setProdlineChangePeriod(prodlineBasicInfo.getProdlineBasicInfo().getProdlineChangePeriod());
        // 生产线投入使用后，每期折旧的价值。完工当期不折旧
        prodlineDetailVo.setProdlineDepreciation(prodlineBasicInfo.getProdlineBasicInfo().getProdlineDepreciation());
        // 生产线每期的维修费用
        prodlineDetailVo.setProdlineMainCost(prodlineBasicInfo.getProdlineBasicInfo().getProdlineMainCost());
        // 生产线安装周期
        prodlineDetailVo.setProdlineSetupPeriod(prodlineBasicInfo.getProdlineBasicInfo().getProdlineSetupPeriod());
        // 生产线安装费用
        prodlineDetailVo.setProdlineSetupPeriodPrice(prodlineBasicInfo.getProdlineBasicInfo().getProdlineSetupPeriodPrice());
        // 生产线的残值。即折旧到一定阶段后，无论怎么再折旧，都不再减少的价值
        prodlineDetailVo.setProdlineStumpcost(prodlineBasicInfo.getProdlineBasicInfo().getProdlineStumpcost());
        // 不考虑残值时，生产线售卖价值
        prodlineDetailVo.setProdlineValue(prodlineBasicInfo.getProdlineBasicInfo().getProdlineValue());
        // 生产线类型
        prodlineDetailVo.setProdlineType(prodlineBasicInfo.getProdlineBasicInfo().getProdlineType());
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
        prodlineDevelopDisplayVo.setProdlineSetupPeriod(prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineSetupPeriod());
        // 生产线类型
        prodlineDevelopDisplayVo.setProdlineType(prodlineDevelopInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineType());
        // 该生产线上生产的产品名称
        prodlineDevelopDisplayVo.setProductName(prodlineDevelopInfo.getProductDevelopInfo().getProductBasicInfo().getProductName());

        return prodlineDevelopDisplayVo;
    }


    public static ProdlineDevelopVo copyFieldsFromEntityToVo(GameProdlineBasicInfo prodlineBasicInfo) {

        ProdlineDevelopVo prodlineDevelopVo = new ProdlineDevelopVo();

        prodlineDevelopVo.setId(prodlineBasicInfo.getId());
        prodlineDevelopVo.setProdlineDepreciation(prodlineBasicInfo.getProdlineBasicInfo().getProdlineDepreciation());
        prodlineDevelopVo.setExtraPeriod(prodlineBasicInfo.getProdlineBasicInfo().getExtraPeriod());
        prodlineDevelopVo.setExtraValue(prodlineBasicInfo.getProdlineBasicInfo().getExtraValue());
        prodlineDevelopVo.setProdlineMainCost(prodlineBasicInfo.getProdlineBasicInfo().getProdlineMainCost());
        prodlineDevelopVo.setProdlineSetupPeriod(prodlineBasicInfo.getProdlineBasicInfo().getProdlineSetupPeriod());
        prodlineDevelopVo.setProdlineSetupPeriodPrice(prodlineBasicInfo.getProdlineBasicInfo().getProdlineSetupPeriodPrice());
        prodlineDevelopVo.setProdlineStumpcost(prodlineBasicInfo.getProdlineBasicInfo().getProdlineStumpcost());
        prodlineDevelopVo.setProdlineType(prodlineBasicInfo.getProdlineBasicInfo().getProdlineType());

        return prodlineDevelopVo;
    }


    public static FactoryDevelopVo copyFieldsFromEntityToVo(GameFactoryBasicInfo factoryBasicInfo) {

        FactoryDevelopVo factoryDevelopVo = new FactoryDevelopVo();

        factoryDevelopVo.setId(factoryBasicInfo.getId());
        factoryDevelopVo.setFactoryCapacity(factoryBasicInfo.getFactoryBasicInfo().getFactoryCapacity());
        factoryDevelopVo.setFactoryDepreciation(factoryBasicInfo.getFactoryBasicInfo().getFactoryDepreciation());
        factoryDevelopVo.setFactoryMaintainCost(factoryBasicInfo.getFactoryBasicInfo().getFactoryMaintainCost());
        factoryDevelopVo.setFactoryMakeCost(factoryBasicInfo.getFactoryBasicInfo().getFactoryMakeCost());
        factoryDevelopVo.setFactoryMakePeriod(factoryBasicInfo.getFactoryBasicInfo().getFactoryMakePeriod());
        factoryDevelopVo.setFactoryType(factoryBasicInfo.getFactoryBasicInfo().getFactoryType());

        return factoryDevelopVo;
    }

    public static FactoryDisplayVo copyFieldsFromEntityToVo(FactoryHoldingInfo factoryHoldingInfo, List<ProdlineProduceDisplayVo> prodlineProduceDisplayVoList, List<ProdlineDevelopDisplayVo> prodlineDevelopDisplayVoList) {

        FactoryDisplayVo factoryDisplayVo = new FactoryDisplayVo();

        GameFactoryBasicInfo factoryBasicInfo = factoryHoldingInfo.getFactoryBasicInfo();

        factoryDisplayVo.setId(factoryHoldingInfo.getId());

        // 厂房最大容量
        factoryDisplayVo.setFactoryCapacity(factoryBasicInfo.getFactoryBasicInfo().getFactoryCapacity());
        // 厂房的当前容量
        factoryDisplayVo.setCurrentCapacity(prodlineDevelopDisplayVoList.size()+prodlineProduceDisplayVoList.size());
        // 厂房拥有状态（自建的：拥有中 / 租来的：租赁中）
        factoryDisplayVo.setFactoryHoldingStatus(factoryHoldingInfo.getFactoryHoldingStatus());
        // 厂房编号，取的是厂房id后三位，不足位补0
        factoryDisplayVo.setFactoryNumber(NumberFormatUtil.numberFormat(factoryHoldingInfo.getId(),3));
        // 厂房拥有状态（已修建or已出售 / 租赁中or租赁）
        factoryDisplayVo.setDevelopStatus(factoryHoldingInfo.isEnable());
        // 厂房类型
        factoryDisplayVo.setFactoryType(factoryBasicInfo.getFactoryBasicInfo().getFactoryType());
        // 生产中的生产线
        factoryDisplayVo.setProdlineProduceDisplayVoList(prodlineProduceDisplayVoList);
        // 修建中的生产线
        factoryDisplayVo.setProdlineDevelopDisplayVoList(prodlineDevelopDisplayVoList);

        return factoryDisplayVo;
    }


    public static FactoryDetailVo copyFieldsFromEntityToVo(FactoryHoldingInfo factoryHoldingInfo) {
        FactoryDetailVo factoryDetailVo = new FactoryDetailVo();

        GameFactoryBasicInfo factoryBasicInfo = factoryHoldingInfo.getFactoryBasicInfo();

        factoryDetailVo.setId(factoryHoldingInfo.getId());

        // 展示为厂房状态，实际为自建的厂房状态 / 租来的厂房状态（true为拥有中 / 租赁中，false为已出售 / 暂停租赁）
        factoryDetailVo.setDevelopStatus(factoryHoldingInfo.isEnable());
        // 厂房容量
        factoryDetailVo.setFactoryCapacity(factoryBasicInfo.getFactoryBasicInfo().getFactoryCapacity());
        // 厂房建造完成后每期的折旧费用
        factoryDetailVo.setFactoryDepreciation(factoryBasicInfo.getFactoryBasicInfo().getFactoryDepreciation());
        // 厂房的拥有状态
        factoryDetailVo.setFactoryHoldingStatus(factoryHoldingInfo.getFactoryHoldingStatus());
        // 厂房修建完成后每期的维护费用
        factoryDetailVo.setFactoryMaintainCost(factoryBasicInfo.getFactoryBasicInfo().getFactoryMaintainCost());
        // 修建该厂房每期所需要的费用
        factoryDetailVo.setFactoryMakeCost(factoryBasicInfo.getFactoryBasicInfo().getFactoryMakeCost());
        // 修建该厂房所需要的周期数
        factoryDetailVo.setFactoryMakePeriod(factoryBasicInfo.getFactoryBasicInfo().getFactoryMakePeriod());
        // 不考虑残值使厂房的售卖价格
        factoryDetailVo.setFactoryValue(factoryBasicInfo.getFactoryBasicInfo().getFactoryValue());
        // 残值
        factoryDetailVo.setFactoryStumpCost(factoryBasicInfo.getFactoryBasicInfo().getFactoryStumpCost());
        // 厂房编号
        factoryDetailVo.setFactoryNumber(NumberFormatUtil.numberFormat(factoryHoldingInfo.getId(),3));
        // 厂房类型
        factoryDetailVo.setFactoryType(factoryBasicInfo.getFactoryBasicInfo().getFactoryType());

        return factoryDetailVo;
    }


    public static FactoryDevelopDisplayVo copyFieldsFromEntityToVo(FactoryDevelopInfo factoryDevelopInfo, FactoryDevelopDisplayVo flag) {
        FactoryDevelopDisplayVo factoryDevelopDisplayVo = new FactoryDevelopDisplayVo();

        factoryDevelopDisplayVo.setId(factoryDevelopInfo.getId());

        // 厂房类型
        factoryDevelopDisplayVo.setFactoryType(factoryDevelopInfo.getFactoryBasicInfo().getFactoryBasicInfo().getFactoryType());
        // 厂房临时编号
        factoryDevelopDisplayVo.setFactoryNumber(NumberFormatUtil.numberFormat(factoryDevelopInfo.getId(),3));
        // 厂房容量
        factoryDevelopDisplayVo.setFactoryCapacity(factoryDevelopInfo.getFactoryBasicInfo().getFactoryBasicInfo().getFactoryCapacity());
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
        factoryDevelopDetailVo.setFactoryMakePeriod(factoryDevelopInfo.getFactoryBasicInfo().getFactoryBasicInfo().getFactoryMakePeriod());
        // 厂房的修建情况（true修建中 false暂停修建）
        factoryDevelopDetailVo.setDevelopStatus(factoryDevelopInfo.isEnable());
        // 厂房类型
        factoryDevelopDetailVo.setFactoryType(factoryDevelopInfo.getFactoryBasicInfo().getFactoryBasicInfo().getFactoryType());

        return factoryDevelopDetailVo;
    }


    /******************* 下面本来是给ProductionPlanServiceImpl自用的，但是由于某种不可抗力被挪到这里来了 ************************/

    public static List<ProdlineTypeVo> copyFieldsFromEntityToVo(List<ProdlineProduceInfo> prodlineProduceInfoList, ProdlineTypeVo flag) {

        List<ProdlineTypeVo> prodlineTypeVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            ProdlineTypeVo prodlineTypeVo = new ProdlineTypeVo();

            prodlineTypeVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineType());
            prodlineTypeVo.setId(prodlineProduceInfo.getId());

            prodlineTypeVoList.add(prodlineTypeVo);
        }

        return prodlineTypeVoList;
    }

    public static List<ProdlineProduceDisplayVo> copyFieldsFromEntityToVo(List<ProdlineProduceInfo> prodlineProduceInfoList, ProdlineProduceDisplayVo flag) {

        List<ProdlineProduceDisplayVo> prodlineProduceDisplayVoList = new ArrayList<>();
        for (ProdlineProduceInfo prodlineProduceInfo : prodlineProduceInfoList) {
            prodlineProduceDisplayVoList.add(copyFieldsFromEntityToVo(prodlineProduceInfo, new ProdlineProduceDisplayVo()));
        }
        return prodlineProduceDisplayVoList;
    }


    public static List<ProdlineDevelopDisplayVo> copyFieldsFromEntityToVo(List<ProdlineDevelopInfo> prodlineDevelopInfoList, ProdlineDevelopDisplayVo flag) {

        List<ProdlineDevelopDisplayVo> prodlineDevelopDisplayVoList = new ArrayList<>();
        for(ProdlineDevelopInfo prodlineDevelopInfo : prodlineDevelopInfoList) {
            prodlineDevelopDisplayVoList.add(copyFieldsFromEntityToVo(prodlineDevelopInfo));
        }

        return prodlineDevelopDisplayVoList;
    }


    public static ProductProduceVo copyFieldsFromEntityToVo(ProdlineProduceInfo prodlineProduceInfo) {

        ProductProduceVo productProduceVo = new ProductProduceVo();

        productProduceVo.setId(prodlineProduceInfo.getId());
        productProduceVo.setProdlineType(prodlineProduceInfo.getProdlineHoldingInfo().getProdlineBasicInfo().getProdlineBasicInfo().getProdlineType());
        productProduceVo.setFactoryNumber(NumberFormatUtil.numberFormat(prodlineProduceInfo.getProdlineHoldingInfo().getFactoryHoldingInfo().getId(),3));

        return productProduceVo;
    }

    /******************************* 以上 喵 ***********************************/


    public static MaterialOrderDisplayVo copyFieldsFromEntityToVo(MaterialOrderInfo materialOrderInfo) {
        MaterialOrderDisplayVo materialOrderDisplayVo = new MaterialOrderDisplayVo();

        materialOrderDisplayVo.setId(materialOrderInfo.getId());

        // 订单编号
        materialOrderDisplayVo.setOrderNumber(NumberFormatUtil.numberFormat(materialOrderInfo.getId(),5));
        // 原材料名称
        materialOrderDisplayVo.setMaterialName(materialOrderInfo.getMaterialBasicInfo().getMaterialName());
        // 原材料单价
        materialOrderDisplayVo.setMaterialPrice(materialOrderInfo.getMaterialBasicInfo().getMaterialPrice());
        // 原材料购买数量
        materialOrderDisplayVo.setPurchaseNumber(materialOrderInfo.getPurchaseNumber());
        // 采购时间
        materialOrderDisplayVo.setPurchaseTime(materialOrderInfo.getPurchaseTime());
        // 运输方式
        materialOrderDisplayVo.setTransportMethod(EntityVoUtil.copyFieldsFromEntityToVo(materialOrderInfo.getTransportMethod()));
        // 开始运货的时间
        materialOrderDisplayVo.setTransportTime(materialOrderInfo.getTransportTime());
        // 原材料运送状态
        materialOrderDisplayVo.setTransportStatus(materialOrderInfo.getTransportStatus());

        return materialOrderDisplayVo;
    }

    public static MaterialStockDisplayVo copyFieldsFromEntityToVo(MaterialStockInfo materialStockInfo) {
        MaterialStockDisplayVo materialStockDisplayVo = new MaterialStockDisplayVo();

        materialStockDisplayVo.setId(materialStockInfo.getId());

        // 原材料基本信息id
        materialStockDisplayVo.setMaterialBasicId(materialStockInfo.getMaterialBasicInfo().getId());
        // 原材料名称
        materialStockDisplayVo.setMaterialName(materialStockInfo.getMaterialBasicInfo().getMaterialName());
        // 该材料库存数量
        materialStockDisplayVo.setMaterialNumber(materialStockInfo.getMaterialNumber());
        // 原材料单价（保留两位小数）
        Double sellPrice = materialStockInfo.getMaterialBasicInfo().getMaterialPrice()*materialStockInfo.getMaterialBasicInfo().getSellRate();
        materialStockDisplayVo.setMaterialPrice(new BigDecimal(sellPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        return materialStockDisplayVo;
    }

    public static ProductStockDisplayVo copyFieldsFromEntityToVo(ProductStockInfo productStockInfo) {
        ProductStockDisplayVo productStockDisplayVo = new ProductStockDisplayVo();

        productStockDisplayVo.setId(productStockInfo.getId());

        // 原材料基本信息id
        productStockDisplayVo.setProductBasicId(productStockInfo.getProductBasicInfo().getId());
        // 原材料名称
        productStockDisplayVo.setProductName(productStockInfo.getProductBasicInfo().getProductName());
        // 该材料库存数量
        productStockDisplayVo.setProductNumber(productStockInfo.getProductNumber());

        return productStockDisplayVo;
    }

    public static TransportMethodDisplayVo copyFieldsFromEntityToVo(GameTransportBasicInfo transportBasicInfo) {
        TransportMethodDisplayVo transportMethodDisplayVo = new TransportMethodDisplayVo();

        transportMethodDisplayVo.setId(transportBasicInfo.getId());

        // 运输方式
        transportMethodDisplayVo.setTransportName(transportBasicInfo.getTransportBasicInfo().getTransportName());
        // 该运输方式所需要的周期数
        transportMethodDisplayVo.setTransportPeriod(transportBasicInfo.getTransportBasicInfo().getTransportPeriod());
        // 每周期运费
        transportMethodDisplayVo.setTransportPrice(transportBasicInfo.getTransportBasicInfo().getTransportPrice());

        return transportMethodDisplayVo;
    }

    public static GameOrderVo copyFieldsFromEntityToVo(GameOrderInfo gameOrderInfo, GameOrderVo flag) {
        GameOrderVo gameOrderVo = new GameOrderVo();
        BeanCopyUtil.copyPropertiesSimple(gameOrderInfo, gameOrderVo);

        gameOrderVo.setGameBasicInfoId(gameOrderInfo.getGameBasicInfo().getId());

        gameOrderVo.setMarketBasicType(new MarketBasicTypeVo(gameOrderInfo.getMarketBasicInfo().getId(), gameOrderInfo.getMarketBasicInfo().getMarketName()));
        gameOrderVo.setProductBasicType(new ProductBasicTypeVo(gameOrderInfo.getProductBasicInfo().getId(), gameOrderInfo.getProductBasicInfo().getProductName()));
        if(gameOrderInfo.getIsoBasicInfo() != null) {
            gameOrderVo.setIsoBasicType(new IsoBasicTypeVo(gameOrderInfo.getIsoBasicInfo().getId(), gameOrderInfo.getIsoBasicInfo().getIsoName()));
        }

        gameOrderVo.setOrderId(gameOrderInfo.getId());

        return gameOrderVo;
    }

    public static LoanEnterpriseDisplayVo copyFieldsFromEntityToVo(LoanEnterpriseInfo loanEnterpriseInfo) {

        LoanEnterpriseDisplayVo loanEnterpriseDisplayVo = new LoanEnterpriseDisplayVo();

        loanEnterpriseDisplayVo.setBeginPeriod(loanEnterpriseInfo.getBeginPeriod());
        loanEnterpriseDisplayVo.setEndPeriod(loanEnterpriseInfo.getEndPeriod());
        loanEnterpriseDisplayVo.setLoanAmount(loanEnterpriseInfo.getLoanAmount());
        loanEnterpriseDisplayVo.setLoanId(loanEnterpriseInfo.getId());
        loanEnterpriseDisplayVo.setLoanNumber(NumberFormatUtil.numberFormat(loanEnterpriseInfo.getId(), 4));
        loanEnterpriseDisplayVo.setRepaid(loanEnterpriseInfo.isRepaid());

        return loanEnterpriseDisplayVo;
    }

}
