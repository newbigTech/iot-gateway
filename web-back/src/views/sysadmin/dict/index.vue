<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="类别" v-model="listQuery.category">
      </el-input>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="keyName" v-model="listQuery.keyName">
      </el-input>

      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="edit">添加</el-button>
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">

      <el-table-column prop='id' type='selection' width='45'>
      </el-table-column>

      <el-table-column min-width="70px" prop='category' label="类别">
      </el-table-column>
      <el-table-column prop='keyName' label='键'>
      </el-table-column>
      <el-table-column prop='value' label='值'>
      </el-table-column>
      <el-table-column prop='sort' label='排序'>
      </el-table-column>
      <el-table-column prop='gmtCreate' label='创建时间'>
      </el-table-column>
      <el-table-column prop='gmtModify' label='修改时间'>
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
            type='danger'
            icon='setting'
            @click='handleDelete(scope.row)'>删除
          </el-button>
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

        <el-form-item label="类别">
          <el-input v-model="temp.category"></el-input>
        </el-form-item>
        <el-form-item label="name">
          <el-input v-model="temp.keyName"></el-input>
        </el-form-item>
        <el-form-item label="value">
          <el-input v-model="temp.value"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-slider v-model='temp.sort'></el-slider>
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
  import { addSysDict, fetchSysDictList, updateSysDict, deleteSysDict } from '../../../api/sysadmin'

  export default {
    name: 'dict',
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
          category: '',
          keyName: ''
        },
        temp: {
          id: '',
          category: '',
          keyName: '',
          value: '',
          sort: 0
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '编辑',
          create: '创建'
        },
        tableKey: 0
      }
    },
    filters: {
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        const query = 'category=' + this.listQuery.category + '&keyName=' + this.listQuery.keyName +
                      '&pageSize=' + this.listQuery.limit + '&pageNum=' + this.listQuery.page
        fetchSysDictList(query)
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
      handleDelete(row) {
        this.listLoading = true
        deleteSysDict(row.id)
          .then(res => {
            if (res.data.code === 200) {
              this.getList()
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
            }
          })
      },
      create() {
        addSysDict(this.temp)
          .then(res => {
            if (res.data.code === 200) {
              this.getList()
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
            }
          })
      },
      update() {
        updateSysDict(this.temp)
          .then(res => {
            if (res.data.code === 200) {
              this.getList()
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            }
          })
      },
      resetTemp() {
        this.temp = {
          id: '',
          category: '',
          keyName: '',
          value: '',
          sort: 0
        }
      }
    }
  }
</script>
