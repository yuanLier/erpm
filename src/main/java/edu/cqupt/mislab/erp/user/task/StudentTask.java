package edu.cqupt.mislab.erp.user.task;

import edu.cqupt.mislab.erp.commons.response.ResponseVo;
import edu.cqupt.mislab.erp.user.model.dto.UserStudentInfoRegisterDto;
import edu.cqupt.mislab.erp.user.model.entity.MajorInfo;

import java.util.List;
import java.util.Map;

public interface StudentTask {

    ResponseVo<String> userStudentRegister(UserStudentInfoRegisterDto registerDto);
}
