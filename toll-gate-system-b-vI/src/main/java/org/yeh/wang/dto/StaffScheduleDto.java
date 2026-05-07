package org.yeh.wang.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.yeh.wang.entity.NeedDutyEntity;
import org.yeh.wang.entity.SchedulePatternEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffScheduleDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class StaffInfo{
        private String staffId;  // 职员ID
        private String staffName;  // 职员姓名
        private double workHours;  // 可提供工作时长
        private double remainHours;  // 剩余可提供时长
        private String staffPosition;  // 职员职位
        private List<String> workDays;  // 已被分配的日期
    }

    private List<StaffInfo> staffInfos;  // 职员信息
    private List<NeedDutyEntity> needDuties;  // 需要排班的职位信息
    private List<SchedulePatternEntity> schedulePatterns;  // 排班模式
}
