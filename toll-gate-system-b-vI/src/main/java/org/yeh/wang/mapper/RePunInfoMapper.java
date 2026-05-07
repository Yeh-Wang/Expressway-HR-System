package org.yeh.wang.mapper;

import org.apache.ibatis.annotations.Select;
import org.yeh.wang.entity.RePunInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.yeh.wang.vo.CountRePunInfoVo;
import org.yeh.wang.vo.RePunInfoVo;

import java.util.List;

/**
 * <p>
 * 员工奖惩 Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Mapper
public interface RePunInfoMapper extends BaseMapper<RePunInfoEntity> {


    @Select("SELECT " +
            "rew_id, " +
            "rew_content, " +
            "rew_time, " +
            "rew_nature, " +
            "rew_staff, " +
            "staff_name" +
            "FROM  re_pun_info, staff_info where re_pun_info.rew_staff = staff_info.staff_id and re_pun_info.rew_staff = #{staffId}")
    public List<RePunInfoVo> getAllPunInfoByStaffId(String staffId);


    /**
     * 计算指定月份员工某性质的奖惩次数
     */
    @Select("SELECT count(rew_id) FROM re_pun_info WHERE rew_time like #{month} and rew_nature = #{rewNature} and rew_staff = #{staffId}")
    public Integer countRePunInfo(String month,String rewNature,String staffId);
}
