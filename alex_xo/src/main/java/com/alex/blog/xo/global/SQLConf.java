package com.alex.blog.xo.global;

import com.alex.blog.base.global.BaseSqlConf;

/**
 *description:  sql字段常量
 *author:       alex
 *createDate:   2021/7/23 7:02
 *version:      1.0.0
 */
public final class SQLConf extends BaseSqlConf {

    //参数配置相关
    public static final String PARAMS_NAME = "params_name";

    public static final String PARAMS_KEY = "params_key";

    public static final String PARAMS_VALUE = "params_value";

    public static final String PATAMS_TYPE = "params_type";

    //user表
    public static final String USERNAME = "username";

    public static final String USER_EMAIL = "email";

    public static final String SOURCE = "source";

    public static final String MOBILE = "mobile";

    public static final String PASSWORD = "password";

    public static final String COMMENT_STATUS = "comment_status";

    public static final String UUID = "uuid";

    public static final String URL = "url";

    public static final String USER_ID = "user_id";

    public static final String TO_USER_ID = "to_user_id";

    public static final String COMMENT_ID = "comment_id";

    public static final String REPORT_COMMENT_ID = "report_comment_id";

    public static final String TAG_ID = "tag_id";

    public static final String AVATAR = "avatar";


    //system config 相关
    public static final String UPLOAD_QI_NIU = "upload_qi_niu";

    public static final String UPLOAD_LOCAL = "upload_local";

    public static final String QINIU_PICTURE_BASE_URL = "qi_niu_picture_base_url";

    public static final String LOCAL_PICTURE_BASE_URL = "local_picture_base_url";

    public static final String QI_NIU_ACCESS_KEY = "qi_niu_access_key";

    public static final String QI_NIU_SECRET_KEY = "qi_niu_secret_key";

    public static final String QI_NIU_BUCKET = "qi_niu_bucket";

    public static final String QI_NIU_AREA = "qi_niu_area";

    public static final String PICTURE_PRIORITY = "picture_priority";


    //web_visit表
    public static final String IP = "ip";

    //t_comment表
    public static final String TYPE = "type";

    /**
     * t_subject表
     */
    public final static String SUBJECT_NAME = "subject_name";
    public final static String SUBJECT_UID = "subject_id";

    /**
     * t_picture表
     */
    public final static String FILE_OLD_NAME = "file_old_name";
    public final static String FILE_PATH = "file_path";
    public final static String FILE_NAME = "file_name";
    public final static String DATA = "data";

    public final static String SORT_NAME = "sort_name";
    public final static String RESOURCE_SORT_UID = "resource_sort_uid";

    public final static String FILE_TYPE = "file_type";
    public final static String EXTEND_NAME = "extend_name";
    public final static String IS_DIR = "is_dir";

    //role
    public static final String ROLEID = "role_id";
    public static final String ROLENAME = "role_name";
}
