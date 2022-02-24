package com.alex.blog.xo.mapper.blog;

import com.alex.blog.common.entity.blog.Subject;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description:  专题表 mapper
 * @author:       alex
 * @createDate:   2021/1/20 20:14
 * @version:      1.0.0
 */
@Mapper
public interface SubjectMapper extends SuperMapper<Subject> {

}
