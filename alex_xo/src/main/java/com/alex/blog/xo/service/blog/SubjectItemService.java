package com.alex.blog.xo.service.blog;

import com.alex.blog.common.entity.blog.SubjectItem;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alex.blog.common.vo.blog.SubjectItem.SubjectItemVo;
import java.util.List;
/**
 * 专题Item表 服务类
 * @author: alex
 * @createDate: 2022-03-03 21:32:16
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface SubjectItemService extends SuperService<SubjectItem> {

    /**
     * @param: subjectItemVo
     * @description: 获取专题Item表列表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:16
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.SubjectItem>
     */
    IPage<SubjectItem> getPageList(SubjectItemVo subjectItemVo);

    /**
     * @param: subjectItemVo
     * @description: 新增专题Item表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:16
     * @return:      java.lang.String
     */
    String addSubjectItem(SubjectItemVo subjectItemVo);

    /**
     * @param: subjectItemVo
     * @description: 编辑专题Item表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:16
     * @return:      java.lang.String
     */
    String editSubjectItem(SubjectItemVo subjectItemVo);

    /**
     * @param ids
     * @description: 批量删除专题Item表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:16
     * @return:      java.lang.String
     */
    String deleteBatchSubjectItem(List<String> ids);
}
