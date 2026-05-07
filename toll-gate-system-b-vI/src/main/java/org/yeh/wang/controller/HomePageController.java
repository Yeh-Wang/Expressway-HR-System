package org.yeh.wang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.PublicNoticeEntity;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.PublicNoticeServiceImpl;
import org.yeh.wang.service.impl.StaffInfoServiceImpl;
import org.yeh.wang.vo.PublicNoticeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 面向前端首页展示的控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/home-page")
public class HomePageController {

    private PublicNoticeServiceImpl noticeService;
    private StaffInfoServiceImpl staffInfoService;

    @Autowired
    public void setNoticeService(PublicNoticeServiceImpl noticeService) {
        this.noticeService = noticeService;
    }
    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    /**
     * 获取所有公告信息
     * @return 所有公告信息
     */
    @RequestMapping("/get-all-notice")
    public Result<List<PublicNoticeVo>> getAllNotice() {
        List<PublicNoticeEntity> list = noticeService.list();
        List<PublicNoticeVo> listVo = new ArrayList<>();
        list.forEach(item->{
            StaffInfoEntity staffInfoEntity = staffInfoService.getById(item.getPubCreate());
            PublicNoticeVo vo = new PublicNoticeVo();
            vo.setPubId(item.getPubId());
            vo.setPubTitle(item.getPubTitle());
            vo.setPubContent(item.getPubContent());
            vo.setPubTime(item.getPubTime());
            vo.setPubCreate(item.getPubCreate());
            vo.setPubStatus(item.getPubStatus());
            vo.setPubCreateName(staffInfoEntity.getStaffName());
            listVo.add(vo);
        });
        return new Result<>(200, "获取成功", listVo);
    }

    /**
     * 增加公告信息
     * @param publicNoticeEntity 公告信息
     */
    @RequestMapping("/add-notice")
    public Result<?> addNotice(@RequestBody PublicNoticeEntity publicNoticeEntity) {
        if (noticeService.save(publicNoticeEntity)) {
            return new Result<>(ResultEnum.SUCCESS.getCode(), "添加成功", publicNoticeEntity);
        } else {
            return new Result<>(ResultEnum.ERROR.getCode(), "添加失败", null);
        }
    }
    /**
     * 编辑公告信息
     */
    @RequestMapping("/update-notice")
    public Result<?> updateNotice(@RequestBody PublicNoticeEntity publicNoticeEntity) {
        if (noticeService.updateById(publicNoticeEntity)) {
            return new Result<>(ResultEnum.SUCCESS.getCode(), "修改成功", publicNoticeEntity);
        } else {
            return new Result<>(ResultEnum.ERROR.getCode(), "修改失败", null);
        }
    }

    /**
     * 删除公告信息
     */
    @RequestMapping("/delete-notice/{pubId}")
    public Result<?> deleteNotice(@PathVariable Integer pubId) {
        if (noticeService.removeById(pubId)) {
            return new Result<>(ResultEnum.SUCCESS.getCode(), "删除成功", null);
        } else {
            return new Result<>(ResultEnum.ERROR.getCode(), "删除失败", null);
        }
    }
}
