
export default {
  //校验手机号码
  checkPhone(value){
    if (!(/^1[34578]\d{9}$/.test(value))) {
      return false
    }
    return true
  }
}
