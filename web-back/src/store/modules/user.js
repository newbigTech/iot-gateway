import { loginByUsername } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
// import request from '@/utils/request'

const user = {
  state: {
    user: '',
    status: '',
    code: '',
    token: getToken(),
    name: '',
    avatar: '',
    introduction: '',
    roles: [],
    setting: {
      articlePlatform: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_INTRODUCTION: (state, introduction) => {
      state.introduction = introduction
    },
    SET_SETTING: (state, setting) => {
      state.setting = setting
    },
    SET_STATUS: (state, status) => {
      state.status = status
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 用户名登录
    LoginByMobile({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        // commit('SET_TOKEN', 'data.token')
        // setToken('data.token')
        // resolve()
        console.log(userInfo)
        loginByUsername(userInfo.mobile.trim(), userInfo.password.trim()).then(response => {
          const data = response.data
          commit('SET_TOKEN', data.result)
          setToken(data.result)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        this.avatarImage = `https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif`
        commit('SET_ROLES', ['admin'])
        commit('SET_NAME', 'name')
        commit('SET_AVATAR', this.avatarImage)
        commit('SET_INTRODUCTION', 'data.introduction')
        resolve()
        // request({
        //   url: 'http://127.0.0.1:8089/getcurrentUserInfo',
        //   method: 'GET',
        //   withCredentials: true
        // })
        //   .then(response => {
        //     if (!response.data) { // 由于mockjs 不支持自定义状态码只能这样hack
        //       reject('error')
        //     }
        //     const data = response.data
        //     this.avatarImage = `https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif`
        //     commit('SET_ROLES', ['admin'])
        //     commit('SET_NAME', data.name)
        //     commit('SET_AVATAR', this.avatarImage)
        //     commit('SET_INTRODUCTION', 'data.introduction')
        //     resolve(response)
        //   }).catch(error => {
        //     reject(error)
        //   })
      })
    },

    // 第三方验证登录
    // LoginByThirdparty({ commit, state }, code) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_CODE', code)
    //     loginByThirdparty(state.status, state.email, state.code).then(response => {
    //       commit('SET_TOKEN', response.data.token)
    //       setToken(response.data.token)
    //       resolve()
    //     }).catch(error => {
    //       reject(error)
    //     })
    //   })
    // },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resolve()
        // logout(state.token).then(() => {
        //   commit('SET_TOKEN', '')
        //   commit('SET_ROLES', [])
        //   removeToken()
        //   resolve()
        // }).catch(error => {
        //   reject(error)
        // })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    },

    // 动态修改权限
    ChangeRoles({ commit }, role) {
      return new Promise(resolve => {
        commit('SET_TOKEN', role)
        setToken(role)
      })
    }
  }
}

export default user
