package com.alex.blog.xo.service.sys;

import com.alex.blog.common.entity.sys.WebVisit;
import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.vo.sys.WebVisitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * Web访问记录表 服务类
 * </p>
 *
 * @author alex
 * @since 2022-01-26 17:27:16
 */
public interface WebVisitService extends SuperService<WebVisit> {

    /**
     * @param adminId
     * @param moduleId
     * @param otherData
     * @description: 增加访问记录（异步接口）
     * @author:      alex
     * @createDate:  2022/1/26 17:35
     * @return:      void
    */
    void addWebVisit(String adminId, HttpServletRequest request, String moduleId, String otherData);

    /**
     * @description: 获取今日访问量
     * @author:      alex
     * @createDate:  2022/1/26 17:32
     * @return:      java.lang.Integer
    */
    Integer getWebVisitCount();

    /**
     * @description: 获取近7天访问量
     * @author:      alex
     * @createDate:  2022/1/26 17:35
     * @return:      Map<String,Object>
    */
    Map<String, Object> getVisitByWeek();

    /**
     * @param webVisitVo
     * @description: 获取访问列表
     * @author:      alex
     * @createDate:  2022/1/26 17:33
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.sys.WebVisit>
    */
    IPage<WebVisit> getPageList(WebVisitVo webVisitVo);
}
