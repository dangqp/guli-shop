import request from '@/utils/request'

export default {

  // 讲师列表
  getTeacherListPage: function(current, limit, teacherQuery) {
    return request({
      // url: '/eduservice/teacher/pageTeacherCondition/' + current + '/' + limit,
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery 条件对象，后端使用requestbody获取数据
      //data表示把对象转换为json进行传递到接口里面
      data:teacherQuery
    })
  },
    // 删除讲师
    removeDataById:function (id) {
      return request({
          url: `/eduservice/teacher/${id}`,
          method: 'delete'
      });
    },
  // 获取讲师信息
  getTeacherInfo:function (id) {
    return request({
      url: `/eduservice/teacher/getTeacher/${id}`,
      method: 'get'
    });
  },
  updateTeacherInfo:function (teacher) {
    return request({
      url: "/eduservice/teacher/updateTeacher",
      method: 'post',
      data:teacher
    });
  },
  addTeacher:function (teacher) {
    return request({
      url: "/eduservice/teacher/addTeacher",
      method: 'post',
      data: teacher
    });
  }
}

