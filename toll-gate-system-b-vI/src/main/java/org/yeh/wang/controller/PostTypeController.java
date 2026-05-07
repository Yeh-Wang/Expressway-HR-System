package org.yeh.wang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.PostTypeServiceImpl;

/**
 * 岗位信息表 前端控制器
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
@RestController
@CrossOrigin
@RequestMapping("/post-type-entity")
public class PostTypeController {

    private PostTypeServiceImpl postTypeService;

    @Autowired
    public void setPostTypeService(PostTypeServiceImpl postTypeService) {
        this.postTypeService = postTypeService;
    }

    /**
     * 获取所有岗位信息
     */
    @GetMapping("/get-all-post-type")
    public Result<?> getAllPostType() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), "查询成功", postTypeService.list());
    }
}
