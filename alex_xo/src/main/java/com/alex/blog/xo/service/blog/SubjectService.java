package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.entity.blog.Subject;
import com.alex.blog.common.vo.blog.FeedbackVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
/**
 * <p>
 * 专题表 服务类
 * </p>
 * @author: alex
 * @createDate: 2022-02-24 15:46:25
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface SubjectService extends SuperService<Subject> {

    /**
     * @param FeedbackVo
     * @description: 获取专题表列表
     * @author:      alex
     * @createDate:  2022-02-24 15:46:25
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Subject>
     */
    IPage<Subject> getPageList(FeedbackVo FeedbackVo);

    /**
     * @param FeedbackVo
     * @description: 新增专题表
     * @author:      alex
     * @createDate:  2022-02-24 15:46:25
     * @return:      java.lang.String
     */
    String addSubject(FeedbackVo FeedbackVo);

    /**
     * @param FeedbackVo
     * @description: 编辑专题表
     * @author:      alex
     * @createDate:  2022-02-24 15:46:25
     * @return:      java.lang.String
     */
    String editSubject(FeedbackVo FeedbackVo);

    /**
     * @param ids
     * @description: 批量删除专题表
     * @author:      alex
     * @createDate:  2022-02-24 15:46:25
     * @return:      java.lang.String
     */
    String deleteBatchSubject(List<String> ids);
}
