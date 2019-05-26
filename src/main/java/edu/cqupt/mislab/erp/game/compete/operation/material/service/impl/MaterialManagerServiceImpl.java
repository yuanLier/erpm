package edu.cqupt.mislab.erp.game.compete.operation.material.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.dto.MaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.entity.MaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.vo.MaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.material.service.MaterialManagerService;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-24 20:16
 * @description 注 ：更新材料时要记得同步更新由该材料组成的产品相关信息
 */
@Service
public class MaterialManagerServiceImpl implements MaterialManagerService {

    @Autowired
    private MaterialBasicInfoRepository materialBasicInfoRepository;
    @Autowired
    private ProductMaterialBasicInfoRepository productMaterialBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialBasicVo addMaterialBasicInfo(MaterialBasicDto materialBasicDto) {

        // 将接受到的dto中的数据复制给materialBasicInfo
        MaterialBasicInfo materialBasicInfo = new MaterialBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(materialBasicDto,materialBasicInfo);

        // 启用该条设置
        materialBasicInfo.setEnable(true);

        // 保存修改并刷新
        materialBasicInfo = materialBasicInfoRepository.saveAndFlush(materialBasicInfo);

        // 将获取了新id的info数据复制给materialBasicVo
        MaterialBasicVo materialBasicVo = new MaterialBasicVo();
        BeanCopyUtil.copyPropertiesSimple(materialBasicInfo, materialBasicVo);

        // 返回vo
        return materialBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialBasicVo updateMaterialBasicInfo(Long materialBasicId, MaterialBasicDto materialBasicDto) {

        // 获取之前的材料信息并设置为不启用
        MaterialBasicInfo materialBasicInfo = materialBasicInfoRepository.findOne(materialBasicId);
        materialBasicInfo.setEnable(false);

        materialBasicInfoRepository.save(materialBasicInfo);

        // 重新生成一条数据
        MaterialBasicInfo newMaterialBasicInfo = new MaterialBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(materialBasicDto,newMaterialBasicInfo);
        // 设置可用
        newMaterialBasicInfo.setEnable(true);

        newMaterialBasicInfo = materialBasicInfoRepository.saveAndFlush(newMaterialBasicInfo);

        // 获取全部使用了该材料的产品
        List<ProductMaterialBasicInfo> productMaterialBasicInfoList = productMaterialBasicInfoRepository.findByMaterialBasicInfo_IdAndEnableIsTrue(materialBasicInfo.getId());
        for (ProductMaterialBasicInfo productMaterialBasicInfo : productMaterialBasicInfoList) {

            // 更新构成该产品的材料信息
            productMaterialBasicInfo.setMaterialBasicInfo(newMaterialBasicInfo);

            productMaterialBasicInfoRepository.save(productMaterialBasicInfo);
        }

        MaterialBasicVo materialBasicVo = new MaterialBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newMaterialBasicInfo, materialBasicVo);

        return materialBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialBasicVo closeMaterialBasicInfo(Long materialBasicId) {

        // 获取全部使用了该材料的产品
        List<ProductMaterialBasicInfo> productMaterialBasicInfoList = productMaterialBasicInfoRepository.findByMaterialBasicInfo_IdAndEnableIsTrue(materialBasicId);
        // 确认该产品集是否为空
        if(productMaterialBasicInfoList.size() != 0) {
            return null;
        }

        // 只有满足了“所有使用了该材料的产品信息已被关闭”这一条件，才允许关闭材料信息

        // 获取这个材料信息
        MaterialBasicInfo materialBasicInfo = materialBasicInfoRepository.findOne(materialBasicId);

        // 设置为不启用
        materialBasicInfo.setEnable(false);

        // 保存修改
        materialBasicInfoRepository.save(materialBasicInfo);

        MaterialBasicVo materialBasicVo = new MaterialBasicVo();
        BeanCopyUtil.copyPropertiesSimple(materialBasicInfo, materialBasicVo);

        return materialBasicVo;
    }

    @Override
    public List<MaterialBasicVo> getAllMaterialBasicVoOfStatus(boolean enable) {

        List<MaterialBasicInfo> materialBasicInfoList = materialBasicInfoRepository.findByEnable(enable);

        List<MaterialBasicVo> materialBasicVoList = new ArrayList<>();
        for (MaterialBasicInfo materialBasicInfo : materialBasicInfoList) {
            MaterialBasicVo materialBasicVo = new MaterialBasicVo();
            BeanCopyUtil.copyPropertiesSimple(materialBasicInfo, materialBasicVo);

            materialBasicVoList.add(materialBasicVo);
        }

        return materialBasicVoList;
    }
}
