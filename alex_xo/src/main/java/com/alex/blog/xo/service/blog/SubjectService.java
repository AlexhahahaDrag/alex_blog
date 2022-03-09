package com.alex.blog.xo.service.blog;

import com.alex.blog.common.entity.blog.Subject;
import com.alex.blog.base.service.SuperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.alex.blog.common.vo.blog.SubjectVo;
import java.util.List;
/**
 * 专题表 服务类
 * @author: alex
 * @createDate: 2022-03-03 21:32:04
 * @description: 我是由代码生成器生成
 * version: 1.0.0
 */
public interface SubjectService extends SuperService<Subject> {

    /**
     * @param: subjectVo
     * @description: 获取专题表列表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:04
     * @return:      com.baomidou.mybatisplus.core.metadata.IPage<com.alex.blog.common.entity.blog.Subject>
     */
    IPage<Subject> getPageList(SubjectVo subjectVo);

    /**
     * @param: subjectVo
     * @description: 新增专题表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:04
     * @return:      java.lang.String
     */
    String addSubject(SubjectVo subjectVo);

    /**
     * @param: subjectVo
     * @description: 编辑专题表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:04
     * @return:      java.lang.String
     */
    String editSubject(SubjectVo subjectVo);

    /**
     * @param ids
     * @description: 批量删除专题表
     * @author:      alex
     * @createDate:  2022-03-03 21:32:04
     * @return:      java.lang.String
     */
    String deleteBatchSubject(List<String> ids);
}
