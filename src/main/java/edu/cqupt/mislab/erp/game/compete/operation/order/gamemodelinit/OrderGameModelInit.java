package edu.cqupt.mislab.erp.game.compete.operation.order.gamemodelinit;

import edu.cqupt.mislab.erp.game.compete.basic.GameModelInit;
import edu.cqupt.mislab.erp.game.compete.basic.impl.GameModelInitService;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.gamemodelinit.IsoGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.dao.MarketDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.market.gamemodelinit.MarketGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.market.model.entity.MarketDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.GameOrderInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.dao.OrderPredictionInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.GameOrderInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.OrderPredictionInfo;
import edu.cqupt.mislab.erp.game.compete.operation.order.model.entity.OrderPredictionInfo.OrderPredictionInfoBuilder;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.gamemodelinit.ProductGameModelInit;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.dao.GameBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.GameBasicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author： chuyunfei date：2019/3/1
 */
@Slf4j
@Component
public class OrderGameModelInit implements GameModelInit {

    @Autowired private GameBasicInfoRepository gameBasicInfoRepository;
    @Autowired private GameOrderInfoRepository gameOrderInfoRepository;
    @Autowired private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;
    @Autowired private IsoDevelopInfoRepository isoDevelopInfoRepository;
    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;
    @Autowired private MarketDevelopInfoRepository marketDevelopInfoRepository;
    @Autowired private OrderPredictionInfoRepository orderPredictionInfoRepository;

    @Autowired private GameModelInitService gameModelInitService;

    @Autowired private IsoGameModelInit isoGameModelInit;
    @Autowired private MarketGameModelInit marketGameModelInit;
    @Autowired private ProductGameModelInit productGameModelInit;

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/1 20:11
     * @Description: 初始化订单模块的信息，在初始化之前需要保证必须保证初始化的状态是正确的
     **/
    @Override
    public List<String> initGameModel(Long gameId){

        //判断当前模块是否已经被初始化过了
        if(gameModelInitService.addInitializedModelIfNotExist(gameId,this)){

            //必须要保证ISO、Market、Product、Material全部已经被初始化了
            List<String> preInitResult = orderPreInit(gameId);

            if(preInitResult != null){

                return preInitResult;
            }

            try{

                log.info("开始初始化订单模块的比赛数据");

                //初始化所有订单的预测信息
                initPrediction(gameId);

                //初始化比赛所有的订单
                initAllOrder(gameId);

                //初始化所有的ISO附加信息
                initIsoOfOrder(gameId);

                return null;
            }catch(Exception e){
                e.printStackTrace();
            }

            return Collections.singletonList("初始化第订单模块出错，无法初始化比赛");
        }

        return null;
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/3 16:50
     * @Description: 为所有的订单附加ISO条件
     **/
    private void initIsoOfOrder(Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //获取比赛的基本数据
        final Integer period = gameBasicInfo.getGameInitBasicInfo().getPeriodOfOneYear();

        //获取所有的ISO认证信息信息
        final Long enterpriseId = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId).get(0).getId();

        final List<IsoBasicInfo> isoBasicInfos = isoDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId)
                .stream()
                .map(IsoDevelopInfo::getIsoBasicInfo)
                .collect(Collectors.toList());

        //获取所有的订单
        final List<GameOrderInfo> gameOrderInfos = gameOrderInfoRepository.findByGameBasicInfo_Id(gameId);

        //获取全部的年数，total
        final Integer totalYear = gameBasicInfo.getGameInitBasicInfo().getTotalYear();

        //为订单附加ISO认证信息的要求
        /*
            ISO附加概率算法：
            y = ax + b ; x=1 => y=10;x=totalYear => y=90;

            计算斜率的格式为 80/(totalYear-1)
         */

        //计算斜率
        double slope = 80.0/(totalYear-1);
        //计算截距
        double intercept = 10 - slope;

        //随机对象
        Random random = new Random();

