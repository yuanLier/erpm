package edu.cqupt.mislab.erp.game.compete.operation.order.service.impl;

import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.EnterpriseAdInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderChooseInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderChooseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.OrderChooseDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class OrderChooseServiceImpl implements OrderChooseService {

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/9 15:31
     * @Description: 1、用于选单计时功能；2、订单排序功能；
     **/

    @Autowired
    private CommonWebSocketMessagePublisher messagePublisher;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired
    private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired
    private EnterpriseAdInfoRepository enterpriseAdInfoRepository;
    @Autowired
    private GameOrderChooseInfoRepository gameOrderChooseInfoRepository;
    @Autowired
    private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired
    private MarketDevelopInfoRepository marketDevelopInfoRepository;

    //用于记录每一个比赛的信息
    private Map<Long,OrderChooseHelper> orderChooseHelperMap = new ConcurrentHashMap<>();

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/10 12:35
     * @Description: 移除某个比赛的比赛订单选择信息
     **/
    public void removeGameOrderChooseData(long gameId){

        //移除内存里面的订单排序信息
        orderChooseHelperMap.remove(gameId);
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/9 16:06
     * @Description: 初始化一个比赛的订单选择信息，即初始化比赛选择的基本辅助信息
     **/
    public boolean initGameOrderChooseData(long gameId){
        
        try{

            //初始化信息
            orderChooseHelperMap.put(gameId,new OrderChooseHelper(gameId));

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/10 13:36
     * @Description: 用于辅助订单选择的信息封装对象，注意在声明这个对象的时候需要保证这个比赛还没有被初始化过
     **/
    @Getter
    @Setter
    public class OrderChooseHelper {

        //用于持久化数据
        private GameOrderChooseInfo gameOrderChooseInfo;
        //用于记录所有的已研发的按照研发周期进行排序的所有产品信息
        private List<ProductBasicInfo> productBasicInfos;
        //当前正在进行订单选择的产品
        private ProductBasicInfo concurrentProductBasicInfo;
        //用于记录所有的已开拓的按照开拓周期进行排序的所有市场信息
        private List<MarketBasicInfo> marketBasicInfos;
        //当亲正在进行订单选择的市场信息
        private MarketBasicInfo concurrentMarketBasicInfo;
        //用于记录订单的顺序
        private List<EnterpriseAdInfo> enterpriseAdInfos;
        //当前是哪一个订单正在进行订单的选择
        private EnterpriseAdInfo concurrentEnterpriseAdInfo;
        //用于记录那一年
        private Integer concurrentYear;
        //用于记录是否结束
        private boolean finish;
        //用于记录时间
        private Long lastChangeTime;
        
        /* 
         * @Author: chuyunfei
         * @Date: 2019/3/10 21:11
         * @Description: 获取当前比赛的信息
         **/
        public OrderChooseDisplayVo getConcurrentOrderInfo(){

            //todo


            
            return null;
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/10 13:39
         * @Description: 构造函数
         **/
        OrderChooseHelper(long gameId){

            //初始化对象
            init(gameId);
            //计时开始
            this.lastChangeTime = System.currentTimeMillis();
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/10 12:54
         * @Description: 初始化一个OrderChooseHelper，如果原来存在就重新加载，如果不存在就创建
         **/
        private void init(long gameId){

            final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

            final Integer currentYear = gameBasicInfo.getGameCurrentYear();

            //检查是否曾经程序中断过
            final GameOrderChooseInfo gameOrderChooseInfo = gameOrderChooseInfoRepository.findByGameBasicInfo_IdAndYear(gameId,currentYear);

            if(gameOrderChooseInfo != null){
                //程序中断过或者重复初始化

                if(gameOrderChooseInfo.getFinished()){
                    //如果这个比赛的订单选择流程已经结束了就直接跳过
                    this.finish = true;

                    return;
                }

                //从数据库里面恢复现场数据
                this.gameOrderChooseInfo = gameOrderChooseInfo;

                //查取全部的产品信息
                this.productBasicInfos = getGameProductOfOneYear(gameId);
                //恢复产品进度
                this.concurrentProductBasicInfo = gameOrderChooseInfo.getConcurrentEnterprise().getProductBasicInfo();

                //查取全部市场信息
                this.marketBasicInfos = getGameMarketOfOneYear(gameId);
                //恢复市场进度
                this.concurrentMarketBasicInfo = gameOrderChooseInfo.getConcurrentEnterprise().getMarketBasicInfo();

                //查询需要继续进行排序的订单
                this.enterpriseAdInfos = enterpriseAdInfoRepository.findByEnterpriseBasicInfo_GameInfo_IdAndYearAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(gameId,currentYear);
                //恢复订单选择进度
                this.concurrentEnterpriseAdInfo = gameOrderChooseInfo.getConcurrentEnterprise();

                //设置当前比赛信息未结束
                this.concurrentYear = currentYear;
                this.finish = false;

            }else {

                this.gameOrderChooseInfo = new GameOrderChooseInfo();

                //设置比赛信息
                this.gameOrderChooseInfo.setGameBasicInfo(gameBasicInfo);
                //设置当前年数据
                this.gameOrderChooseInfo.setYear(currentYear);
                //设置当前轮为第一轮
                this.gameOrderChooseInfo.setFrequency(1);

                //从头开始初始化订单选择信息
                //查取全部的产品信息
                this.productBasicInfos = getGameProductOfOneYear(gameId);
                //设置产品进度
                this.concurrentProductBasicInfo = this.productBasicInfos.get(0);

                //查取全部市场信息
                this.marketBasicInfos = getGameMarketOfOneYear(gameId);
                //设置市场进度
                this.concurrentMarketBasicInfo = this.marketBasicInfos.get(0);

                //查询需要继续进行排序的订单
                this.enterpriseAdInfos = enterpriseAdInfoRepository.findByEnterpriseBasicInfo_GameInfo_IdAndYearAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(gameId,currentYear);

                //没有可用的订单
                if(this.enterpriseAdInfos == null || this.enterpriseAdInfos.size() == 0){

                    this.finish = true;
                }else {

                    //有可用订单
                    //恢复订单选择进度
                    this.concurrentEnterpriseAdInfo = this.enterpriseAdInfos.get(0);

                    //设置需要持久化的信息
                    this.gameOrderChooseInfo.setConcurrentEnterprise(this.concurrentEnterpriseAdInfo);

                    this.finish = false;
                }

                //设置当前比赛年周期
                this.concurrentYear = currentYear;

                try{

                    //持久化数据
                    this.gameOrderChooseInfo = gameOrderChooseInfoRepository.save(this.gameOrderChooseInfo);
                }catch(Exception e){
                    e.printStackTrace();

                    this.finish = true;
                }
            }
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/9 16:40
         * @Description: 获取在某比赛当年的所有的企业的全部以开发产品集合信息
         **/
        private List<ProductBasicInfo> getGameProductOfOneYear(long gameId){

            //选取所有的企业
            final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

            //用于记录所有非重复的基本产品信息，需要重写hashCode和equal函数
            Set<ProductBasicInfo> productBasicInfos = new HashSet<>();

            enterpriseBasicInfos.forEach(

                    enterpriseBasicInfo -> {
                        //选取企业的指定状态的数据
                        productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId())
                                .forEach(productDevelopInfo -> {

                                    //需要研发成功的数据信息
                                    if(productDevelopInfo.getProductDevelopStatus() == ProductDevelopStatus.DEVELOPED){
                                        productBasicInfos.add(productDevelopInfo.getProductBasicInfo());
                                    }
                                });
                    }
            );

            final ArrayList<ProductBasicInfo> result = new ArrayList<>(productBasicInfos);

            //按照研发周期数进行排序
            result.sort(Comparator.comparing(ProductBasicInfo::getProductResearchPeriod));

            return result;
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/9 20:59
         * @Description: 获取在某比赛当年的所有的企业的全部以开发市场集合信息
         **/
        private List<MarketBasicInfo> getGameMarketOfOneYear(long gameId){

            //选取所有的企业信息
            final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameInfo_Id(gameId);

            //用于存储所有的非重复的市场信息
            Set<MarketBasicInfo> marketBasicInfos = new HashSet<>();

            enterpriseBasicInfos.forEach(

                    enterpriseBasicInfo -> {

                        //获取指定状态的市场研发信息
                        marketDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId())
                                .forEach(marketDevelopInfo -> {
                                    //筛选全部已经开拓的市场信息
                                    if(marketDevelopInfo.getMarketStatus() == MarketStatusEnum.DEVELOPED){
                                        marketBasicInfos.add(marketDevelopInfo.getMarketBasicInfo());
                                    }
                                });
                    }
            );

            final ArrayList<MarketBasicInfo> result = new ArrayList<>(marketBasicInfos);

            //按照市场的开拓周期进行排序
            result.sort(Comparator.comparing(MarketBasicInfo::getMarketResearchCost));

            return result;
        }

        /* 
         * @Author: chuyunfei
         * @Date: 2019/3/10 13:33
         * @Description: 从数据库里面重新加载数据
         **/
        public void refresh(){

            //todo
        }
    }
}
