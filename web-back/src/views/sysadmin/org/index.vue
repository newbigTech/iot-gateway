<template>
  <imp-panel>
    <h3 class='box-title' slot='header' style='width: 100%;'>
      <el-button type='primary' icon='plus' @click='newAdd'>新增</el-button>
    </h3>
    <el-row slot='body' style='margin-bottom: 20px;' :gutter='24'>
      <el-col :span='12' :xs='24' :sm='24' :md='6' :lg='6' style='margin-bottom: 20px;'>
        <el-input v-model='listQuery.name' auto-complete='off'></el-input>
        <!--<div v-show="1" class="pagination-container">-->
          <!--<el-pagination @size-change="" @current-change="" :current-page.sync="listQuery.page"-->
                         <!--:page-sizes="[10]" :page-size="listQuery.limit" layout="total, prev, next" :total="this.total">-->
          <!--</el-pagination>-->
        <!--</div>-->
        <el-tree v-if="resourceTree" ref="resourceTree" :data="resourceTree" show-checkbox highlight-current :render-content="renderContent"
                 default-expand-all
                 @node-click="handleNodeClick" clearable node-key="id" :props="defaultProps">
        </el-tree>
      </el-col>
      <el-col :span='12' :xs='24' :sm='24' :md='18' :lg='18'>
        <el-card class='box-card'>
          <div class='text item'>
            <el-form :model='form' ref='form'>
              <el-form-item label='父级' :label-width='formLabelWidth'>
                <el-select-tree v-model='form.parentId' :treeData='resourceTree' :propNames='defaultProps' clearable
                                placeholder='请选择父级'>
                </el-select-tree>
              </el-form-item>
              <el-form-item label='名称' :label-width='formLabelWidth'>
                <el-input v-model='form.name' auto-complete='off'></el-input>
              </el-form-item>
              <el-form-item label='' :label-width='formLabelWidth'>
                <el-button type='primary' @click='onSubmit' v-text="form.id ? '修改':'新增'"></el-button>
                <el-button type='danger' @click='deleteSelected' icon='delete' v-show='form.id && form.id!=null'>删除
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </imp-panel>

</template>
<script>
  import panel from '@/components/panel.vue'
  import selectTree from '@/components/selectTree.vue'
  import treeter from '@/components/treeter'
  import merge from 'element-ui/src/utils/merge'
  import { getSysOrgTree, addSysOrg, updateSysOrg } from '../../../api/sysadmin'

  export default {
    mixins: [treeter],
    components: {
      'imp-panel': panel,
      'el-select-tree': selectTree
    },
    data() {
      return {
        formLabelWidth: '200px',
        list: null,
        total: null,
        defaultProps: {
          children: 'children',
          label: 'name',
          id: 'id'
        },
        listQuery: {
          page: 1,
          limit: 20,
          name: '',
          mobile: '',
          status: ''
        },
        resourceTree: [],
        form: {
          id: null,
          parentId: null,
          name: '',
          code: '',
          type: 1,
          sort: 0,
          usable: '1',
          remarks: ''
        }
      }
    },
    methods: {
      newAdd() {
        this.form = {
          id: null,
          parentId: null,
          name: '',
          code: '',
          type: 1,
          sort: 0,
          usable: '1',
          remarks: ''
        }
      },
      /* eslint-disable */
      renderContent(h, { node, data, store }) {
        return (<span> <span> <span> {node.label} </span> </span> </span>)
      },
      /* eslint-disable */

      deleteSelected() {
        deleteSysOrg(this.form.id)
          .then(res => {
            if (res.data.code === 200) {
              this.getTree()
              this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 2000
              })
            }
          }).catch(e => {
        })
      },
      handleNodeClick(data) {
        this.form = merge({}, data)
      },
      onSubmit() {
        if (this.form.id == null) {
          addSysOrg(this.form)
            .then(res => {
              if (res.data.code === 200) {
                this.getTree()
                this.$notify({
                  title: '成功',
                  message: '添加成功',
                  type: 'success',
                  duration: 2000
                })
              }
            }).catch(e => {

          })
        } else {
          updateSysOrg(this.form)
            .then(res => {
              if (res.data.code === 200) {
                this.getTree()
                this.$notify({
                  title: '成功',
                  message: '更新成功',
                  type: 'success',
                  duration: 2000
                })
              }
            }).catch(e => {
          })
        }
      },
      getTree() {
        const query = ''
        getSysOrgTree(query)
          .then(res => {
            this.resourceTree = []
            this.resourceTree.push(...res.data.result.list)
            this.total = res.data.result.total
          }).catch((error) => {
          console.log(error)
        })
      }
    },
    created() {
      this.getTree()
    }
  }
</script>

<style>
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: '';
  }

  .clearfix:after {
    clear: both
  }

</style>
