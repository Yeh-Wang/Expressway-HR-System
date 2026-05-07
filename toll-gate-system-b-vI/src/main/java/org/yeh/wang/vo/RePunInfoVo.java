package org.yeh.wang.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RePunInfoVo {
    /**
     * 奖惩编号
     */
    private Integer rewId;

    /**
     * 奖惩内容
     */
    private String rewContent;

    /**
     * 奖惩时间
     */
    private String rewTime;

    /**
     * 奖惩性质（红黑事件）
     */
    private String rewNature;

    /**
     * 员工编号
     */
    private String rewStaff;

    /**
     * 员工姓名
     */
    private String staffName;

}
