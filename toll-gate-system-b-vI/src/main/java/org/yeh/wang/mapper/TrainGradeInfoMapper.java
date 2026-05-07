package org.yeh.wang.mapper;

import org.apache.ibatis.annotations.Select;
import org.yeh.wang.entity.TrainGradeInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 培训考核成绩表; Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Mapper
public interface TrainGradeInfoMapper extends BaseMapper<TrainGradeInfoEntity> {

    /**
     * 根据培训计划id查询学员培训成绩信息
     */
    @Select("SELECT train_grade_info.*,staff_user.staff_name as staffName,staff_user.post_name as postName FROM train_grade_info left join staff_user on train_grade_info.staff_id = staff_user.staff_id WHERE tra_id = #{traId}")
    List<TrainGradeInfoEntity> getTrainGradeInfoByTrainInfoId(Integer traId);
}
