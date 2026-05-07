package org.yeh.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeh.wang.entity.ApplyFeedbackEntity;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.StaffUserEntity;
import org.yeh.wang.mapper.ApplyFeedbackMapper;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.ApplyFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.vo.ApplyFeedbackVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 职员申请或反馈表 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class ApplyFeedbackServiceImpl extends ServiceImpl<ApplyFeedbackMapper, ApplyFeedbackEntity> implements ApplyFeedbackService {

    private StaffUserServiceImpl staffUserService;
    private StaffInfoServiceImpl staffInfoService;

    @Autowired
    public void setStaffUserService(StaffUserServiceImpl staffUserService) {
        this.staffUserService = staffUserService;
    }
    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @Override
    public Result<?> listByStaffId(String staffId) {
        StaffUserEntity staffUserEntity = staffUserService.getOne(new QueryWrapper<StaffUserEntity>().eq("staff_id", staffId));
        List<ApplyFeedbackVo> list = new ArrayList<>();
        if (Objects.equals(staffUserEntity.getPostName(), "站长") || Objects.equals(staffUserEntity.getPostName(), "收费班长")){
            List<StaffInfoEntity> staffInfoEntityList = staffInfoService.getStaffInfoByStaffId(staffId);
            staffInfoEntityList.forEach(staffInfoEntity -> {
                List<ApplyFeedbackEntity> applyFeedbackEntityList = this.list(new QueryWrapper<ApplyFeedbackEntity>().eq("staff_id", staffInfoEntity.getStaffId()));
                List<ApplyFeedbackVo> applyFeedbackVoList = new ArrayList<>();
                applyFeedbackEntityList.forEach(applyFeedbackEntity -> {
                    ApplyFeedbackVo applyFeedbackVo = new ApplyFeedbackVo();
                    applyFeedbackVo.setProcessId(applyFeedbackEntity.getProcessId());
                    applyFeedbackVo.setProcessContent(applyFeedbackEntity.getProcessContent());
                    applyFeedbackVo.setProcessTime(applyFeedbackEntity.getProcessTime());
                    applyFeedbackVo.setProcessType(applyFeedbackEntity.getProcessType());
                    applyFeedbackVo.setCfoView(applyFeedbackEntity.getCfoView());
                    applyFeedbackVo.setCfoTime(applyFeedbackEntity.getCfoTime());
                    applyFeedbackVo.setCfoId(applyFeedbackEntity.getCfoId());
                    applyFeedbackVo.setIsVerify(applyFeedbackEntity.getIsVerify());
                    applyFeedbackVo.setStaffId(applyFeedbackEntity.getStaffId());
                    applyFeedbackVo.setStaffName(staffInfoEntity.getStaffName());
                    StaffInfoEntity staffInfo = staffInfoService.getById(applyFeedbackEntity.getCfoId());
                    applyFeedbackVo.setCfoName(staffInfo.getStaffName());
                    applyFeedbackVoList.add(applyFeedbackVo);
                });
                list.addAll(applyFeedbackVoList);
            });
        } else {
            List<ApplyFeedbackEntity> applyFeedbackEntityList = this.list(new QueryWrapper<ApplyFeedbackEntity>().eq("staff_id", staffId));
            applyFeedbackEntityList.forEach(applyFeedbackEntity -> {
                ApplyFeedbackVo applyFeedbackVo = new ApplyFeedbackVo();
                applyFeedbackVo.setProcessId(applyFeedbackEntity.getProcessId());
                applyFeedbackVo.setProcessContent(applyFeedbackEntity.getProcessContent());
                applyFeedbackVo.setProcessTime(applyFeedbackEntity.getProcessTime());
                applyFeedbackVo.setProcessType(applyFeedbackEntity.getProcessType());
                applyFeedbackVo.setCfoView(applyFeedbackEntity.getCfoView());
                applyFeedbackVo.setCfoTime(applyFeedbackEntity.getCfoTime());
                applyFeedbackVo.setCfoId(applyFeedbackEntity.getCfoId());
                applyFeedbackVo.setIsVerify(applyFeedbackEntity.getIsVerify());
                applyFeedbackVo.setStaffId(applyFeedbackEntity.getStaffId());
                applyFeedbackVo.setStaffName(staffUserEntity.getStaffName());
                applyFeedbackVo.setCfoName(staffUserEntity.getStaffName());
                list.add(applyFeedbackVo);
            });
        }
        return new Result<>(ResultEnum.SUCCESS.getCode(), "查询成功", list);
    }

    @Override
    public boolean isApply(String staffId, String time) {
        List<ApplyFeedbackEntity> applyFeedbackEntityList = this.list(new QueryWrapper<ApplyFeedbackEntity>().eq("staff_id", staffId).eq("process_type",1).eq("cfo_view","批准").like("process_time", time));
        return !applyFeedbackEntityList.isEmpty();
    }
}
