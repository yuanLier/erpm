package edu.cqupt.mislab.erp.game.compete.operation.product.service.impl;

import edu.cqupt.mislab.erp.commons.util.BeanCopyUtil;
import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.material.dao.MaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductMaterialBasicInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.dto.ProductMaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductMaterialBasicInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductMaterialBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-16 18:22
 * @description
 */

@Service
public class ProductManagerServiceImpl implements ProductManagerService {

    @Autowired
    private ProductBasicInfoRepository productBasicInfoRepository;
    @Autowired
    private MaterialBasicInfoRepository materialBasicInfoRepository;
    @Autowired
    private ProductMaterialBasicInfoRepository productMaterialBasicInfoRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductBasicVo addProductBasicInfo(ProductBasicDto productBasicDto) {

        // 将接受到的dto中的数据复制给productBasicInfo
        ProductBasicInfo productBasicInfo = new ProductBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(productBasicDto, productBasicInfo);

        // 启用该条设置
        productBasicInfo.setEnable(true);

        // 保存修改并刷新
        productBasicInfo = productBasicInfoRepository.saveAndFlush(productBasicInfo);

        // 将获取了新id的info数据复制给productBasicVo
        ProductBasicVo productBasicVo = new ProductBasicVo();
        BeanCopyUtil.copyPropertiesSimple(productBasicInfo, productBasicVo);

        // 返回vo
        return productBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductBasicVo updateProductBasicInfo(Long productBasicId, ProductBasicDto productBasicDto) {

        // 获取之前的产品信息并设置为不启用
        ProductBasicInfo productBasicInfo = productBasicInfoRepository.findOne(productBasicId);
        productBasicInfo.setEnable(false);

        productBasicInfoRepository.save(productBasicInfo);

        // 重新生成一条数据
        ProductBasicInfo newProductBasicInfo = new ProductBasicInfo();
        BeanCopyUtil.copyPropertiesSimple(productBasicDto, newProductBasicInfo);
        // 设置可用
        newProductBasicInfo.setEnable(true);

        newProductBasicInfo = productBasicInfoRepository.saveAndFlush(newProductBasicInfo);

        ProductBasicVo productBasicVo = new ProductBasicVo();
        BeanCopyUtil.copyPropertiesSimple(newProductBasicInfo, productBasicVo);

        return productBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductBasicVo closeProductBasicInfo(Long productBasicId) {

        // 获取这个产品信息
        ProductBasicInfo productBasicInfo = productBasicInfoRepository.findOne(productBasicId);

        // 设置为不启用
        productBasicInfo.setEnable(false);

        // 保存修改
        productBasicInfoRepository.save(productBasicInfo);

        ProductBasicVo productBasicVo = new ProductBasicVo();
        BeanCopyUtil.copyPropertiesSimple(productBasicInfo, productBasicVo);

        return productBasicVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductMaterialBasicVo addProductMaterialBasicInfo(ProductMaterialBasicDto productMaterialBasicDto) {
        // 构建该条产品原料信息
        ProductMaterialBasicInfo productMaterialBasicInfo = new ProductMaterialBasicInfo();
        productMaterialBasicInfo.setMaterialBasicInfo(materialBasicInfoRepository.findOne(productMaterialBasicDto.getMaterialBasicInfoId()));
        productMaterialBasicInfo.setProductBasicInfo(productBasicInfoRepository.findOne(productMaterialBasicDto.getProductBasicInfoId()));
        productMaterialBasicInfo.setNumber(productMaterialBasicDto.getNumber());

        // 启用该条设置
        productMaterialBasicInfo.setEnable(true);

        // 保存修改并刷新
        productMaterialBasicInfo = productMaterialBasicInfoRepository.saveAndFlush(productMaterialBasicInfo);

        // 返回vo
        return EntityVoUtil.copyFieldsFromEntityToVo(productMaterialBasicInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductMaterialBasicVo updateProductMaterialBasicInfo(Long productMaterialId, Integer number) {
        // 获取之前的产品原材料信息并设置为不启用
        ProductMaterialBasicInfo productMaterialBasicInfo = productMaterialBasicInfoRepository.findOne(productMaterialId);
        productMaterialBasicInfo.setEnable(false);

        productMaterialBasicInfoRepository.save(productMaterialBasicInfo);

        // 重新生成一条数据
        ProductMaterialBasicInfo newProductMaterialBasicInfo = new ProductMaterialBasicInfo();
        // 复制产品和原料信息
        newProductMaterialBasicInfo.setProductBasicInfo(productMaterialBasicInfo.getProductBasicInfo());
        newProductMaterialBasicInfo.setMaterialBasicInfo(productMaterialBasicInfo.getMaterialBasicInfo());
        // 修改原料数量
        newProductMaterialBasicInfo.setNumber(number);
        // 设置可用
        newProductMaterialBasicInfo.setEnable(true);

        newProductMaterialBasicInfo = productMaterialBasicInfoRepository.saveAndFlush(newProductMaterialBasicInfo);

        return EntityVoUtil.copyFieldsFromEntityToVo(productMaterialBasicInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductMaterialBasicVo closeProductMaterialBasicInfo(Long productMaterialId) {
        // 获取这个产品原材料信息
        ProductMaterialBasicInfo productMaterialBasicInfo = productMaterialBasicInfoRepository.findOne(productMaterialId);

        // 设置为不启用
        productMaterialBasicInfo.setEnable(false);

        // 保存修改
        productMaterialBasicInfoRepository.save(productMaterialBasicInfo);

        return EntityVoUtil.copyFieldsFromEntityToVo(productMaterialBasicInfo);
    }

    @Override
    public List<ProductBasicVo> getAllProductBasicVoOfStatus(boolean enable) {

        List<ProductBasicInfo> productBasicInfoList = productBasicInfoRepository.findByEnable(enable);

        List<ProductBasicVo> productBasicVoList = new ArrayList<>();
        for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
            ProductBasicVo productBasicVo = new ProductBasicVo();
            BeanCopyUtil.copyPropertiesSimple(productBasicInfo, productBasicVo);

            productBasicVoList.add(productBasicVo);
        }

        return productBasicVoList;
    }

    @Override
    public List<ProductMaterialBasicVo> getAllProductMaterialBasicVoOfStatus(boolean enable) {

        List<ProductMaterialBasicInfo> productMaterialBasicInfoList = productMaterialBasicInfoRepository.findByEnable(enable);

        List<ProductMaterialBasicVo> productMaterialBasicVoList = new ArrayList<>();
        for (ProductMaterialBasicInfo productMaterialBasicInfo : productMaterialBasicInfoList) {
            productMaterialBasicVoList.add(EntityVoUtil.copyFieldsFromEntityToVo(productMaterialBasicInfo));
        }

        return productMaterialBasicVoList;
    }
}