        gameOrderInfos.forEach(gameOrderInfo -> {

            //计算需要附加ISO认证信息的概率
            double probability = (slope * gameOrderInfo.getYear() + intercept) / 100;

            //判断当前订单是否需要附加ISO认证信息
            if(random.nextDouble() < probability){

                //获取当前是哪一年的订单
                final Integer gameOrderInfoYear = gameOrderInfo.getYear();

                //附加ISO认证信息
                //获取所有有可能被附加的ISO认证信息
                final List<IsoBasicInfo> possibleIsoInfos = isoBasicInfos.stream()
                        .filter(isoBasicInfo -> {

                            final Integer isoResearchPeriod = isoBasicInfo.getIsoResearchPeriod();

                            return isoResearchPeriod < gameOrderInfoYear * period;
                        })
                        .collect(Collectors.toList());

                if(possibleIsoInfos.size() != 0){

                    //如果左右一条数据
                    if(possibleIsoInfos.size() == 1){

                        gameOrderInfo.setIsoBasicInfo(possibleIsoInfos.get(0));
                    }else {

                        final int size = possibleIsoInfos.size();

                        //设置ISO认证信息
                        gameOrderInfo.setIsoBasicInfo(possibleIsoInfos.get(((int) Math.ceil(random.nextDouble() * size)) - 1));
                    }
                    //更新数据
                    gameOrderInfoRepository.save(gameOrderInfo);
                }
            }
        });
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/3 16:50
     * @Description: 初始化所有的订单信息
     **/
    private void initAllOrder(Long gameId){

        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        //获取全部的预测信息
        final List<OrderPredictionInfo> orderPredictionInfos = orderPredictionInfoRepository.findByGameBasicInfo_Id(gameId);

        //用于存储生成的订单
        List<GameOrderInfo> gameOrderInfos = new ArrayList<>();

        //开始生产订单
        final int size = orderPredictionInfos.size();

        //随机对象
        Random random = new Random();

        //获取比赛的初始化信息
        final Integer gameCurrentYear = gameBasicInfo.getGameCurrentYear();
        final Integer period = gameBasicInfo.getGameInitBasicInfo().getPeriodOfOneYear();

        for(int i = 0; i < size; i++){

            //获取一条预测信息
            final OrderPredictionInfo orderPredictionInfo = orderPredictionInfos.get(i);

            //获取预测的基本信息
            final Integer totalMount = orderPredictionInfo.getMount();//这个数值必须是大于0的，不然会出错
            final Double basicPrice = orderPredictionInfo.getPrice();

            //获取总共能够生成的最大订单数量
            int maxOrderNumber = (int) Math.ceil(enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId).size() * 1.5);

            //该条预测信息已经生成了多少个产品
            int exitedOrderNumber = 0;

            //开始生成订单
            for(int j = 0; j < maxOrderNumber; j ++){

                //计算平均每个订单的产品个数
                int averageProductNumber = (int) Math.ceil(totalMount * 1.0D / maxOrderNumber);

                //随机生成该订单的产品个数
                int orderProductNumber = (int) Math.ceil(averageProductNumber + random.nextInt(3));

                //随机生成每个订单的单价
                double orderProductPerPrice = Math.ceil(basicPrice + Math.random() * 3);

                //随机生成交货日期
                int deliveryPeriod = (int) Math.ceil(gameCurrentYear * period + random.nextInt(period) + 2);

                //随机生成到账日期
                int moneyTime = (int) Math.ceil(random.nextInt(period) + 2);

                //随机生成罚金率
                double penalPercent = -1;

                do{
                    penalPercent = Math.random();
                }while(penalPercent <= 0.01D || penalPercent >= 1D);

                //构建订单信息
                GameOrderInfo gameOrderInfo = GameOrderInfo.builder()
                        .gameBasicInfo(orderPredictionInfo.getGameBasicInfo())
                        .marketBasicInfo(orderPredictionInfo.getMarketBasicInfo())
                        .productBasicInfo(orderPredictionInfo.getProductBasicInfo())
                        .year(orderPredictionInfo.getYear())
                        .productNumber(orderProductNumber)
                        .price(orderProductPerPrice)
                        .deliveryPeriod(deliveryPeriod)
                        .moneyTime(moneyTime)
                        .penalPercent(penalPercent)
                        .build();

                //添加到订单集合里面去
                gameOrderInfos.add(gameOrderInfo);

                exitedOrderNumber += orderProductNumber;

                //如果该条预测信息已经产生了多余或等于预测信息的产品个数将进行下一个预测信息的处设立
                if(exitedOrderNumber >= totalMount){

                    break;
                }
            }

        }

