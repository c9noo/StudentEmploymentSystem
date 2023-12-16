package com.employment.constant;

/**
 * @program: StudentEmploymentSystem
 * @ClassName RedisConstant
 * @author: c9noo
 * @create: 2023-11-08 20:36
 * @Version 1.0
 * 存入redis时设置key的前缀常量池
 **/
public class RedisConstant {

    /**
     * redis过期时间，小时 * 3600秒
     */
    public static final Integer REDIS_TIME = 3 * 60 * 60;

    /**
     * 设置redis中验证码的过期时间
     */
    public static final Integer REDIS_CAPTCHA_TIME = 5 * 60;

    /**
     * 设置redis中用户的过期时间
     */
    public static final Integer REDIS_USER_TIME = 1 * 60 * 60;

    /**
     * redis存验证码 的前缀
     */
    public static final String REDIS_CAPTCHA = "captcha:";

    /**
     * 随机秒数 0-60
     */
    public static final Integer RANDOM_TIME = 61;

    public static final String REDIS_EMAIL = "email:";

    public static final Integer REDIS_EMAIL_TIME = 3 * 60;

    /**
     * 后端用户存入redis的key前缀
     */
    public static final String REDIS_USER_KEY = "system:user:";

    /**
     * 招聘信息分页查询存入redis的key前缀
     */
    public static final String REDIS_RECRUIT_PAGE = "recruitPage";

    /**
     * 招聘信息的详情存入redis的key前缀
     */
    public static final String REDIS_RECRUIT_DETAIL = "recruitDetail";

    /**
     * 公司信息分页查询存入redis的key前缀
     */
    public static final String REDIS_COMPANY_PAGE = "companyPage";

    /**
     * 公司信息的详情存入redis的key前缀
     */
    public static final String REDIS_COMPANY_DETAIL = "companyDetail";

    /**
     * 老师信息的分页查询存入redis的key值
     */
    public static final String REDIS_TEACHER_PAGE = "teacherPage";

    /**
     * 老师信息详情存入redis的key值
     */
    public static final String REDIS_TEACHER_DETAIL = "teacherDetail";

    /**
     * 校友信息分页查询
     */
    public static final String REDIS_ALUMNI_PAGE = "alumniPage";


}
