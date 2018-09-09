<template>
  <div class="app-container">
    <el-select style='width:150px;' v-model="idType" clearable placeholder="请选择id类型">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <el-input style='width:200px;' placeholder="请输入id" prefix-icon="el-icon-document" v-model="id"></el-input>
    <el-button style='margin-bottom:20px' type="primary" icon="document" @click="handleGetDetail" :loading="downloadLoading">查询</el-button>

    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="活动区域">
        <el-select v-model="form.type" placeholder="选择类型">
          <el-option label="aa" value=2></el-option>
          <el-option label="bb" value=1></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="文件">
        <el-upload
        class="upload-demo"
        action="/"
        ref="upload"
        :multiple="false"
        :limit="1"
        :on-change="dandleOnChange"
        :auto-upload="false"
        :file-list="fileList">
        <el-button slot="trigger" size="small" @click="clearUploadedImage" type="primary">选取文件</el-button>
      </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitFiles()">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>

    <div class="components-container">
      <div class="editor-container">
        <json-editor ref="jsonEditor" v-model="value"></json-editor>
      </div>
    </div>

  </div>
</template>

<script>
  import request from '@/utils/request'
  import JsonEditor from '@/components/JsonEditor'
  export default {
    name: 'query',
    components: { JsonEditor },
    data() {
      return {
        list: null,
        listLoading: true,
        id: '',
        multipleSelection: [],
        downloadLoading: false,
        dialogVisible: false,
        value: '',
        form: {
          //
        },
        file: null,
        fileList: [
          //
        ],
        options: [{
          value: 'id1',
          label: 'id1'
        }, {
          value: 'apId2',
          label: 'id2'
        }, {
          value: 'Id3',
          label: 'id3'
        }],
        idType: ''
      }
    },
    created() {
    },
    methods: {
      clearUploadedImage() {
        this.$refs.upload.clearFiles()
      },
      dandleOnChange(file, fileList) {
        this.file = file
      },
      submitFiles() {
        const formData = new FormData()
        formData.append('file', this.file.raw)
        formData.append('type', this.form.type)
        /*
          Make the request to the POST /multiple-files URL
        */
        request.post('/meta/',
          formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        ).then(function() {
          console.log('SUCCESS!!')
        })
          .catch(function() {
            console.log('FAILURE!!')
          })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      },
      handleGetDetail() {
        if (this.idType === '') {
          this.$message({
            type: 'warning',
            message: '请选择id类型'
          })
        } else if (this.id === '') {
          this.$message({
            type: 'warning',
            message: '请输入id'
          })
        } else {
          // request.get('')
          //   .then(res => {
          //     this.value = res.data
          //   })
          this.value = 'res.data'
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      }
    }
  }
</script>

<style scoped>
.editor-container{
  position: relative;
  height: 100%;
}
</style>
