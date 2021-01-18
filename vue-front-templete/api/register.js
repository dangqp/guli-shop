import request from '@/utils/request'

export default {
    //根据手机号发验证码
  sendCode(phone) {
    return request({
      // url: `/edumsm/msm/send/${phone}`,
      url: `/edumsm/msm/sendMsmTest/${phone}`, //测试用，直接保存Redis不发大鱼
      method: 'get'
    })
  },

  //注册的方法
  registerMember(formItem) {
    return request({
      url: `/ucenter/member/register`,
      method: 'post',
      data: formItem
    })
  }

}
