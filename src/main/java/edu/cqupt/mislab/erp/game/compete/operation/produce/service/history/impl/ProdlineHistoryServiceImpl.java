package edu.cqupt.mislab.erp.game.compete.operation.produce.service.history.impl;

import edu.cqupt.mislab.erp.commons.constant.ProduceOperationConstant;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineOperationVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.history.ProdlineHistoryService;
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
 * @create 2019-06-05 17:41
 * @description
 */
@Service
public class ProdlineHistoryServiceImpl implements ProdlineHistoryService {

    @Autowired
    private ProdlineHistoryRepository prodlineHistoryRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public List<ProdlineHistoryVo> findProdlineHistoryVoOfGameAndPeriod(Long gameId, Integer period) {
        List<ProdlineHistoryVo> prodlineHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            List<ProdlineOperationVo> prodlineOperationVoList = new ArrayList<>();

            ProdlineHistoryVo prodlineHistoryVo = new ProdlineHistoryVo();
            prodlineHistoryVo.setEnterpriseId(enterpriseBasicInfo.getId());
            prodlineHistoryVo.setPeriod(period);
            // 计算截止该周期的生产线总数
            prodlineHistoryVo.setSum(prodlineHistoryRepository.sumAmount(enterpriseBasicInfo.getId(), period));

            ProdlineOperationVo prodlineOperationVo = new ProdlineOperationVo();

            // 若该周期该企业经营已结束，标记为“结束经营”；否则，依次判断三种操作标签
            if (period > enterpriseBasicInfo.getEnterpriseCurrentPeriod()) {
                // 操作标签为“结束经营”，操作列表为空
                prodlineOperationVo.setOperation(ProduceOperationConstant.BUSINESS_END);
                prodlineOperationVo.setOperationAmount(new HashMap<>(0));

                prodlineOperationVoList.add(prodlineOperationVo);
            } else {
                // 分别获取该周期下该企业三种基本操作对应的全部生产线历史数据
                List<ProdlineHistoryInfo> developList = prodlineHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.PRODLINE_DEVELOPED);
                prodlineOperationVoList.add(record(ProduceOperationConstant.PRODLINE_DEVELOPED, prodlineOperationVo, developList));

                List<ProdlineHistoryInfo> soldList = prodlineHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.PRODLINE_SOLD);
                prodlineOperationVoList.add(record(ProduceOperationConstant.PRODLINE_SOLD, prodlineOperationVo, soldList));

                List<ProdlineHistoryInfo> notUsableList = prodlineHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriodAndOperation(enterpriseBasicInfo.getId(), period, ProduceOperationConstant.PRODLINE_NOT_USABLE);
                prodlineOperationVoList.add(record(ProduceOperationConstant.PRODLINE_NOT_USABLE, prodlineOperationVo, notUsableList));
            }

            // 添加到历史数据记录
            prodlineHistoryVo.setProdlineOperationVoList(prodlineOperationVoList);

            // 添加到列表
            prodlineHistoryVoList.add(prodlineHistoryVo);
        }

        return prodlineHistoryVoList;
    }


    /**
     * 对operationVo转化步骤的简单封装
     * @param operation 哪种操作
     * @param prodlineOperationVo
     * @param prodlineHistoryInfoList
     * @return
     */
    private ProdlineOperationVo record(String operation, ProdlineOperationVo prodlineOperationVo, List<ProdlineHistoryInfo> prodlineHistoryInfoList) {
        // 获取该企业在当前周期的全部的生产线历史数据并转化为vo实体集
        Map<ProdlineBasicVo, Integer> map = new HashMap<>(16);

        prodlineOperationVo.setOperation(operation);
        for (ProdlineHistoryInfo prodlineHistoryInfo : prodlineHistoryInfoList) {

            ProdlineBasicVo prodlineBasicVo = new ProdlineBasicVo();
            BeanCopyUtil.copyPropertiesSimple(prodlineHistoryInfo.getProdlineBasicInfo(), prodlineBasicVo);

            // 给同类生产线计数
            Integer amount = map.containsKey(prodlineBasicVo) ? map.get(prodlineBasicVo)+1 : 1;
            map.put(prodlineBasicVo, amount);
        }
        prodlineOperationVo.setOperationAmount(map);

        // 这里一定要加上这一步，返回一个新对象；否则每次调用操作的都是同一个对象，旧数据会被新数据覆盖掉
        ProdlineOperationVo returnVo = new ProdlineOperationVo();
        BeanCopyUtil.copyPropertiesSimple(prodlineOperationVo, returnVo);

        return returnVo;
    }


}
