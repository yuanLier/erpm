package edu.cqupt.mislab.erp.game.compete.operation.product.service.impl;

import edu.cqupt.mislab.erp.commons.util.EntityVoUtil;
import edu.cqupt.mislab.erp.game.compete.operation.product.dao.ProductDevelopInfoRepository;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopInfo;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.entity.ProductDevelopStatus;
import edu.cqupt.mislab.erp.game.compete.operation.product.model.vo.ProductEnterpriseBasicVo;
import edu.cqupt.mislab.erp.game.compete.operation.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductDevelopInfoRepository productDevelopInfoRepository;

    @Override
    public List<ProductEnterpriseBasicVo> getProductDevelopInfoOfStatus(Long enterpriseId,ProductDevelopStatus status){

        //选取指定状态的产品研发信息
        final List<ProductDevelopInfo> productDevelopInfos = productDevelopInfoRepository.findByEnterpriseBasicInfo_IdAndProductDevelopStatus(enterpriseId,status);

        //进行转换
        if(productDevelopInfos != null && productDevelopInfos.size() > 0){

            List<ProductEnterpriseBasicVo> basicVos = new ArrayList<>();

            for(ProductDevelopInfo productDevelopInfo : productDevelopInfos){

                ProductEnterpriseBasicVo basicVo = new ProductEnterpriseBasicVo();

                //进行VO转换
                EntityVoUtil.copyFieldsFromEntityToVo(productDevelopInfo,basicVo);

                basicVos.add(basicVo);
            }

            return basicVos;
        }
        return null;
    }
}
