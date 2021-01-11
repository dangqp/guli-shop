<template>
    <div class="app-container">
        讲师列表
        <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
            </el-form-item>

            <el-form-item>
                <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
                    <el-option :value="1" label="高级讲师"/>
                    <el-option :value="2" label="首席讲师"/>
                </el-select>
            </el-form-item>

            <el-form-item label="添加时间">
                <el-date-picker
                        v-model="teacherQuery.begin"
                        type="datetime"
                        placeholder="选择开始时间"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        default-time="00:00:00"
                />
            </el-form-item>
            <el-form-item>
                <el-date-picker
                        v-model="teacherQuery.end"
                        type="datetime"
                        placeholder="选择截止时间"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        default-time="00:00:00"
                />
            </el-form-item>

            <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
            <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>

        <!-- 表格 -->
        <el-table
                :data="list"
                border
                fit
                highlight-current-row>

            <el-table-column
                    label="序号"
                    width="70"
                    align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column prop="name" label="名称" width="80"/>

            <el-table-column label="头衔" width="80">
                <template slot-scope="scope">
                    {{ scope.row.level===1?'高级讲师':'首席讲师' }}
                </template>
            </el-table-column>

            <el-table-column prop="intro" label="资历"/>

            <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

            <el-table-column prop="sort" label="排序" width="60"/>

            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <router-link :to="'/teacher/edit/'+scope.row.id">
                        <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
                    </router-link>
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination
                :current-page="page"
                :page-size="limit"
                :total="total"
                style="padding: 30px 0; text-align: center;"
                layout="total, prev, pager, next, jumper"
                @current-change="getList"
        />

    </div>

</template>

<script>
    import teacher from '@/api/edu/teacher'

    export default {
        data() { // 定义变量
            return {
                page: 1,
                limit: 5,
                total: 0,
                teacherQuery: {},
                list: null
            }
        },
        created() {
            this.getList();
        },
        methods: {
            //讲师列表
            getList(page=1) {//默认为1
                //debugger
                console.log(page)
                this.page = page
                teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery)
                    .then(res => {
                        console.log(res)
                        console.log(res.code)
                        console.log(res.data.rows)
                        let resCode = res.code;
                        if (resCode === 20000) {
                            this.list = res.data.rows
                            this.total = res.data.total
                            console.log("20000000" + JSON.stringify(this.list))
                        }
                    })
                    .catch(err=>{
                        console.log(err.toString())
                    });
            },
            resetData(){
                //清空查询条件
                this.teacherQuery={}
                // 查询所有数据
                this.getList()
            },
            removeDataById(id){
                this.$confirm('此操作将永久删除该讲师, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    teacher.removeDataById(id).then(res=>{
                        //信息提示
                        let resCode = res.code
                        if (resCode === 20000){
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            //回到页面
                            this.getList()
                        }
                        // request.js 已经处理异常信息
                        // else{
                        //     this.$message({
                        //         type: 'error',
                        //         message: '删除失败!'
                        //     });
                        // }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    }
</script>