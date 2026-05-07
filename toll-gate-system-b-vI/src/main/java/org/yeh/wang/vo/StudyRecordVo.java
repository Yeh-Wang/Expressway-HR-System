package org.yeh.wang.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudyRecordVo {
    private double TotalTime;
    private String StartTime;
    private String EndTime;
    private String staffId;
}
