package org.yeh.wang.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeh.wang.dto.StaffScheduleDto;
import org.yeh.wang.entity.NeedDutyEntity;
import org.yeh.wang.entity.SchedulePatternEntity;
import org.yeh.wang.entity.StaffScheduleEntity;
import org.yeh.wang.entity.StaffUserEntity;
import org.yeh.wang.mapper.StaffScheduleMapper;
import org.yeh.wang.service.StaffScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.vo.CountApplyVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 职员排班表 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
@Service
public class StaffScheduleServiceImpl extends ServiceImpl<StaffScheduleMapper, StaffScheduleEntity> implements StaffScheduleService {

    private StaffUserServiceImpl staffUserService;
    private SchedulePatternServiceImpl schedulePatternService;
    private NeedDutyServiceImpl needDutyService;
    private ApplyFeedbackServiceImpl applyFeedbackService;

    @Autowired
    public void setStaffUserService(StaffUserServiceImpl staffUserService) {
        this.staffUserService = staffUserService;
    }

    @Autowired
    public void setSchedulePatternService(SchedulePatternServiceImpl schedulePatternService) {
        this.schedulePatternService = schedulePatternService;
    }

    @Autowired
    public void setNeedDutyService(NeedDutyServiceImpl needDutyService) {
        this.needDutyService = needDutyService;
    }

    @Autowired
    public void setApplyFeedbackService(ApplyFeedbackServiceImpl applyFeedbackService) {
        this.applyFeedbackService = applyFeedbackService;
    }

    @Override
    public List<StaffScheduleEntity> autoSchedule(List<String> dates) {
        //获取所有职员信息
        List<StaffUserEntity> staffUserEntities = staffUserService.list();
        // 获取所有员工该周请假信息
        List<CountApplyVo> countApplyVos = applyFeedbackService.getBaseMapper().selectGroupByStaffBetween(dates.get(0), dates.get(dates.size() - 1));
        //初始化排班信息
        StaffScheduleDto staffScheduleDto = new StaffScheduleDto();
        staffScheduleDto.setStaffInfos(new ArrayList<>());
        for (StaffUserEntity staffUserEntity : staffUserEntities) {
            if (staffUserEntity.getPostName().equals("站长")) {
                continue;
            }
            StaffScheduleDto.StaffInfo staffInfo = new StaffScheduleDto.StaffInfo();
            staffInfo.setStaffId(staffUserEntity.getStaffId());
            staffInfo.setStaffName(staffUserEntity.getStaffName());
            staffInfo.setWorkHours(40);

            // 初始化剩余工时
            staffInfo.setRemainHours(40);
            countApplyVos.stream().filter(countApplyVo -> countApplyVo.getStaffId().equals(staffUserEntity.getStaffId())).forEach(countApplyVo -> staffInfo.setRemainHours(staffInfo.getRemainHours() - countApplyVo.getDayCount() * 8));

            staffInfo.setStaffPosition(staffUserEntity.getPostName());
            List<String> workDays = new ArrayList<>();
            staffInfo.setWorkDays(workDays);
            staffScheduleDto.getStaffInfos().add(staffInfo);
        }
        staffScheduleDto.setNeedDuties(needDutyService.list());
        staffScheduleDto.setSchedulePatterns(schedulePatternService.list());
        //按照剩余工时排序
        staffScheduleDto.getStaffInfos().sort(Comparator.comparingDouble(StaffScheduleDto.StaffInfo::getRemainHours).reversed());
        //存储排班结果
        List<StaffScheduleEntity> staffScheduleEntities = new ArrayList<>();
        //排班
        for (String data : dates) {
            for (NeedDutyEntity needDuty : staffScheduleDto.getNeedDuties()) {
                for (SchedulePatternEntity schedulePattern : staffScheduleDto.getSchedulePatterns()) {
                    if (needDuty.getDutyRemark().equals(schedulePattern.getSchRemark())) {
                        StaffScheduleEntity staffScheduleEntity = new StaffScheduleEntity();
                        staffScheduleEntity.setProPost(needDuty.getDutyId());
                        staffScheduleEntity.setProSchedule(schedulePattern.getSchId());
                        staffScheduleEntity.setProTime(data);
                        //按照剩余工时排序
                        staffScheduleDto.getStaffInfos().sort(Comparator.comparingDouble(StaffScheduleDto.StaffInfo::getRemainHours).reversed());
                        for (StaffScheduleDto.StaffInfo staffInfo : staffScheduleDto.getStaffInfos()) {
                            double hours = calculateHours(schedulePattern.getSchStart(),schedulePattern.getSchEnd());
                            if (staffInfo.getRemainHours() >= hours && !staffInfo.getWorkDays().contains(data) && staffInfo.getStaffPosition().equals(needDuty.getDutyRemark()) && !applyFeedbackService.isApply(staffInfo.getStaffId(), data)){
                                staffScheduleEntity.setProStaff(staffInfo.getStaffId());
                                staffInfo.setRemainHours(staffInfo.getRemainHours() - hours);
                                staffInfo.getWorkDays().add(data);
                                break;
                            }
                        }
                        staffScheduleEntities.add(staffScheduleEntity);
                    }
                }
            }
        }
        return staffScheduleEntities;
    }

    @Override
    public List<StaffScheduleEntity> getAll() {
        return this.getBaseMapper().selectAll();
    }

    private static double calculateHours(String schStart, String schEnd) {
        // 这里假设schStart和schEnd是HH:mm:ss格式的字符串，并计算出时长
        return DateUtil.between(DateUtil.parse(schStart), DateUtil.parse(schEnd), DateUnit.HOUR);
    }
}
