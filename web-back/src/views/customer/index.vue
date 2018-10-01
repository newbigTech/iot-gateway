<template>
  <div class="app-container">

  </div>
</template>

<script>
  import request from '@/utils/request'
  import JsonEditor from '@/components/JsonEditor'
  import { getToken } from '@/utils/auth'

  export default {
    name: 'customer',
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
        immsg: {
          cmd: 0,
          data: '',
          proto: 0
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
    mounted() {
      console.log(this)
      this.initWebsocket()
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
      initWebsocket() {
        const wsuri = 'ws://127.0.0.1:8890/ws'
        this.websock = new WebSocket(wsuri)
        this.websock.onopen = this.websocketopen
        this.websock.onmessage = this.websocketonmessage
        this.websock.onclose = this.websocketclose
        this.websock.onerror = this.websocketerror
      },
      websocketopen() {
        console.log('WebSocket连接成功')
        var a = {
          type: 0,
          id: 12345678,
          token: getToken()
        }
        var b = {
          clientId: '1111111',
          cmd: 1,
          data: JSON.stringify(a)
        }
        this.websock.send(JSON.stringify(b))
      },
      websocketonmessage(e) {
        this.immsg = JSON.parse(e.data)
        if (this.immsg && this.immsg.cmd === 11) {
          const a = JSON.parse(this.immsg.data)
          if (!a.success) {
            console.log('connect failed ', a.msg)
          } else {
            console.log('connect success')
          }
        } else {
          console.log('connect success')
        }
      },
      websocketclose() {
        console.log('WebSocket关闭')
      },
      websocketerror() {
        console.log('WebSocket连接失败')
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
