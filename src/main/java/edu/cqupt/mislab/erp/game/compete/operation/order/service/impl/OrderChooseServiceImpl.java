package edu.cqupt.mislab.erp.game.compete.operation.order.service.impl;

import edu.cqupt.mislab.erp.commons.websocket.CommonWebSocketMessagePublisher;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketStatusEnum;
import edu.cqupt.mislab.erp.game.compete.operation.order.constant.OrderConstant;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.EnterpriseAdInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderChooseInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.EnterpriseAdInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderChooseInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.vo.OrderChooseDisplayVo;
import edu.cqupt.mislab.erp.game.compete.operation.order.service.OrderChooseService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatusEnum;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderChooseServiceImpl implements OrderChooseService {

    @Autowired private ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void postConstruct(){

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/15 11:17
         * @Description: 开启轮询线程任务
         **/

        Runnable runnableTask = () -> {

            int DEFAULT_SLEEP_TIME = 60;

            while(true){

               synchronized(this){

                   final Set<Long> keySet = orderChooseHelperMap.keySet();

                   if(keySet.size() == 0){

                       try{
                           this.wait();//无限等待
                       }catch(InterruptedException e){
                           e.printStackTrace();
                       }
                   }else {

                       //下一次订单睡眠的时间
                       long nextWaiteTime = Long.MAX_VALUE;

                       final Iterator<Long> iterator = keySet.iterator();

                       while(iterator.hasNext()){

                           final Long next = iterator.next();

                           final OrderChooseHelper orderChooseHelper = orderChooseHelperMap.get(next);

                           if(orderChooseHelper != null){

                               if(orderChooseHelper.finish){

                                   iterator.remove();
                               }else {

                                   final long distance = System.currentTimeMillis() - orderChooseHelper.lastChangeTime;

                                   if(distance > OrderConstant.CHOOSE_INTERVALS_TIME){

                                       //通知前端后端顺序改变
                                       messagePublisher.publish(next,new TextMessage(OrderConstant.ORDER_CHOOSE_CHANGE_KEY_NAME + next));

                                       //刷新订单顺序数据
                                       orderChooseHelper.refresh(true);
                                   }else {

                                       //生成睡眠时间
                                       if(distance < nextWaiteTime){

                                           nextWaiteTime = distance;
                                       }
                                   }
                               }
                           }
                       }

                       try{
                           //定时沉睡
                           this.wait(nextWaiteTime);
                       }catch(InterruptedException e){
                           e.printStackTrace();
                       }
                   }
               }

            }
        };

        //提交运行任务
        threadPoolExecutor.execute(runnableTask);
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/9 15:31
     * @Description: 1、用于选单计时功能；2、订单排序功能；
     **/

    @Autowired private CommonWebSocketMessagePublisher messagePublisher;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private EnterpriseAdInfoRepository enterpriseAdInfoRepository;
    @Autowired private GameOrderChooseInfoRepository gameOrderChooseInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired private MarketDevelopInfoRepository marketDevelopInfoRepository;

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

            synchronized(this){

                //初始化信息
                orderChooseHelperMap.put(gameId,new OrderChooseHelper(gameId));

                //唤醒监督线程
                this.notifyAll();

                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void loadOrderChooseInfoFromDatabase(){

        //选取所有的非完结的比赛
        List<GameBasicInfo> gameBasicInfos = gameBasicInfoRepository.findByGameStatus(GameStatusEnum.PLAYING);

        if(gameBasicInfos != null && gameBasicInfos.size() > 0){

            gameBasicInfos.stream()
                     .map(gameBasicInfo -> {

                         final Integer gameCurrentYear = gameBasicInfo.getGameCurrentYear();

                         return gameOrderChooseInfoRepository.findByGameBasicInfo_IdAndYear(gameBasicInfo.getId(),gameCurrentYear);
                     })
                     .filter(GameOrderChooseInfo::getFinished)
                     .map(GameOrderChooseInfo::getGameBasicInfo)
                    .forEach(gameBasicInfo -> {

                        orderChooseHelperMap.put(gameBasicInfo.getId(),new OrderChooseHelper(gameBasicInfo.getId()));
                    });
        }
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

        //该比赛已经选择已经多少轮
        private Integer frequency;

        //用于记录是否结束
        private boolean finish;

        //用于记录时间
        private Long lastChangeTime;

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/10 21:11
         * @Description: 获取当前比赛的信息
         **/
        public OrderChooseDisplayVo getConcurrentOrderInfoVo(){

            OrderChooseDisplayVo displayVo = new OrderChooseDisplayVo();

            //设置当前是哪一个订单在进行选择
            displayVo.setConcurrentEnterpriseAdId(this.concurrentEnterpriseAdInfo.getId());

            //设置全部订单ID集合
            displayVo.setEnterpriseAdIds(this.enterpriseAdInfos.stream().map(EnterpriseAdInfo::getId).collect(Collectors.toList()));

            //设置当前选单市场
            displayVo.setConcurrentMarketName(this.concurrentMarketBasicInfo.getMarketName());

            //设置全部的市场集合
            displayVo.setMarketNames(this.marketBasicInfos.stream().map(MarketBasicInfo::getMarketName).collect(Collectors.toList()));

            //设置当前选单的产品
            displayVo.setConcurrentProductName(this.concurrentProductBasicInfo.getProductName());

            //设置全部的产品集合
            displayVo.setProductNames(this.productBasicInfos.stream().map(ProductBasicInfo::getProductName).collect(Collectors.toList()));

            //设置剩余时间，单位为秒
            displayVo.setRemainTime((int) Math.floor((System.currentTimeMillis() - this.lastChangeTime) * 1.0/1000));

            return displayVo;
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/10 13:39
         * @Description: 构造函数
         **/
        OrderChooseHelper(long gameId){

            //初始化对象
            initOrderChooseHelper(gameId);
            //计时开始
            this.lastChangeTime = System.currentTimeMillis();
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/10 12:54
         * @Description: 初始化一个OrderChooseHelper
         **/
        private void initOrderChooseHelper(long gameId){

            final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

            final Integer currentYear = gameBasicInfo.getGameCurrentYear();

            this.gameOrderChooseInfo = new GameOrderChooseInfo();

            //设置比赛信息
            this.gameOrderChooseInfo.setGameBasicInfo(gameBasicInfo);
            //设置当前年数据
            this.gameOrderChooseInfo.setYear(currentYear);
            //设置当前轮为第一轮
            this.gameOrderChooseInfo.setFrequency(1);

            this.frequency = 1;

            //从头开始初始化订单选择信息
            //查取全部的产品信息
            this.productBasicInfos = getGameProductOfOneYear(gameId);

            if(this.productBasicInfos.size() == 0){

                this.finish = true;

                //设置是否结束
                this.gameOrderChooseInfo.setFinished(true);

                //持久化数据
                this.gameOrderChooseInfo = gameOrderChooseInfoRepository.save(this.gameOrderChooseInfo);

                return;
            }

            //设置产品进度
            this.concurrentProductBasicInfo = this.productBasicInfos.get(0);

            //查取全部市场信息
            this.marketBasicInfos = getGameMarketOfOneYear(gameId);

            if(this.marketBasicInfos.size() == 0){

                this.finish = true;

                //设置是否结束
                this.gameOrderChooseInfo.setFinished(true);

                //持久化数据
                this.gameOrderChooseInfo = gameOrderChooseInfoRepository.save(this.gameOrderChooseInfo);

                return;
            }
            //设置市场进度
            this.concurrentMarketBasicInfo = this.marketBasicInfos.get(0);

            //查询需要继续进行排序的订单
            this.enterpriseAdInfos = enterpriseAdInfoRepository
                    .findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearAndProductBasicInfo_IdAndMarketBasicInfo_IdAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(
                            gameId,currentYear,this.concurrentProductBasicInfo.getId(),this.concurrentMarketBasicInfo.getId()
                    );

            //没有可用的订单
            while(this.enterpriseAdInfos == null || this.enterpriseAdInfos.size() == 0){

                //获取当前产品的下标地址
                int concurrentProductIndex = this.productBasicInfos.indexOf(this.concurrentProductBasicInfo);

                //产品循环
                if(concurrentProductIndex == this.productBasicInfos.size() - 1){

                    //获取当前产品的数组下标
                    int concurrentMarketIndex = this.marketBasicInfos.indexOf(this.concurrentMarketBasicInfo);

                    //没有指定的广告信息
                    if(concurrentMarketIndex == this.marketBasicInfos.size() - 1){

                        break;
                    }

                    //更新当前市场信息
                    this.concurrentMarketBasicInfo = this.marketBasicInfos.get(concurrentMarketIndex + 1);

                    //更新当前产品信息
                    this.concurrentProductBasicInfo = this.productBasicInfos.get(0);
                }else {

                    this.concurrentProductBasicInfo = this.productBasicInfos.get(concurrentProductIndex + 1);
                }

                //查询需要继续进行排序的订单
                this.enterpriseAdInfos = enterpriseAdInfoRepository
                        .findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearAndProductBasicInfo_IdAndMarketBasicInfo_IdAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(
                                gameId,currentYear,this.concurrentProductBasicInfo.getId(),this.concurrentMarketBasicInfo.getId()
                        );

            }

            //没有指定的信息
            if(this.enterpriseAdInfos == null||this.enterpriseAdInfos.size() == 0){

                this.finish = true;
            }else{

                //有可用订单
                //恢复订单选择进度
                this.concurrentEnterpriseAdInfo = this.enterpriseAdInfos.get(0);

                //设置需要持久化的信息
                this.gameOrderChooseInfo.setConcurrentEnterprise(this.concurrentEnterpriseAdInfo);

                this.finish = false;
            }

            //设置当前比赛年周期
            this.concurrentYear = currentYear;

            //设置是否结束
            this.gameOrderChooseInfo.setFinished(this.finish);

            //持久化数据
            this.gameOrderChooseInfo = gameOrderChooseInfoRepository.save(this.gameOrderChooseInfo);
        }

        /*
         * @Author: chuyunfei
         * @Date: 2019/3/13 18:48
         * @Description: 刷新数据
         **/
        public void refresh(boolean force){

            //非强制性刷新数据
            if(!force){

                //判断时间是否过期
                long currentTimeMillis = System.currentTimeMillis();

                //如果未到自动的更新时间就不进行强制刷新
                if(currentTimeMillis < OrderConstant.CHOOSE_INTERVALS_TIME + this.lastChangeTime){
                    return;
                }
            }

            //更新订单信息
            int concurrentAdInfoIndex = this.enterpriseAdInfos.indexOf(this.concurrentEnterpriseAdInfo);

            if(concurrentAdInfoIndex == this.enterpriseAdInfos.size() - 1){

                this.concurrentEnterpriseAdInfo = null;

                do{
                    //获取当前产品的下标地址
                    int concurrentProductIndex = this.productBasicInfos.indexOf(this.concurrentProductBasicInfo);

                    //产品循环
                    if(concurrentProductIndex == this.productBasicInfos.size() - 1){

                        //获取当前产品的数组下标
                        int concurrentMarketIndex = this.marketBasicInfos.indexOf(this.concurrentMarketBasicInfo);

                        //没有指定的广告信息
                        if(concurrentMarketIndex == this.marketBasicInfos.size() - 1){

                            this.finish = true;

                            this.gameOrderChooseInfo.setFinished(true);

                            gameOrderChooseInfoRepository.save(this.gameOrderChooseInfo);

                            return;
                        }else {

                            //更新市场信息
                            this.concurrentMarketBasicInfo = this.marketBasicInfos.get(concurrentMarketIndex + 1);

                            //更新产品信息
                            this.concurrentProductBasicInfo = this.productBasicInfos.get(0);
                        }
                    }else {

                        this.concurrentProductBasicInfo = this.productBasicInfos.get(concurrentProductIndex + 1);
                    }

                    //查询需要继续进行排序的订单
                    this.enterpriseAdInfos = enterpriseAdInfoRepository
                            .findByEnterpriseBasicInfo_GameBasicInfo_IdAndYearAndProductBasicInfo_IdAndMarketBasicInfo_IdAndFinishedIsFalseOrderByMoneyDescTimeStampAsc(
                                    this.gameOrderChooseInfo.getGameBasicInfo().getId()
                                    ,this.getConcurrentYear(),this.concurrentProductBasicInfo.getId(),this.concurrentMarketBasicInfo.getId()
                            );

                    if(this.enterpriseAdInfos != null && this.enterpriseAdInfos.size() != 0){

                        this.concurrentEnterpriseAdInfo = this.enterpriseAdInfos.get(0);
                    }

                }while(this.concurrentEnterpriseAdInfo == null);

            }else {

                this.concurrentEnterpriseAdInfo = this.enterpriseAdInfos.get(concurrentAdInfoIndex + 1);
            }

            this.gameOrderChooseInfo.setConcurrentEnterprise(this.concurrentEnterpriseAdInfo);

            gameOrderChooseInfoRepository.save(this.gameOrderChooseInfo);
        }
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/9 16:40
     * @Description: 获取在某比赛当年的所有的企业的全部以开发产品集合信息
     **/
    private List<ProductBasicInfo> getGameProductOfOneYear(long gameId){

        //选取所有的企业
        final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        //用于记录所有非重复的基本产品信息，需要重写hashCode和equal函数
        Set<ProductBasicInfo> productBasicInfos = new HashSet<>();

        enterpriseBasicInfos.forEach(

                enterpriseBasicInfo -> {
                    //选取企业的指定状态的数据
                    productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId()).forEach(productDevelopInfo -> {

                        //需要研发成功的数据信息
                        if(productDevelopInfo.getProductDevelopStatus() == ProductDevelopStatusEnum.DEVELOPED){
                            productBasicInfos.add(productDevelopInfo.getProductBasicInfo());
                        }
                    });
                });

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
        final List<EnterpriseBasicInfo> enterpriseBasicInfos = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);

        //用于存储所有的非重复的市场信息
        Set<MarketBasicInfo> marketBasicInfos = new HashSet<>();

        enterpriseBasicInfos.forEach(

                enterpriseBasicInfo -> {

                    //获取指定状态的市场研发信息
                    marketDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseBasicInfo.getId()).forEach(marketDevelopInfo -> {
                        //筛选全部已经开拓的市场信息
                        if(marketDevelopInfo.getMarketStatus() == MarketStatusEnum.DEVELOPED){
                            marketBasicInfos.add(marketDevelopInfo.getMarketBasicInfo());
                        }
                    });
                });

        final ArrayList<MarketBasicInfo> result = new ArrayList<>(marketBasicInfos);

        //按照市场的开拓周期进行排序
        result.sort(Comparator.comparing(MarketBasicInfo::getMarketResearchCost));

        return result;
    }
}