package org.yeh.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.StudyRecordEntity;
import org.yeh.wang.mapper.StudyRecordMapper;
import org.yeh.wang.service.StudyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.vo.StudyRecordVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.hutool.core.util.NumberUtil.round;

/**
 * <p>
 * 学习记录表 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class StudyRecordServiceImpl extends ServiceImpl<StudyRecordMapper, StudyRecordEntity> implements StudyRecordService {

    StaffInfoServiceImpl staffInfoService;

    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @Override
    public List<StudyRecordVo> selectAllStudyTime(String staffId, String startTime, String endTime) {
        String start = startTime + " 00:00:00";
        String end = endTime + " 23:59:59";
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(staffId);
        if (Objects.equals(staffInfoEntity.getStaffRemark(), "leader")) {
            List<StudyRecordVo> list = this.baseMapper.selectAllStudyTime(start, end);
            list.forEach(item -> {
                double total = round(item.getTotalTime(), 2).doubleValue();
                item.setTotalTime(total);
                item.setStartTime(start);
                item.setEndTime(end);
            });
            return list;
        } else {
            List<StudyRecordVo> result = new ArrayList<>();
            List<StudyRecordVo> list = this.baseMapper.selectAllStudyTime(start, end);
            list.forEach(item -> {
                double total = round(item.getTotalTime(), 2).doubleValue();
                item.setTotalTime(total);
                item.setStartTime(start);
                item.setEndTime(end);
                if (Objects.equals(item.getStaffId(), staffId)) {
                    result.add(item);
                }
            });
            return result;
        }
    }

    public List<StudyRecordVo> selectAllStudyTimeByMonth(String startTime, String endTime) {
        List<StudyRecordVo> list = this.baseMapper.selectAllStudyTime(startTime, endTime);
        list.forEach(item -> {
            double total = round(item.getTotalTime(), 2).doubleValue();
            item.setTotalTime(total);
            item.setStartTime(startTime);
            item.setEndTime(endTime);
        });
        return list;
    }

    @Override
    public List<StudyRecordEntity> selectStudyTimeByTrain(String staffId, String startTime, String endTime, Integer trainId) {
        String start = startTime + " 00:00:00";
        String end = endTime + " 23:59:59";
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(staffId);
        List<StudyRecordEntity> studyRecordEntities = new ArrayList<>();
        if (Objects.equals(staffInfoEntity.getStaffRemark(), "leader")) {
            List<StudyRecordVo> list = this.baseMapper.selectStudyTimeByTrain(start, end, trainId);
            list.forEach(item -> {
                double total = round(item.getTotalTime(), 2).doubleValue();
                item.setTotalTime(total);
                item.setStartTime(start);
                item.setEndTime(end);
                StudyRecordEntity studyRecordEntity = new StudyRecordEntity();
                StaffInfoEntity staffInfo = staffInfoService.getById(item.getStaffId());
                studyRecordEntity.setStaffId(item.getStaffId());
                studyRecordEntity.setStaffName(staffInfo.getStaffName());
                studyRecordEntity.setStuStartTime(start);
                studyRecordEntity.setStuEndTime(end);
                studyRecordEntity.setStuTotalTime(total);
                studyRecordEntity.setStuTrain(trainId);
                studyRecordEntities.add(studyRecordEntity);
            });
        } else {
            List<StudyRecordVo> result = new ArrayList<>();
            List<StudyRecordVo> list = this.baseMapper.selectStudyTimeByTrain(start, end, trainId);
            list.forEach(item -> {
                if (Objects.equals(item.getStaffId(), staffId)) {
                    double total = round(item.getTotalTime(), 2).doubleValue();
                    item.setTotalTime(total);
                    item.setStartTime(start);
                    item.setEndTime(end);
                    result.add(item);
                    StaffInfoEntity staffInfo = staffInfoService.getById(item.getStaffId());
                    StudyRecordEntity studyRecordEntity = new StudyRecordEntity();
                    studyRecordEntity.setStaffId(item.getStaffId());
                    studyRecordEntity.setStaffName(staffInfo.getStaffName());
                    studyRecordEntity.setStuStartTime(start);
                    studyRecordEntity.setStuEndTime(end);
                    studyRecordEntity.setStuTotalTime(total);
                    studyRecordEntity.setStuTrain(trainId);
                    studyRecordEntities.add(studyRecordEntity);
                }
            });
        }
        return studyRecordEntities;
    }
}
