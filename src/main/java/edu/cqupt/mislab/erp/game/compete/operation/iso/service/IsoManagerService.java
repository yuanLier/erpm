package edu.cqupt.mislab.erp.game.compete.operation.iso.service;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;

import java.util.List;

/**
 * @author yuanyiwen
 * @create 2019-05-08 21:40
 * @description iso管理端
 */
public interface IsoManagerService {


    /**
     * 增加一个iso
     * @param isoBasicDto
     * @return
     */
    IsoBasicVo addIsoBasicInfo(IsoBasicDto isoBasicDto);


    /**
     * 修改iso基本信息
     * @param isoBasicId
     * @param isoBasicDto
     * @return
     */
    IsoBasicVo updateIsoBasicInfo(Long isoBasicId, IsoBasicDto isoBasicDto);


    /**
     * 关闭一个iso
     * @param isoBasicId
     * @return
     */
    IsoBasicVo closeIsoBasicInfo(Long isoBasicId);


    /**
     * 获取处于某一状态（可用or不可用）的Iso基本信息
     * @param enable 是否可用
     * @return
     */
    List<IsoBasicVo> getAllIsoBasicVoOfStatus(boolean enable);

}
