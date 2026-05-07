import { staffInfo } from "../staffManage/type";

/**
 * UserInfo
 */
export type UserInfo = {
    /**
     * 职员编号
     */
    staffId?: string;
    /**
     * 用户编号
     */
    userId: string;
    /**
     * 用户昵称
     */
    userNickname: string;
    /**
     * 用户密码
     */
    userPwd: string;
}
