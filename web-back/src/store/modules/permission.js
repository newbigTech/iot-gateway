import { routeMap, asyncRouterMap, constantRouterMap } from '@/router'
import { Type } from '../../utils/auth'

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, data) => {
      state.addRouters = data.routers
      state.routers = constantRouterMap.concat(data.routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        let newRouters = routeMap[data.type]
        if (newRouters === undefined) {
          newRouters = asyncRouterMap
        }
        localStorage.setItem(Type, data.type)
        commit('SET_ROUTERS', { routers: newRouters })
        resolve()
      })
    }
  }
}

export default permission
