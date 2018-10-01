<template>
  <el-menu class="navbar" mode="horizontal">
    <h3 class="left-menu">后台管理系统</h3>
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>
    <!--<breadcrumb class="breadcrumb-container"></breadcrumb>-->
    <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect"
             style="display: inline-block; margin-top: 0px; margin-left: 50px">
      <el-menu-item style="display: inline-block;" index="0">首页</el-menu-item>
      <el-menu-item style="display: inline-block;" index="13">客服中心</el-menu-item>
      <el-menu-item style="display: inline-block;" index="12">系统设置</el-menu-item>
    </el-menu>
    <div class="right-menu">
      <!--<error-log class="errLog-container right-menu-item"></error-log>-->

      <el-tooltip effect="dark" content='全屏' placement="bottom">
        <screenfull class="screenfull right-menu-item"></screenfull>
      </el-tooltip>

      <el-tooltip effect="dark" content='主题' placement="bottom">
        <theme-picker class="theme-switch right-menu-item"></theme-picker>
      </el-tooltip>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <img class="user-avatar" :src="this.avatarImage">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import ThemePicker from '@/components/ThemePicker'
import store from '@/store'
import router from '@/router'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'
import { Type } from '@/utils/auth'
// progress bar style

NProgress.configure({ showSpinner: false })// NProgress Configuration
export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    ThemePicker
  },
  data() {
    const type = localStorage.getItem(Type)
    return {
      userName: '',
      activeIndex: type === null ? '1' : type,
      activeIndex2: '1',
      avatarImage: ''
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name'
    ])
  },
  mounted() {
    this.avatarImage = store.getters.avatar
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    handleSelect(key, keyPath) {
      // 重新生成路由
      store.dispatch('GenerateRoutes', { type: key }).then(() => { // 生成可访问的路由表
        // router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
      })
      router.push({ path: '/dashboard' })
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .left-menu {
    float: left;
    height: 100%;
    display: inline-block;
    margin: 0 8px;
    &:focus {
      outline: none;
    }
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
