package com.alex.blog.xo.mapper.sys;

import com.alex.blog.common.entity.sys.WebVisit;
import com.alex.blog.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * @param startTime
     * @param endTime
     * @description: 获取ip访问数
     * @author:      alex
     * @createDate:  2022/1/27 14:22
     * @return:      java.lang.Integer
    */
    Integer getIpCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @param startTime
     * @param endTime
     * @description: 获取指定日期内访问量
     * @author:      alex
     * @createDate:  2022/1/27 14:22
     * @return:      java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Integer> getPvByWeek(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @param startTime
     * @param endTime
     * @description: 获取指定日期内独立用户数
     * @author:      alex
     * @createDate:  2022/1/27 14:23
     * @return:      java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Integer> getUvByWeek(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
