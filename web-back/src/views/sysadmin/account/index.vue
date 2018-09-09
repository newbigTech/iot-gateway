<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="姓名" v-model="listQuery.name">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="手机号" v-model="listQuery.mobile">
      </el-input>

      <el-select clearable class="filter-item" style="width: 130px" v-model="listQuery.status" placeholder="状态">
        <el-option v-for="item in  statusOption" :key="item.key" :label="item.display_name+'('+item.key+')'" :value="item.key">
        </el-option>
      </el-select>

      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="edit">添加</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">

      <el-table-column prop='id' type='selection' width='45'>
      </el-table-column>

      <el-table-column min-width="70px" prop='name' label="姓名">
      </el-table-column>
      <el-table-column prop='mobile' label='手机号'>
      </el-table-column>
      <el-table-column prop='job' label='职位'>
      </el-table-column>
      <el-table-column prop='address' label='地址'>
      </el-table-column>
      <el-table-column prop='avatar' label='头像' width="80">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" width="50" height="50"/>
        </template>
      </el-table-column>
      <el-table-column prop='status' label='状态' width='100'>
        <template slot-scope="scope">
          <p v-if = 'scope.row.status == 0'>正常</p>
          <p v-if = 'scope.row.status == 1'>已禁用</p>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="285">
        <template slot-scope="scope">
          <el-button
            size='small'
            type='default'
            icon='edit'
            @click="handleUpdate(scope.row)">编辑
          </el-button>
          <el-button
            size='small'
            type='info'
            icon='setting'
            @click='handleRoleConfig(scope.$index, scope.row)'>配置角色
          </el-button>
          <!--<el-button-->
            <!--size='small'-->
            <!--type='danger'-->
            <!--@click='handleDelete(scope.row.userId, 1-scope.row.status)'>-->
            <!--<a v-if = 'scope.row.status == 0'>禁用</a>-->
            <!--<a v-if = 'scope.row.status == 1'>启用</a>-->
          <!--</el-button>-->
        </template>
      </el-table-column>

    </el-table>

    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[20]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="temp" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>

        <el-form-item label="姓名">
          <el-input v-model="temp.name"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="temp.mobile"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="temp.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="temp.job" ></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="temp.address" ></el-input>
        </el-form-item>
        <!--<el-form-item label="头像">-->
          <!--<template>-->
            <!--<img :src="temp.avatar" width="50" height="50"/>-->
          <!--</template>-->
        <!--</el-form-item>-->
        <el-form-item label="状态">
          <el-select class="filter-item" v-model="temp.status" placeholder="请选择">
            <el-option v-for="item in  statusOption" :key="item.key" :label="item.display_name" :value="item.key">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create">确 定</el-button>
        <el-button v-else type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves/index.js'
import { fetchSysUserList } from '../../../api/sysadmin'

const statusOption = [
  { key: '1', display_name: '禁用' },
  { key: '0', display_name: '正常' }
]

// arr to obj
const statusKeyValue = statusOption.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'account',
  directives: {
    waves
  },
  data() {
    return {
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: '',
        mobile: '',
        status: ''
      },
      temp: {
        id: '',
        name: '',
        mobile: '',
        job: '',
        password: '',
        address: '',
        status: ''
      },
      importanceOptions: [1, 2, 3],
      statusOption,
      sortOptions: [{ label: '按ID升序列', key: '+id' }, { label: '按ID降序', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      dialogPvVisible: false,
      pvData: [],
      showAuditor: false,
      tableKey: 0
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return statusKeyValue[type]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      const query = 'name=' + this.listQuery.name + '&mobile=' + this.listQuery.mobile +
        '&status=' + this.listQuery.status + '&pageSize=' + this.listQuery.limit + '&pageNum=' + this.listQuery.page
      fetchSysUserList(query)
        .then(res => {
          this.list = res.data.result.list
          this.total = res.data.result.total
          this.listLoading = false
        })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    timeFilter(time) {
      if (!time[0]) {
        this.listQuery.start = undefined
        this.listQuery.end = undefined
        return
      }
      this.listQuery.start = parseInt(+time[0] / 1000)
      this.listQuery.end = parseInt((+time[1] + 3600 * 1000 * 24) / 1000)
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    handleDelete(userId, status) {
      this.listLoading = true
      // fetch({
      //   url: api.SYS_ACCOUNT_DELETE,
      //   method: 'post',
      //   data: {
      //     userId: userId,
      //     status: status
      //   }
      // })
      //   .then(res => {
      //     this.list = res.data.result.list
      //     this.total = res.data.result.total
      //     this.listLoading = false
      //   })
      this.$notify({
        title: '成功',
        message: '删除成功',
        type: 'success',
        duration: 200
      })
      const index = this.list.indexOf(userId)
      this.list.splice(index, 1)
    },
    create() {
      this.list.unshift(this.temp)
      // fetch({
      //   url: api.SYS_ACCOUNT_ADD,
      //   method: 'post',
      //   data: this.temp
      // })
      //   .then(res => {
      //     this.list = res.data.result.list
      //     this.total = res.data.result.total
      //     this.listLoading = false
      //   })
      this.dialogFormVisible = false
      this.$notify({
        title: '成功',
        message: '创建成功',
        type: 'success',
        duration: 2000
      })
    },
    update() {
      for (const v of this.list) {
        if (v.id === this.temp.id) {
          const index = this.list.indexOf(v)
          this.list.splice(index, 1, this.temp)
          break
        }
      }
      // fetch({
      //   url: api.SYS_ACCOUNT_UPDATE,
      //   method: 'post',
      //   data: this.temp
      // })
      //   .then(res => {
      //     this.list = res.data.result.list
      //     this.total = res.data.result.total
      //     this.listLoading = false
      //   })
      this.dialogFormVisible = false
      this.$notify({
        title: '成功',
        message: '更新成功',
        type: 'success',
        duration: 2000
      })
    },
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        mobile: '',
        email: '',
        password: '',
        status: '0'
      }
    },
    handleDownload() {
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        return v[j]
      }))
    }
  }
}
</script>
