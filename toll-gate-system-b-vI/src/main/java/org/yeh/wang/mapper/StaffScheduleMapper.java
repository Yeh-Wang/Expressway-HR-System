package org.yeh.wang.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yeh.wang.entity.StaffScheduleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 职员排班表 Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
@Mapper
public interface StaffScheduleMapper extends BaseMapper<StaffScheduleEntity> {

    /**
     * 连表查询所有排班信息
     */
    @Select("SELECT staff_schedule.*, " +
            "staff_info.staff_name, " +
            "need_duty.duty_name, " +
            "schedule_pattern.sch_start, " +
            "schedule_pattern.sch_end " +
            "from staff_schedule " +
            "left join staff_info " +
            "on staff_schedule.pro_staff = staff_info.staff_id " +
            "left join schedule_pattern " +
            "on staff_schedule.pro_schedule = schedule_pattern.sch_id " +
            "left join need_duty " +
            "on staff_schedule.pro_post = need_duty.duty_id")
    List<StaffScheduleEntity> selectAll();

    /**
     * 根据职员id连表查询该职员所有排班信息
     */
    @Select("SELECT staff_schedule.*, " +
            "staff_info.staff_name, " +
            "need_duty.duty_name, " +
            "schedule_pattern.sch_start, " +
            "schedule_pattern.sch_end " +
            "from staff_schedule " +
            "left join staff_info " +
            "on staff_schedule.pro_staff = staff_info.staff_id " +
            "left join schedule_pattern " +
            "on staff_schedule.pro_schedule = schedule_pattern.sch_id " +
            "left join need_duty " +
            "on staff_schedule.pro_post = need_duty.duty_id " +
            "where staff_schedule.pro_staff = #{staffId}")
    List<StaffScheduleEntity> selectScheduleInfoById(@Param("staffId") String staffId);
}
