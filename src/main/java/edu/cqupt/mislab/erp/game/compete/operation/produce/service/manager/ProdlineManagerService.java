package edu.cqupt.mislab.erp.game.compete.operation.produce.service.manager;

import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.dto.ProdlineBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.produce.model.prodline.vo.ProdlineBasicVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-06-05 22:20
 * @description
 */
public interface ProdlineManagerService {

    /**
     * 增加一个生产线基本信息
     * @param prodlineBasicDto
     * @return
     */
    ProdlineBasicVo addProdlineBasicInfo(ProdlineBasicDto prodlineBasicDto);


    /**
     * 修改生产线基本信息
     * @param prodlineBasicId
     * @param prodlineBasicDto
     * @return
     */
    ProdlineBasicVo updateProdlineBasicInfo(Long prodlineBasicId, ProdlineBasicDto prodlineBasicDto);


    /**
     * 关闭一个生产线基本信息
     * @param prodlineBasicId
     * @return
     */
    ProdlineBasicVo closeProdlineBasicInfo(Long prodlineBasicId);


    /**
     * 获取处于某一状态（可用or不可用）的生产线基本信息
     * @param enable 是否可用
     * @return
     */
    List<ProdlineBasicVo> getAllProdlineBasicVoOfStatus(boolean enable);

}
