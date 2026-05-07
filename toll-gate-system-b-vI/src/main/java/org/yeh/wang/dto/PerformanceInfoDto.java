package org.yeh.wang.dto;

import lombok.*;
import org.yeh.wang.utils.pairs;

import java.util.List;
import java.util.Map;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceInfoDto {
    /**
     * 绩效考核内容及权重容器
     */
    private Map<String,Double> contentWeight;
    /**
     * 绩效考核对象
     */
    private String perType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
