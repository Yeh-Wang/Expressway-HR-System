package org.yeh.wang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeh.wang.entity.PerformTypeEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.impl.PerformTypeServiceImpl;

/**
 * 面向开发的工具类控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/until")
public class UntilController {

    private PerformTypeServiceImpl performTypeService;

    @Autowired
    public void setPerformTypeService(PerformTypeServiceImpl performTypeService) {
        this.performTypeService = performTypeService;
    }

    /**
     * 添加考核类型
     * @param performTypeEntity 考核类型信息
     */
    @RequestMapping("/add-perform-type")
    public Result<?> addPerformType(PerformTypeEntity performTypeEntity) {
        PerformTypeEntity performTypeEntity1 = new PerformTypeEntity();
        performTypeEntity1.setContent(performTypeEntity.getContent());
        performTypeEntity1.setWeight(performTypeEntity.getWeight());
        performTypeEntity1.setAddPeople(performTypeEntity.getAddPeople());
        performTypeEntity1.setAddTime(performTypeEntity.getAddTime());
        if (performTypeService.save(performTypeEntity1)) {
            return new Result<>(200, "添加成功", performTypeEntity1);
        } else {
            return new Result<>(500, "添加失败", null);
        }
    }

}
