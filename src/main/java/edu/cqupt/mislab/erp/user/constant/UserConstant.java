package edu.cqupt.mislab.erp.user.constant;

public interface UserConstant {

    /*
    Session里面用来记录用户登录错误次数的属性名称
     */
    String USER_LOGIN_WARN_TIMES_SESSION_ATTR_NAME = "userLoginWarnTimesSessionAttrName";

    /*
    默认多少次登录失败后需要进行验证码校验
     */
    Long USER_MAX_WARN_TIMES_WITHOUT_VERIFICATION_CODE = 3L;

    /*
    会话里面存储登录验证码的属性名称
     */
    String USER_LOGIN_VERIFICATION_CODE_ATTR_NAME = "userLoginVerificationCodeAttrName";

    /*
    Session里面用来表示用户是否登录成功的属性名称
     */
    String USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME = "userLoginSuccessSessionAttrTokenName";

    /*
    Session里面用来保存用户最常用的信息的属性名称，该属性用来辅助拦截常用的请求
     */
    String USER_COMMON_INFO_SESSION_ATTR_NAME = "userCommonInfoSessionAttrName";

    /*
    学生账号前缀，该属性用来区分账号身份
     */
    String USER_STUDENT_ACCOUNT_PREFIX = "S";

    /*
    教师账号前缀，该属性用来区分账号身份
     */
    String USER_TEACHER_ACCOUNT_PREFIX = "T";

    /*
    管理员账号前缀，该属性用来区分账号身份
     */
    String USER_ADMIN_ACCOUNT_PREFIX = "A";

    /*
    用户注册的时候的默认密码
     */
    String USER_DEFAULT_PASSWORD = "M123456";
}