        //存储订单信息
        gameOrderInfoRepository.save(gameOrderInfos);
    }

    /*
     * @Author: chuyunfei
     * @Date: 2019/3/3 16:49
     * @Description: 为比赛创建产品销量的预测信息
     **/
    private void initPrediction(Long gameId){

        //获取这场比赛多少年
        final GameBasicInfo gameBasicInfo = gameBasicInfoRepository.findOne(gameId);

        final Integer totalYear = gameBasicInfo.getGameInitBasicInfo().getTotalYear();

        //获取所有的市场信息
        final Long enterpriseId = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId).get(0).getId();

        List<MarketBasicInfo> marketBasicInfos = marketDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId)
                .stream()
                .map(MarketDevelopInfo::getMarketBasicInfo)
                .collect(Collectors.toList());

        //获取所有的产品信息
        List<ProductBasicInfo> productBasicInfos = productDevelopInfoRepository.findByEnterpriseBasicInfo_Id(enterpriseId)
                .stream()
                .map(ProductDevelopInfo::getProductBasicInfo)
                .collect(Collectors.toList());

        //用于存储所有的数据信息
        List<OrderPredictionInfo> orderPredictionInfos = new ArrayList<>();

        //开始生产预测信息

        //随机对象
        Random r = new Random();

        for(int i = 0; i < totalYear; i++){
            for(MarketBasicInfo marketBasicInfo : marketBasicInfos){
                for(ProductBasicInfo productBasicInfo : productBasicInfos){

                    final OrderPredictionInfoBuilder builder = OrderPredictionInfo.builder()
                            .gameBasicInfo(gameBasicInfo)
                            .marketBasicInfo(marketBasicInfo)
                            .year(i+1)
                            .productBasicInfo(productBasicInfo);

                    //开始根据信息随机生成数量和价格

                    //随机生成价格和数量，优化原版本的算法
                    double price = -1;

                    /*
                    当产品的价格为0时将会出现死循环，需要控制产品价格
                     */
                    do{
                        price = productBasicInfo.getPrice() //原价
                                + productBasicInfo.getPrice() * productBasicInfo.getPriceDifference() * Math.pow(-1,Math.ceil(Math.random() * 2)) //价格差异
                                + productBasicInfo.getPrice() * productBasicInfo.getPriceFloat() * Math.pow(-1,Math.ceil(Math.random() * 2)); //价格波动
                    }while(price <= 0);

                    double mount = -1;

                    /*
                    当产品的个数为0时将会出现死循环，需要控制产品价格
                     */
                    do{
                        mount = productBasicInfo.getMount() //原个数
                                + productBasicInfo.getMount() * productBasicInfo.getMountDifference() * Math.pow(-1,Math.ceil(Math.random() * 2))//数量差异
                                + productBasicInfo.getMount() * productBasicInfo.getMount() * Math.pow(-1,Math.ceil(Math.random() * 2));//数量波动
                    }while(mount <= 0);

                    //对生成的数值进行向上取整处理
                    mount = Math.ceil(mount);

                    //转换为所需要的数据
                    int realMount = (int) mount;

                    //设置基本数据信息
                    builder.mount(realMount);
                    builder.price(price);

                    //添加到全局里面去
                    orderPredictionInfos.add(builder.build());
                }
            }
        }

        //存在预测信息
        orderPredictionInfoRepository.save(orderPredictionInfos);
    }

    /**
     * @Author: chuyunfei
     * @Date: 2019/3/1 20:59
     * @Description: 确保前面的几个个模块都已经被初始化后才行
     **/
    private List<String> orderPreInit(Long gameId){

        //初始化ISO模块
        List<String> strings = isoGameModelInit.initGameModel(gameId);

        if(strings != null){

            return strings;
        }

        //初始化市场模块
        strings = marketGameModelInit.initGameModel(gameId);

        if(strings != null){

            return strings;
        }

        //初始化产品模块
        strings = productGameModelInit.initGameModel(gameId);

        if(strings != null){

            return strings;
        }

        return null;
    }
}