package edu.cqupt.mislab.erp.game.compete.operation.material.service;

import edu.cqupt.mislab.erp.game.compete.operation.material.model.dto.MaterialBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.material.model.vo.MaterialBasicVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-24 19:02
 * @description
 */
public interface MaterialManagerService {

    /**
     * 增加一个材料基本信息
     * @param materialBasicDto
     * @return
     */
    MaterialBasicVo addMaterialBasicInfo(MaterialBasicDto materialBasicDto);


    /**
     * 修改材料基本信息
     * @param materialBasicId
     * @param materialBasicDto
     * @return
     */
    MaterialBasicVo updateMaterialBasicInfo(Long materialBasicId, MaterialBasicDto materialBasicDto);


    /**
     * 关闭一个材料基本信息
     * @param materialBasicId
     * @return
     */
    MaterialBasicVo closeMaterialBasicInfo(Long materialBasicId);


    /**
     * 获取处于某一状态（可用or不可用）的材料基本信息
     * @param enable 是否可用
     * @return
     */
    List<MaterialBasicVo> getAllMaterialBasicVoOfStatus(boolean enable);

}
