package org.yeh.wang.vo;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountRePunInfoVo {
    String staffId;
    /**
     * 总奖次数
     */
    Integer totalRew;
    /**
     * 总惩次数
     */
    Integer totalPun;
    /**
     * 总奖励分
     */
    Integer totalReward;
    /**
     * 总惩罚分
     */
    Integer totalPunish;
}
