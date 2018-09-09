<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-date-picker v-model="listQuery.gmtBegin" type="datetime" format="yyyy-MM-dd HH:mm:ss" style="width: 200px;" class="filter-item" placeholder="开始时间">
      </el-date-picker>
      <el-date-picker v-model="listQuery.gmtEnd" type="datetime" format="yyyy-MM-dd HH:mm:ss" style="width: 200px;" class="filter-item" placeholder="结束时间">
      </el-date-picker>
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="角色名称" v-model="listQuery.roleName">
      </el-input>

      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="edit">添加</el-button>
      <!--<el-button class="filter-item" type="primary" icon="document" @click="handleDownload">导出</el-button>-->
    </div>

    <el-table :key='tableKey' :data="list" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">

      <el-table-column prop='id' type='selection' width='45'>
      </el-table-column>

      <el-table-column min-width="70px" prop='roleName' label="角色名称">
      </el-table-column>
      <el-table-column prop='orgName' label='所属组织'>
      </el-table-column>
      <el-table-column prop='gmtCreate' label='创建时间' width='200'>
      </el-table-column>
      <el-table-column prop='gmtModify' label='修改时间' width='200'>
      </el-table-column>
      <el-table-column align="center" label="操作" width="285">
        <template slot-scope="scope">
          <el-button
            size='small'
            type='default'
            icon='edit'
            @click="handleUpdate(scope.row)">编辑
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[20]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" @open="show()">
      <el-form class="small-space" :model="temp" label-position="left" label-width="70px" style='width: 800px; margin-left:50px;'>
        <el-row :gutter="0">
          <el-col :span="12">
            <el-form-item label="角色名称">
              <el-input v-model="temp.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-autocomplete v-model="temp.orgName" :fetch-suggestions="searchOrg" placeholder="请输入部门名称" @select="handleorgSelect" style="width: 200px;">
              </el-autocomplete>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="0">
          <el-col :span="12">
            <el-form-item label="资源权限">
              <el-tree  ref="resourceTree" :data="resourceTree" show-checkbox highlight-current :render-content="renderContent"
                       clearable node-key="id" :default-checked-keys="resourceIds" :props="defaultProps">
              </el-tree>
              <div v-show="!orgLoading" class="pagination-container">
                <el-pagination
                  :page-sizes="[listQuery.limit]" :page-size="listQuery.limit" layout="total, prev, pager, next" :total="orgtotal">
                </el-pagination>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="组织权限">
              <el-tree  ref="orgTree" :data="orgTree" show-checkbox highlight-current :render-content="renderContent"
                        clearable node-key="id" :default-checked-keys="resourceIds" :props="defaultProps">
              </el-tree>
              <div v-show="!orgLoading" class="pagination-container">
                <el-pagination
                  :page-sizes="[listQuery.limit]" :page-size="listQuery.limit" layout="total, prev, pager, next" :total="orgtotal">
                </el-pagination>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
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
import waves from '@/directive/waves/index.js' // 水波纹指令
import * as moment from 'moment'
import request from '@/utils/request'
import selectTree from '@/components/selectTree.vue'
import treeter from '@/components/treeter'
import { fetchSysRoleList, getResourceTree, getSysOrgTree, getSysOrgList } from '../../../api/sysadmin'
export default {
  name: 'role',
  directives: {
    waves
  },
  mixins: [treeter],
  components: {
    'el-select-tree': selectTree
  },
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'name',
        id: 'id'
      },
      list: null,
      total: null,
      orgTree: null,
      orgtotal: null,
      listLoading: true,
      orgLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        roleName: '',
        gmtBegin: '',
        gmtEnd: ''
      },
      temp: {
        id: '',
        name: '',
        orgId: '',
        orgName: '',
        resourceIds: [],
        orgIds: []
      },
      resourceTree: [],
      resourceIds: [],
      orgIds: [],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      tableKey: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /* eslint-disable */
    renderContent(h, { node, data, store }) {
      return (
        <span>
        <span>
        <span> {node.label} </span>
      </span>
      </span>
    )
    },
    getList() {
      this.listLoading = true
      const begin = this.listQuery.gmtBegin === '' ? '' : moment(this.listQuery.gmtBegin).format('YYYY-MM-DD HH:mm:ss')
      const end = this.listQuery.gmtEnd === '' ? '' : moment(this.listQuery.gmtEnd).format('YYYY-MM-DD HH:mm:ss')
      const query = 'name=' + this.listQuery.name + '&gmtBegin=' + begin + '&gmtEnd=' + end +
                    '&pageSize=' + this.listQuery.limit + '&pageNum=' + this.listQuery.page
      fetchSysRoleList(query)
        .then(res => {
          this.list = res.data.result.list
          this.total = res.data.result.total
          this.listLoading = false
        })
    },
    getResourceList() {
      getResourceTree()
        .then(res => {
          this.resourceTree = []
          this.resourceTree.push(...res.data.result.list)
        })
    },
    getSysOrgTree(row) {
      this.orgLoading = true
      getSysOrgTree('')
        .then(res => {
          this.orgTree = res.data.result.list
          this.orgtotal = res.data.result.total
          this.orgLoading = false
          if (row != undefined) {
            setTimeout(() => {
              this.$refs.orgTable.clearSelection()
              this.orgTree.forEach(row => {
                if (this.orgIds.indexOf(parseInt(row.id)) >= 0) {
                  this.$refs.orgTable.toggleRowSelection(row, true);
                }
              });
            }, 0)
          }
        })
    },
    searchOrg(name, cb) {
      const query = 'name='+name
      getSysOrgList(query)
        .then(res => {
          const results = res.data.result.list.map((result) => {
            return { value: result.name, id: result.id }
          })
          cb(results)
        })
    },
    handleorgSelect(item) {
      this.temp.orgId = item.id
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
      this.getResourceList()
      this.getSysOrgTree()
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },
    show() {

    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row)
      this.orgIds = JSON.parse(this.temp.orgIds)
      this.getResourceList()
      this.getSysOrgTree(row)
      this.temp.orgIds = []
      this.temp.orgIds.push(...this.orgIds)
      this.resourceIds = JSON.parse(this.temp.resourceIds)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },
    handleorgIdsChange(vals){
      const tempIds = []
       vals.forEach(val => {
           tempIds.push(val.id)
      });
      this.temp.orgIds = tempIds
    },
    handleDelete(userId, status) {
      this.listLoading = true
      request({
        url: api.SYS_ACCOUNT_DELETE,
        method: 'post',
        data: {
          userId: userId,
          status: status
        }
      })
        .then(res => {
          this.list = res.data.result.list
          this.total = res.data.result.total
          this.listLoading = false
        })
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
      this.temp.resourceIds = this.$refs.resourceTree.getCheckedKeys();
      request({
        url: '/sysRole/add',
        method: 'POST',
        data: this.temp
      }).then(res => {
        if (res.data.code === 200) {
          this.dialogFormVisible = false
          this.getList()
          this.$notify({
            title: '成功',
            message: '创建成功',
            type: 'success',
            duration: 2000
          })
        }
      }).catch(e => {
      })

    },
    update() {
      this.temp.resourceIds = this.$refs.resourceTree.getCheckedKeys();
      request({
        url: '/sysRole/update',
        method: 'POST',
        data: this.temp
      }).then(res => {
        if (res.data.code === 200) {
          this.dialogFormVisible = false
          this.getList()
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000
          })
        }
      }).catch(e => {
      })
    },
    resetTemp() {
      this.temp = {
        id: '',
        name: '',
        orgId: '',
        orgName: '',
        resourceIds: [],
        orgIds: []
      }
    }
  }
}
</script>
