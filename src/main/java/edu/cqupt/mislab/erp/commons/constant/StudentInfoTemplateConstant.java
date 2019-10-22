package edu.cqupt.mislab.erp.commons.constant;

/**
 * @author yuanyiwen
 * @create 2019-10-21 22:09
 * @description 学生信息导入时用到的常量值
 */
public interface StudentInfoTemplateConstant {

    /**
     * 模板外部链接尾部，即文件名转义字符
     */
    String TEMPLATE_NAME = "erp-%E5%AD%A6%E7%94%9F%E4%BF%A1%E6%81%AF%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xlsx";

    /**
     * 学生初始密码
     */
    String INIT_PASSWORD = "123456";

    /**
     * 默认头像存储位置外部链接尾部
     */
    String AVATAR_LOCATION = "002.jpg";

    /**
     * 默认为第几个头像；根据对头像的不同处理方式选择这种方式or上面那种方式
     */
    Long AVATAR_INDEX = 1L;
}
