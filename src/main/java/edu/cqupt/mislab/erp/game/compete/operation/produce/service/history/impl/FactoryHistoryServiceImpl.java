package edu.cqupt.mislab.erp.game.compete.operation.produce.service.history.impl;

import edu.cqupt.mislab.erp.commons.constant.ProduceOperationConstant;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.factory.FactoryHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.entity.FactoryHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.factory.vo.FactoryOperationVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.history.FactoryHistoryService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanyiwen
 * @create 2019-06-02 20:48
 * @description
 */
@Service
public class FactoryHistoryServiceImpl implements FactoryHistoryService {

    @Autowired
    private FactoryHistoryRepository factoryHistoryRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public List<FactoryHistoryVo> findFactoryHistoryVoOfGameAndPeriod(Long gameId, Integer period) {

        List<FactoryHistoryVo> factoryHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            List<FactoryOperationVo> factoryOperationVoList = new ArrayList<>();

            FactoryHistoryVo factoryHistoryVo = new FactoryHistoryVo();
            factoryHistoryVo.setEnterpriseId(enterpriseBasicInfo.getId());
            factoryHistoryVo.setPeriod(period);
            // 计算截止该周期的厂房总数
            factoryHistoryVo.setSum(factoryHistoryRepository.sumAmount(enterpriseBasicInfo.getId(), period));

            FactoryOperationVo factoryOperationVo = new FactoryOperationVo();

            // 若该周期该企业已破产，标记为“已破产”；否则，依次判断四种操作标签
            if (period > enterpriseBasicInfo.getEnterpriseCurrentPeriod()) {
                // 操作标签为“已破产”，操作列表为空
                factoryOperationVo.setOperation(ProduceOperationConstant.ENTERPRISE_BANKRUPT);
                factoryOperationVo.setOperationAmount(new HashMap<>(0));

                factoryOperationVoList.add(factoryOperationVo);
            } else {
                // 分别获取该周期下该企业四种基本操作对应的全部厂房历史数据
                List<FactoryHistoryInfo> developList = factoryHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.FACTORY_DEVELOPED);
                factoryOperationVoList.add(record(ProduceOperationConstant.FACTORY_DEVELOPED, factoryOperationVo, developList));

                List<FactoryHistoryInfo> leaseList = factoryHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.FACTORY_LEASE);
                factoryOperationVoList.add(record(ProduceOperationConstant.FACTORY_LEASE, factoryOperationVo, leaseList));

                List<FactoryHistoryInfo> soldList = factoryHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.FACTORY_SOLD);
                factoryOperationVoList.add(record(ProduceOperationConstant.FACTORY_SOLD, factoryOperationVo, soldList));

                List<FactoryHistoryInfo> leasePauseList = factoryHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.FACTORY_LEASE_PAUSE);
                factoryOperationVoList.add(record(ProduceOperationConstant.FACTORY_LEASE_PAUSE, factoryOperationVo, leasePauseList));
            }

            // 添加到历史数据记录
            factoryHistoryVo.setFactoryOperationVoList(factoryOperationVoList);

            // 添加到列表
            factoryHistoryVoList.add(factoryHistoryVo);
        }

        return factoryHistoryVoList;
    }


    /**
     * 对operationVo转化步骤的简单封装
     * @param operation 哪种操作
     * @param factoryOperationVo
     * @param factoryHistoryInfoList
     * @return
     */
    private FactoryOperationVo record(String operation, FactoryOperationVo factoryOperationVo, List<FactoryHistoryInfo> factoryHistoryInfoList) {
        // 获取该企业在当前周期的全部的厂房历史数据并转化为vo实体集
        Map<FactoryBasicVo, Integer> map = new HashMap<>(16);

        factoryOperationVo.setOperation(operation);
        for (FactoryHistoryInfo factoryHistoryInfo : factoryHistoryInfoList) {

            FactoryBasicVo factoryBasicVo = new FactoryBasicVo();
            BeanCopyUtil.copyPropertiesSimple(factoryHistoryInfo.getFactoryBasicInfo(), factoryBasicVo);

            // 给同类厂房计数
            Integer amount = map.containsKey(factoryBasicVo) ? map.get(factoryBasicVo)+1 : 1;
            map.put(factoryBasicVo, amount);
        }
        factoryOperationVo.setOperationAmount(map);

        // 这里一定要加上这一步，返回一个新对象；否则每次调用操作的都是同一个对象，旧数据会被新数据覆盖掉
        FactoryOperationVo returnVo = new FactoryOperationVo();
        BeanCopyUtil.copyPropertiesSimple(factoryOperationVo, returnVo);

        return returnVo;
    }
}

