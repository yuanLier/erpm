package edu.cqupt.mislab.erp.game.manage.service;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo;
import edu.cqupt.mislab.erp.game.manage.model.dto.EnterpriseCreateDto;
import edu.cqupt.mislab.erp.game.manage.model.vo.EnterpriseDetailInfoVo;

import java.util.List;

/**
 * @author chuyunfei
 * @description 企业相关操作
 * @date 21:29 2019/4/26
 **/

public interface EnterpriseManageService {

    /**
     * @author chuyunfei
     * @description 创建一个新的企业
     * @date 21:29 2019/4/26
     **/
    WebResponseVo<EnterpriseDetailInfoVo> createNewEnterprise(EnterpriseCreateDto createDto);

    /**
     * @author chuyunfei
     * @description 删除一个企业，级联删除企业成员
     * @date 21:30 2019/4/26
     **/
    WebResponseVo<String> deleteOneEnterprise(Long enterpriseId,Long userId,String password);

    /**
     * @author chuyunfei
     * @description 当前企业比赛准备开始
     * @date 21:29 2019/4/26
     **/
    WebResponseVo<String> sureOneEnterprise(Long enterpriseId,Long userId);

    /**
     * @author chuyunfei
     * @description 获取一个企业的详细信息
     * @date 21:29 2019/4/26
     **/
    EnterpriseDetailInfoVo getOneEnterpriseInfo(Long enterpriseId);

    /**
     * @author chuyunfei
     * @description 获取一个比赛的全部信息
     * @date 21:29 2019/4/26
     **/
    List<EnterpriseDetailInfoVo> getEnterpriseInfos(Long gameId);
}
