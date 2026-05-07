package org.yeh.wang.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yeh.wang.entity.DailyPunchEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.yeh.wang.vo.CountApplyVo;
import org.yeh.wang.vo.CountDailyVo;

import java.util.List;

/**
 * <p>
 * 日常打卡表 Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Mapper
public interface DailyPunchMapper extends BaseMapper<DailyPunchEntity> {


    /**
     * 分组统计指定月份的打卡记录
     * @param month 月份
     */
    @Select("select daily_punch.staff_id, " +
            "count(daily_punch.pun_type) as total_day, " +
            "SUM(daily_punch.pun_work_time) as total_time " +
            "from daily_punch " +
            "where daily_punch.pun_start_time like #{month} " +
            "group by daily_punch.staff_id;")
    List<CountDailyVo> selectGroupByStaff(@Param("month") String month);
}
