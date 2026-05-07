package org.yeh.wang.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.yeh.wang.entity.CheckSheetEntity;

@AllArgsConstructor
@Setter
@Getter
@Data
public class CheekSheetVo {
    /**
     * 考勤号
     */
    private Integer chId;

    /**
     * 月份
     */
    private String chMonth;

    /**
     * 出任务次数
     */
    private Integer chTaskTimes;

    /**
     * 请假天数
     */
    private double chVacateDay;

    /**
     * 加班时长
     */
    private double chOverTime;

    /**
     * 应出勤天数
     */
    private Integer chSetDay;

    /**
     * 实出勤天数
     */
    private Integer chRealDay;

    /**
     * 职工编号
     */
    private String staffId;

    /**
     * 职工姓名
     */
    private String staffName;

    public CheekSheetVo(CheckSheetEntity checkSheetEntity,String staffName) {
        this.chId = checkSheetEntity.getChId();
        this.chMonth = checkSheetEntity.getChMonth();
        this.chTaskTimes = checkSheetEntity.getChTaskTimes();
        this.chVacateDay = checkSheetEntity.getChVacateDay();
        this.chOverTime = checkSheetEntity.getChOverTime();
        this.chRealDay = checkSheetEntity.getChRealDay();
        this.chSetDay = checkSheetEntity.getChSetDay();
        this.staffId = checkSheetEntity.getStaffId();
        this.staffName = staffName;
    }
}
