package org.yeh.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.UserInfoEntity;
import org.yeh.wang.entity.UserRoleViewEntity;
import org.yeh.wang.mapper.StaffInfoMapper;
import org.yeh.wang.mapper.UserInfoMapper;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.utils.UtilsCollect;
import org.yeh.wang.vo.LoginVo;

import java.nio.file.Watchable;
import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 账户表; 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserInfoService {

    private UserInfoMapper userInfoMapper;
    private StaffInfoMapper staffInfoMapper;
    private UserRoleViewServiceImpl userRoleService;

    @Autowired
    public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }
    @Autowired
    public void setStaffInfoMapper(StaffInfoMapper staffInfoMapper) {
        this.staffInfoMapper = staffInfoMapper;
    }
    @Autowired
    public void setUserRoleService(UserRoleViewServiceImpl userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public boolean add(UserInfoEntity userInfoEntity) {
        return userInfoMapper.insert(userInfoEntity) > 0;
    }

    //用户名密码登录返回带有Token和权限的对象
    @Override
    public Result<?> login(String username, String password) {
        if (Objects.equals(username, "") || Objects.equals(password, "")) {
            return new Result<>(ResultEnum.ERROR.getCode(), "用户名或密码不能为空", null);
        }
        //根据用户名查询登录用户信息
        UserInfoEntity userInfoEntity = userInfoMapper.selectByUserName(username);
        if (userInfoEntity == null) {
            return new Result<>(ResultEnum.ERROR.getCode(), "用户不存在", null);
        } else {
            if (Objects.equals(password, userInfoEntity.getUserPwd())) {
                //根据职员id查询登录职员信息
                StaffInfoEntity staffInfoEntity = staffInfoMapper.selectById(userInfoEntity.getStaffId());
                //根据用户id获取角色信息
                List<UserRoleViewEntity> list = userRoleService.list(new QueryWrapper<UserRoleViewEntity>().eq("user_id", userInfoEntity.getUserId()));
                //将list中每个对象的角色信息赋值给数组
                String[] roles = new String[list.size()];
                list.forEach(userRoleViewEntity -> {
                    roles[list.indexOf(userRoleViewEntity)] = userRoleViewEntity.getRoleName();
                });
                //生成token
                String token = UtilsCollect.createToken(username,24*60*60);  //24小时
                LoginVo loginVo = new LoginVo(userInfoEntity.getUserId(),staffInfoEntity.getStaffId(),staffInfoEntity.getStaffAvatar(),userInfoEntity.getUserNickname(),staffInfoEntity.getStaffName(),roles,token);
                loginVo.setAvatar(staffInfoEntity.getStaffAvatar());
                return new Result<>(ResultEnum.SUCCESS.getCode(), "登录成功", loginVo);
            } else {
                return new Result<>(ResultEnum.ERROR.getCode(), "密码错误", null);
            }
        }
    }

    @Override
    public boolean isExist(String username) {
        return this.exists(new QueryWrapper<UserInfoEntity>().eq("user_nickname",username));
    }
}
