package com.alex.blog.id.controller;

import com.alex.blog.id.entity.Demo;
import org.springframework.web.bind.annotation.*;

/**
 *description:  生成id
 *author:       majf
 *createDate:   2022/7/1 14:36
 *version:      1.0.0
 */
@RestController
@RequestMapping(value = "/id")
public class IdGeneratorController {

    @GetMapping(value = "/nextId")
    public Demo getSegmentId(@RequestParam(value = "key") String key) {
        Demo demo = new Demo();
        return demo;
    }
}
