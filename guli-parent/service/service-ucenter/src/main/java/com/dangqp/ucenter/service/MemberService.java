package com.dangqp.ucenter.service;

import com.dangqp.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dangqp.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author dangqp
 * @since 2021-01-16
 */
public interface MemberService extends IService<Member> {
    //登录的方法
    String login(Member member);

    //注册的方法
    void register(RegisterVo registerVo);

    //根据openid判断
    Member getOpenIdMember(String openid);

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
