package com.alex.blog.miaosha.web.restApi;

import com.alex.blog.miaosha.common.entity.MiaoshaUser;
import com.alex.blog.miaosha.xo.service.GoodsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  restApi
 * @author:       alex
 * @createDate: 2022-07-04 18:10:59
 * @version:      1.0.0
 */
@Api(value = "货物相关接口", tags = {"货物相关接口"})
@RestController
@RequestMapping("/goods")
public class GoodsRestApi {

    @Autowired
    private final MissionCleint missionCleint;

    public String list(@RequestBody MiaoshaUser user) {

    }
}
