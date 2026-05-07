package org.yeh.wang.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountDailyVo {
    String staffId;
    /**
     * 总打卡天数
     */
    Integer totalDay;
    /**
     * 加班时长（后改成总工时）<待定>
     */
    double totalTime;
}
