package org.yeh.wang.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yeh.wang.entity.StudyRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.yeh.wang.vo.StudyRecordVo;

import java.util.List;

/**
 * <p>
 * 学习记录表 Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Mapper
public interface StudyRecordMapper extends BaseMapper<StudyRecordEntity> {

    /**
     * 根据时间区间查询学习总时长
     */
    @Select("select study_record.staff_id, " +
            "sum(study_record.stu_total_time) as total_time " +
            "from study_record " +
            "where stu_start_time > #{startTime} " +
            "and stu_end_time < #{endTime} " +
            "group by study_record.staff_id;")
    List<StudyRecordVo> selectAllStudyTime(String startTime, String endTime);

    /**
     * 根据时间区间查询指定培训计划学习总时长
     */
    @Select("select study_record.staff_id, " +
            "sum(study_record.stu_total_time) as total_time " +
            "from study_record " +
            "where stu_start_time > #{startTime} " +
            "and stu_end_time < #{endTime} " +
            "and stu_train = #{trainId} " +
            "group by study_record.staff_id;")
    List<StudyRecordVo> selectStudyTimeByTrain(String startTime, String endTime, Integer trainId);
}
