package com.alex.blog.xo.mapper.sys;

import com.alex.blog.common.entity.sys.WebVisit;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Web访问记录表 Mapper 接口
 * </p>
 *
 * @author alex
 * @since 2022-01-26 17:27:16
 */
@Mapper
public interface WebVisitMapper extends SuperMapper<WebVisit> {

    Integer getIpCount(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
