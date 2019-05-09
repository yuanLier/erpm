package edu.cqupt.mislab.erp.game.compete.operation.iso.service;

import edu.cqupt.mislab.erp.game.compete.operation.iso.model.dto.IsoBasicDto;
import edu.cqupt.mislab.erp.game.compete.operation.iso.model.vo.IsoBasicVo;

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

}
