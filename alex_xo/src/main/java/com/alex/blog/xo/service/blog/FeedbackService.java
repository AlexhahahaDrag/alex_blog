package com.alex.blog.xo.service.blog;

import com.alex.blog.base.service.SuperService;
import com.alex.blog.common.vo.blog.FeedbackVo;
import com.alex.blog.xo.blog.entity.Feedback;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 反馈表 服务类
 * </p>
 *
 * @author alex
 * @since 2022-02-14 16:38:58
 */
public interface FeedbackService extends SuperService<Feedback> {

    /**
     * @param FeedbackVo
     * @description: 获取友链列表
     * @author:      alex
     * @createDate:  2022/1/27 17:56
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Feedback>
     */
    IPage<Feedback> getPageList(FeedbackVo FeedbackVo);

    /**
     * @param FeedbackVo
     * @description: 新增友链
     * @author:      alex
     * @createDate:  2022/1/28 9:42
     * @return:      java.lang.String
     */
    String addFeedback(FeedbackVo FeedbackVo);

    /**
     * @param FeedbackVo
     * @description: 编辑友链
     * @author:      alex
     * @createDate:  2022/1/28 9:43
     * @return:      java.lang.String
     */
    String editFeedback(FeedbackVo FeedbackVo);

    /**
     * @param ids
     * @description: 批量删除友链
     * @author:      alex
     * @createDate:  2022/1/28 9:44
     * @return:      java.lang.String
     */
    String deleteBatchFeedback(List<String> ids);
}
