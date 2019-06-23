package edu.cqupt.mislab.erp.game.compete.operation.iso.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.iso.dao.IsoHistoryRepository;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.entity.IsoHistoryInfo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoHistoryVo;
import edu.cqupt.mislab.erp.game.compete.operation.iso.service.IsoHistoryService;
import edu.cqupt.mislab.erp.game.manage.dao.EnterpriseBasicInfoRepository;
import edu.cqupt.mislab.erp.game.manage.model.entity.EnterpriseBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-12 18:16
 * @description
 */
@Service
public class IsoHistoryServiceImpl implements IsoHistoryService {


    @Autowired
    private IsoHistoryRepository isoHistoryRepository;
    @Autowired
    private EnterpriseBasicInfoRepository enterpriseBasicInfoRepository;

    @Override
    public List<IsoHistoryVo> findIsoHistoryVoOfGameAndPeriod(Long gameId, Integer period) {

        List<IsoHistoryVo> isoHistoryVoList = new ArrayList<>();

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            IsoHistoryVo isoHistoryVo = new IsoHistoryVo();
            isoHistoryVo.setEnterpriseId(enterpriseBasicInfo.getId());
            isoHistoryVo.setPeriod(period);

            // 若该周期该企业已结束经营，则返回结束前最后一周期的认证情况
            Integer bankruptPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();
            period = (period < bankruptPeriod) ? period : bankruptPeriod;

            // 获取全部iso认证信息
            List<IsoHistoryInfo> isoHistoryInfoList = isoHistoryRepository.findByEnterpriseBasicInfo_IdAndPeriod(enterpriseBasicInfo.getId(), period);
            // 获取该企业在当前周期的全部的iso历史数据并转化为vo实体集
            List<IsoBasicVo> isoBasicVoList = new ArrayList<>();
            for (IsoHistoryInfo isoHistoryInfo : isoHistoryInfoList) {

                IsoBasicVo isoBasicVo = new IsoBasicVo();
                BeanCopyUtil.copyPropertiesSimple(isoHistoryInfo.getIsoBasicInfo(), isoBasicVo);

                isoBasicVoList.add(isoBasicVo);
            }

            isoHistoryVo.setIsoBasicVoList(isoBasicVoList);

            isoHistoryVoList.add(isoHistoryVo);
        }

        return isoHistoryVoList;
    }

    @Override
    public Integer getTotalPeriod(Long gameId) {

        Integer totalPeriod = 0;

        // 获取当前比赛中的全部企业
        List<EnterpriseBasicInfo> enterpriseBasicInfoList = enterpriseBasicInfoRepository.findByGameBasicInfo_Id(gameId);
        for (EnterpriseBasicInfo enterpriseBasicInfo : enterpriseBasicInfoList) {

            Integer currentPeriod = enterpriseBasicInfo.getEnterpriseCurrentPeriod();

            // 其实就是获取企业的最大存活周期
            totalPeriod = (totalPeriod > currentPeriod) ? totalPeriod : currentPeriod;
        }

        return totalPeriod;
    }
}
