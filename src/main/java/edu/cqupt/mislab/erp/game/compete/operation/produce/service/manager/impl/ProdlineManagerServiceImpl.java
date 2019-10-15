package edu.cqupt.mislab.erp.game.compete.operation.produce.service.manager.impl;

import edu.cqupt.mislab.erp.commons.aspect.BadModificationException;
import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.produce.dao.prodline.ProdlineBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.dto.ProdlineBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.entity.ProdlineBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.produce.service.manager.ProdlineManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 22:27
 * @description
 */
@Service
public class ProdlineManagerServiceImpl implements ProdlineManagerService {

    @Autowired
    private ProdlineBasicInfoRepository prodlineBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProdlineBasicVo addProdlineBasicInfo(ProdlineBasicDto prodlineBasicDto) {

        // 将接受到的dto中的数据复制给prodlineBasicInfo
        ProdlineBasicInfo prodlineBasicInfo = new ProdlineBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(prodlineBasicDto,prodlineBasicInfo);

        // 启用该条设置
        prodlineBasicInfo.setEnable(true);

        // 保存修改并刷新
        prodlineBasicInfo = prodlineBasicInfoRepository.saveAndFlush(prodlineBasicInfo);

        // 将获取了新id的info数据复制给prodlineBasicVo
        ProdlineBasicVo prodlineBasicVo = new ProdlineBasicVo();
        BeanCopyUtil.copyPropertiesSimple(prodlineBasicInfo, prodlineBasicVo);

        // 返回vo
        return prodlineBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProdlineBasicVo updateProdlineBasicInfo(Long prodlineBasicId, ProdlineBasicDto prodlineBasicDto) {

        // 获取之前的生产线信息并设置为不启用
        ProdlineBasicInfo prodlineBasicInfo = prodlineBasicInfoRepository.findOne(prodlineBasicId);
        if(!prodlineBasicInfo.isEnable()) {
            throw new BadModificationException();
        }
        prodlineBasicInfo.setEnable(false);

        prodlineBasicInfoRepository.save(prodlineBasicInfo);

        // 重新生成一条数据
        ProdlineBasicInfo newProdlineBasicInfo = new ProdlineBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(prodlineBasicDto,newProdlineBasicInfo);
        // 设置可用
        newProdlineBasicInfo.setEnable(true);

        newProdlineBasicInfo = prodlineBasicInfoRepository.saveAndFlush(newProdlineBasicInfo);

        ProdlineBasicVo prodlineBasicVo = new ProdlineBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newProdlineBasicInfo, prodlineBasicVo);

        return prodlineBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProdlineBasicVo closeProdlineBasicInfo(Long prodlineBasicId) {

        // 获取这个生产线
        ProdlineBasicInfo prodlineBasicInfo = prodlineBasicInfoRepository.findOne(prodlineBasicId);

        // 设置为不启用
        prodlineBasicInfo.setEnable(false);

        // 保存修改
        prodlineBasicInfoRepository.save(prodlineBasicInfo);

        ProdlineBasicVo prodlineBasicVo = new ProdlineBasicVo();
        BeanCopyUtil.copyPropertiesSimple(prodlineBasicInfo, prodlineBasicVo);

        return prodlineBasicVo;
    }

    @Override
    public List<ProdlineBasicVo> getAllProdlineBasicVoOfStatus(boolean enable) {

        List<ProdlineBasicInfo> prodlineBasicInfoList = prodlineBasicInfoRepository.findByEnable(enable);

        List<ProdlineBasicVo> prodlineBasicVoList = new ArrayList<>();
        for (ProdlineBasicInfo prodlineBasicInfo : prodlineBasicInfoList) {
            ProdlineBasicVo prodlineBasicVo = new ProdlineBasicVo();
            BeanCopyUtil.copyPropertiesSimple(prodlineBasicInfo, prodlineBasicVo);

            prodlineBasicVoList.add(prodlineBasicVo);
        }

        return prodlineBasicVoList;
    }
}
