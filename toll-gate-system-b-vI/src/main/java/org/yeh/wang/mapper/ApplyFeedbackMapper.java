package org.yeh.wang.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yeh.wang.entity.ApplyFeedbackEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.yeh.wang.vo.CountApplyVo;

import java.util.List;

/**
 * <p>
 * 职员申请或反馈表 Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Mapper
public interface ApplyFeedbackMapper extends BaseMapper<ApplyFeedbackEntity> {

    /**
     * 分组查询指定月份的请假申请
     */
    @Select("select staff_id as staff_id," +
            "SUM(apply_feedback.is_verify) as day_count " +
            "from apply_feedback " +
            "where process_type = 1 and cfo_view='批准' and process_time like #{month} " +
            "group by apply_feedback.staff_id;")
    List<CountApplyVo> selectGroupByStaff(@Param("month") String month);

    /**
     * 分组查询指定时间段的请假申请
     */
    @Select("select staff_id as staff_id," +
            "SUM(apply_feedback.is_verify) as day_count " +
            "from apply_feedback " +
            "where process_type = 1 and cfo_view='批准' and process_time between #{startTime} and #{endTime} " +
            "group by apply_feedback.staff_id;")
    List<CountApplyVo> selectGroupByStaffBetween(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据类型和上级意见查询申请
     */
    @Select("select apply_feedback.*,staff_info.staff_name from apply_feedback join staff_info on staff_info.staff_id=apply_feedback.staff_id where process_type = #{processType} and cfo_view = #{cfoView}")
    List<ApplyFeedbackEntity> selectByTypeView(@Param("processType") Integer processType,@Param("cfoView") String cfoView);

    /**
     * 根据职员id获取申请信息
     */
    @Select("select apply_feedback.*,staff_info.staff_name from apply_feedback join staff_info on staff_info.staff_id=apply_feedback.staff_id where apply_feedback.staff_id = #{staffId}")
    List<ApplyFeedbackEntity> selectByStaffId(@Param("staffId") String staffId);
}
