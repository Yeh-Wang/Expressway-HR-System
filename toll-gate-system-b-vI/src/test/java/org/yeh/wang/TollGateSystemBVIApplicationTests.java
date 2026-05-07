package org.yeh.wang;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.yeh.wang.entity.DailyPunchEntity;
import org.yeh.wang.entity.PublicNoticeEntity;
import org.yeh.wang.entity.StaffScheduleEntity;
import org.yeh.wang.entity.UserInfoEntity;
import org.yeh.wang.mapper.ApplyFeedbackMapper;
import org.yeh.wang.mapper.DailyPunchMapper;
import org.yeh.wang.mapper.RePunInfoMapper;
import org.yeh.wang.mapper.UserInfoMapper;
import org.yeh.wang.service.impl.*;
import org.yeh.wang.utils.UtilsCollect;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class TollGateSystemBVIApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Autowired
    private RePunInfoMapper rePunInfoMapper;

    @Autowired
    private ApplyFeedbackMapper applyFeedbackMapper;

    @Autowired
    private DailyPunchMapper dailyPunchMapper;

    @Autowired
    private CheckSheetServiceImpl checkSheetService;

    @Autowired
    private DailyPunchServiceImpl dailyPunchService;

    @Autowired
    private StaffScheduleServiceImpl staffScheduleService;
    @Autowired
    private ApplyFeedbackServiceImpl applyFeedbackService;


    @Test
    void test() {
//        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("test", UUID.randomUUID().toString());
//        Date start = DateUtil.parse("2024-04-26 08:00:00");
//        Date end = DateUtil.parse("2024-04-25 17:00:00");
//        if (DateUtil.isSameDay(start, end)) {
//            System.out.println("是同一天");
//        } else {
//            System.out.println("不是同一天");
//        }
//        float between = DateUtil.between(start, end, DateUnit.HOUR);
//        PublicNoticeEntity publicNoticeEntity = new PublicNoticeEntity();
//        publicNoticeEntity.setPubCreateName(Optional.of("admin"));
//        System.out.println(publicNoticeEntity.getPubCreateName().isPresent()?publicNoticeEntity.getPubCreateName().get():"");
        // 模拟一个日期集合，用于分配班次，假设有五天要安排班次
//        List<String> dates = Arrays.asList("2023-10-11", "2023-10-12", "2023-10-13", "2023-10-14", "2023-10-15", "2023-10-16", "2023-10-17");
//        staffScheduleService.autoSchedule(dates);
//        System.out.println(applyFeedbackService.isApply("07e6c6554af04b6b8c6329d09391fd60", "2023-10-29"));
//        String startTime = DateUtil.beginOfMonth(DateUtil.parse("2024-08-05")).toString();
//        String endTime = DateUtil.endOfMonth(DateUtil.parse("2024-08-05")).toString();
//        System.out.println(startTime+":"+ endTime);
    }

}
