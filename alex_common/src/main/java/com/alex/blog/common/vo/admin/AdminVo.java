package com.alex.blog.common.vo.admin;

import com.alex.blog.base.vo.BaseVo;
import lombok.Data;

import java.util.Date;

/**
 *description:  admin vo
 *author:       alex
 *createDate:   2021/7/11 19:50
 *version:      1.0.0
 */
@Data
public class AdminVo extends BaseVo<AdminVo> {

    //用户名
    private String usrname;

    //密码
    private String password;

    //昵称
    private String nickName;

    //性别
    private String gender;

    //个人头像
    private String avatar;

    //邮箱
    private String email;

    //生日
    private Date birthday;

    //电话
    private String mobile;

    //qq号
    private String qqNumber;

    //微信号
    private String weChat;

    //职业
    private String occupation;

    //自我介绍
    private String summary;

    //个人履历
    private String personResume;

    //github
    private String github;

    //gitee
    private String gitee;

    //角色id
    private String roleUid;

    //最大网盘容量
    private Long maxStorageSize;
}
