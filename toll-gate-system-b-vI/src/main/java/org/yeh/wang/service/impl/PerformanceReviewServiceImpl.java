package org.yeh.wang.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeh.wang.dto.PerformanceInfoDto;
import org.yeh.wang.entity.*;
import org.yeh.wang.mapper.PerformanceReviewMapper;
import org.yeh.wang.mapper.RePunInfoMapper;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.PerformanceReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.vo.CountRePunInfoVo;
import org.yeh.wang.vo.RePunInfoVo;
import org.yeh.wang.vo.StudyRecordVo;

import java.util.*;

/**
 * <p>
 * 绩效考核 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class PerformanceReviewServiceImpl extends ServiceImpl<PerformanceReviewMapper, PerformanceReviewEntity> implements PerformanceReviewService {

    private StaffInfoServiceImpl staffInfoService;
    private StaffUserServiceImpl staffUserService;
    private RePunInfoServiceImpl rePunInfoService;
    private RePunInfoMapper rePunInfoMapper;
    private CheckSheetServiceImpl checkSheetService;
    private StudyRecordServiceImpl studyRecordService;

    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @Autowired
    public void setStaffUserService(StaffUserServiceImpl staffUserService) {
        this.staffUserService = staffUserService;
    }

    @Autowired
    public void setRePunInfoService(RePunInfoServiceImpl rePunInfoService) {
        this.rePunInfoService = rePunInfoService;
    }

    @Autowired
    public void setRePunInfoMapper(RePunInfoMapper rePunInfoMapper) {
        this.rePunInfoMapper = rePunInfoMapper;
    }

    @Autowired
    public void setCheckSheetService(CheckSheetServiceImpl checkSheetService) {
        this.checkSheetService = checkSheetService;
    }

    @Autowired
    public void setStudyRecordService(StudyRecordServiceImpl studyRecordService) {
        this.studyRecordService = studyRecordService;
    }

    @Override
    public Result<?> getAllPunInfoByStaffId(String staffId) {
        List<StaffInfoEntity> list_1 = staffInfoService.getStaffInfoByStaffId(staffId);
        List<RePunInfoVo> list = new ArrayList<>();
        list_1.forEach(item -> {
            List<RePunInfoEntity> list_2 = rePunInfoService.list(new QueryWrapper<RePunInfoEntity>().eq("rew_staff", item.getStaffId()));
            list_2.forEach(item_1 -> {
                RePunInfoVo rePunInfoVo = new RePunInfoVo();
                rePunInfoVo.setRewId(item_1.getRewId());
                rePunInfoVo.setRewContent(item_1.getRewContent());
                rePunInfoVo.setRewTime(item_1.getRewTime());
                rePunInfoVo.setRewNature(item_1.getRewNature());
                rePunInfoVo.setRewStaff(item_1.getRewStaff());
                rePunInfoVo.setStaffName(item.getStaffName());
                list.add(rePunInfoVo);
            });
        });
        return Result.success(list);
    }

    @Override
    public Result<?> addRePunInfo(RePunInfoEntity rePunInfo) {
        if (Objects.nonNull(rePunInfo)) {
            boolean save = rePunInfoService.save(rePunInfo);
            if (save) {
                return new Result<>(200, "添加成功", rePunInfo);
            } else {
                return Result.failed("添加失败，请重试");
            }
        } else {
            return Result.failed("数据为空，无法添加");
        }
    }

    @Override
    public Result<?> deleteRePunInfo(Integer rewId) {
        if (Objects.nonNull(rewId)) {
            boolean remove = rePunInfoService.removeById(rewId);
            if (remove) {
                return Result.success("删除成功");
            } else {
                return Result.failed("删除失败，信息已被删除");
            }
        } else {
            return Result.failed("数据为空，无法删除");
        }
    }

    @Override
    public List<PerformanceReviewEntity> getPerformReviewByStaffId(String staffId) {
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(staffId);
        List<PerformanceReviewEntity> list = new ArrayList<>();
        if (Objects.equals(staffInfoEntity.getStaffRemark(), "leader")) {
            list = this.list();
        } else {
            list = this.list(new QueryWrapper<PerformanceReviewEntity>().eq("per_staff", staffId));
        }
        return list;
    }

    @Override
    public List<PerformanceReviewEntity> generatePerformReview(PerformanceInfoDto performanceInfoDto) {
        //删除该月份的绩效考核结果
        this.remove(new QueryWrapper<PerformanceReviewEntity>().like("per_time", performanceInfoDto.getStartTime()).eq("per_type", performanceInfoDto.getPerType()));
        Map<String, Double> contentGrade = new HashMap<>();
        List<PerformanceReviewEntity> resultList = new ArrayList<>();
        String startTime = DateUtil.beginOfMonth(DateUtil.parse(performanceInfoDto.getStartTime() + "-15")).toString();
        String endTime = DateUtil.endOfMonth(DateUtil.parse(performanceInfoDto.getStartTime() + "-15")).toString();
        List<StaffUserEntity> list = staffUserService.list(new QueryWrapper<StaffUserEntity>().eq("post_name", performanceInfoDto.getPerType()));
        //遍历绩效考核方式Map<String, Double> contentWeight
        performanceInfoDto.getContentWeight().forEach((k, v) -> {
            if (Objects.equals(k, "考勤")) {
//                    获取该月份所有员工的考勤信息
                List<CheckSheetEntity> checkSheetEntities = this.getCheckSheetByPostName(performanceInfoDto.getPerType(), performanceInfoDto.getStartTime());
//                //遍历输出考勤信息
//                checkSheetEntities.forEach(System.out::println);
                //遍历考勤信息并计算分数
                checkSheetEntities.forEach(item -> {
                    if(item != null){
                        list.forEach(item_1 -> {
                            if (Objects.equals(item_1.getStaffId(), item.getStaffId())) {
                                //生成绩效考核结果表
                                PerformanceReviewEntity performanceReviewEntity = new PerformanceReviewEntity();
                                performanceReviewEntity.setPerType(performanceInfoDto.getPerType());
                                performanceReviewEntity.setPerContent(k);
                                performanceReviewEntity.setPerTime(startTime + "至" + endTime);
                                performanceReviewEntity.setPerStaff(item.getStaffId());
                                performanceReviewEntity.setPerStaffName(item_1.getStaffName());
                                if (item.getChRealDay() >= 20) {
                                    performanceReviewEntity.setPerResult(100.0 * v + "");
                                } else if (item.getChRealDay() >= 15) {
                                    performanceReviewEntity.setPerResult(80.0 * v + "");
                                } else if (item.getChRealDay() >= 10) {
                                    performanceReviewEntity.setPerResult(60.0 * v + "");
                                } else {
                                    performanceReviewEntity.setPerResult(0.0 * v + "");
                                }
                                performanceReviewEntity.setPerRemark("子项");
                                this.save(performanceReviewEntity);
                                resultList.add(performanceReviewEntity);
                            }
                        });
                    }
                });
            }
            if (Objects.equals(k, "奖惩")) {
                //获取该月份所有员工的奖惩信息
                List<CountRePunInfoVo> rePunInfoEntities = this.getRePunInfoByPostName(performanceInfoDto.getPerType(), performanceInfoDto.getStartTime());
//                //遍历输出奖惩信息
//                rePunInfoEntities.forEach(System.out::println);
                //遍历奖惩信息并计算分数
                rePunInfoEntities.forEach(item -> {
                    list.forEach(item_1 -> {
                        if (Objects.equals(item_1.getStaffId(), item.getStaffId())) {
                            //生成绩效考核结果表
                            PerformanceReviewEntity performanceReviewEntity = new PerformanceReviewEntity();
                            performanceReviewEntity.setPerType(performanceInfoDto.getPerType());
                            performanceReviewEntity.setPerContent(k);
                            performanceReviewEntity.setPerTime(startTime + "至" + endTime);
                            performanceReviewEntity.setPerStaff(item.getStaffId());
                            performanceReviewEntity.setPerStaffName(item_1.getStaffName());
                            double score = item.getTotalReward() - item.getTotalPunish();
                            if (score >= 0) {
                                score = 100;
                            } else if (score > -3) {
                                score = 60;
                            } else {
                                score = 0;
                            }
                            performanceReviewEntity.setPerResult(score * v + "");
                            performanceReviewEntity.setPerRemark("子项");
                            this.save(performanceReviewEntity);
                            resultList.add(performanceReviewEntity);
                        }
                    });
                });
            }
            if (Objects.equals(k, "学习情况")) {
                //获取该月的第一天和最后一天
                //获取该月份所有员工的学习情况信息
                List<StudyRecordVo> studyRecordVos = studyRecordService.selectAllStudyTimeByMonth(startTime, endTime);
//                //遍历输出学习情况信息
//                studyRecordVos.forEach(System.out::println);
                //遍历学习情况信息并计算分数
                studyRecordVos.forEach(item -> {
                    list.forEach(item_1 -> {
                        PerformanceReviewEntity performanceReviewEntity = new PerformanceReviewEntity();
                        //给各项属性赋值为0
                        performanceReviewEntity.setPerType(performanceInfoDto.getPerType());
                        performanceReviewEntity.setPerContent(k);
                        performanceReviewEntity.setPerTime(startTime + "至" + endTime);
                        performanceReviewEntity.setPerStaff(item.getStaffId());
                        performanceReviewEntity.setPerResult("");
                        performanceReviewEntity.setPerStaffName(item_1.getStaffName());
                        if (Objects.equals(item_1.getStaffId(), item.getStaffId())) {
                            //生成绩效考核结果表
                            if (item.getTotalTime() >= 15) {
                                performanceReviewEntity.setPerResult(100.0 * v + "");
                            } else if (item.getTotalTime() >= 10) {
                                performanceReviewEntity.setPerResult(70.0 * v + "");
                            } else if (item.getTotalTime() >= 5) {
                                performanceReviewEntity.setPerResult(50.0 * v + "");
                            } else {
                                performanceReviewEntity.setPerResult(40.0 * v + "");
                            }
                            performanceReviewEntity.setPerRemark("子项");
                            this.save(performanceReviewEntity);
                            resultList.add(performanceReviewEntity);
                        }
                    });
                });
            }
            if (Objects.equals(k, "上级评价")) {
                //生成绩效考核结果表
                list.forEach(item -> {
                    PerformanceReviewEntity performanceReviewEntity = new PerformanceReviewEntity();
                    performanceReviewEntity.setPerType(performanceInfoDto.getPerType());
                    performanceReviewEntity.setPerContent(k);
                    performanceReviewEntity.setPerTime(startTime + "至" + endTime);
                    performanceReviewEntity.setPerStaff(item.getStaffId());
                    performanceReviewEntity.setPerStaffName(item.getStaffName());
                    performanceReviewEntity.setPerResult("");
                    performanceReviewEntity.setPerRemark("子项");
                    this.save(performanceReviewEntity);
                    resultList.add(performanceReviewEntity);
                });
            }
        });
        //遍历输出绩效考核结果
//        resultList.forEach(System.out::println);
//        按照员工id进行分组
        List<PerformanceReviewEntity> newList = new ArrayList<>();
        Map<String, List<PerformanceReviewEntity>> map = new HashMap<>();
        resultList.forEach(item -> {
            List<PerformanceReviewEntity> list_1 = map.get(item.getPerStaff());
            if (Objects.isNull(list_1)) {
                list_1 = new ArrayList<>();
                map.put(item.getPerStaff(), list_1);
            }
            list_1.add(item);
        });
        //遍历分组后的绩效考核结果
        map.forEach((k, v) -> {
            double total = 0;
            for (PerformanceReviewEntity item : v) {
                if (!Objects.equals(item.getPerResult(), ""))
                    total += Double.parseDouble(item.getPerResult());
            }
            //生成绩效考核结果表
            PerformanceReviewEntity performanceReviewEntity = new PerformanceReviewEntity();
            performanceReviewEntity.setPerType(performanceInfoDto.getPerType());
            performanceReviewEntity.setPerContent("");
            // performanceInfoDto.getContentWeight() 转为字符串
            int index = 1;
            for (Map.Entry<String, Double> entry : performanceInfoDto.getContentWeight().entrySet()) {
                String content = index + "." + entry.getKey() + ":" + entry.getValue() + "。";
                performanceReviewEntity.setPerContent(performanceReviewEntity.getPerContent() + content);
                index++;
            }
            performanceReviewEntity.setPerTime(startTime + "至" + endTime);
            performanceReviewEntity.setPerStaff(k);
            performanceReviewEntity.setPerStaffName(v.get(0).getPerStaffName());
            performanceReviewEntity.setPerPostName(performanceInfoDto.getPerType());
//            if (total >= 85)
//                performanceReviewEntity.setPerResult("优秀");
//            else if (total >= 75)
//                performanceReviewEntity.setPerResult("良好");
//            else if (total >= 60)
//                performanceReviewEntity.setPerResult("一般");
//            else
//                performanceReviewEntity.setPerResult("较差");
            performanceReviewEntity.setPerResult(total + "");
            performanceReviewEntity.setPerRemark("总分");
            this.save(performanceReviewEntity);
            newList.add(performanceReviewEntity);
//            resultList.add(performanceReviewEntity);
        });
        return newList;
    }

    /**
     * 获取指定职位指定月份的的员工考勤表信息   List<CheckSheetEntity>
     */
    public List<CheckSheetEntity> getCheckSheetByPostName(String postName, String month) {
        List<CheckSheetEntity> list = new ArrayList<>();
        List<StaffUserEntity> staffUserEntities = staffUserService.list(new QueryWrapper<StaffUserEntity>().eq("post_name", postName));
        staffUserEntities.forEach(item -> {
            CheckSheetEntity checkSheetEntity = checkSheetService.getOne(new QueryWrapper<CheckSheetEntity>().like("ch_month", month).eq("staff_id", item.getStaffId()));
            list.add(checkSheetEntity);
        });
        return list;
    }

    /**
     * 获取指定职位指定月份的的员工奖惩次数信息  List<CountRePunInfoVo>
     */
    public List<CountRePunInfoVo> getRePunInfoByPostName(String postName, String month) {
        List<StaffUserEntity> staffUserEntities = staffUserService.list(new QueryWrapper<StaffUserEntity>().eq("post_name", postName));
        List<CountRePunInfoVo> rePunInfoEntities = new ArrayList<>();
        staffUserEntities.forEach(item -> {
            CountRePunInfoVo countRePunInfoVo = new CountRePunInfoVo();
            countRePunInfoVo.setStaffId(item.getStaffId());
            countRePunInfoVo.setTotalRew(rePunInfoMapper.countRePunInfo(month, "红事件", item.getStaffId()));
            countRePunInfoVo.setTotalPun(rePunInfoMapper.countRePunInfo(month, "黑事件", item.getStaffId()));
            if (countRePunInfoVo.getTotalRew() != 0) {
                countRePunInfoVo.setTotalReward(countRePunInfoVo.getTotalRew());
            } else {
                countRePunInfoVo.setTotalReward(0);
            }
            if (countRePunInfoVo.getTotalPun() != 0) {
                countRePunInfoVo.setTotalPunish(countRePunInfoVo.getTotalPun());
            } else {
                countRePunInfoVo.setTotalPunish(0);
            }
            rePunInfoEntities.add(countRePunInfoVo);
        });
        return rePunInfoEntities;
    }

}
