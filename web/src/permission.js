import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import '@/components/NProgress/nprogress.less' // progress bar custom style
// import notification from 'ant-design-vue/es/notification'
// import { setDocumentTitle, domTitle } from '@/utils/domUtil'
import { ACCESS_TOKEN } from '@/store/mutation-types'
// import { i18nRender } from '@/locales'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['login', 'register', 'registerResult', 'activePassword', 'change'] // no redirect whitelist
const loginRoutePath = '/user/login'
const defaultRoutePath = '/dashboard/workplace'
// 路由前置守卫
router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  // to.meta && (typeof to.meta.title !== 'undefined' && setDocumentTitle(`${i18nRender(to.meta.title)} - ${domTitle}`))
  /* has token */
  if (sessionStorage.getItem(ACCESS_TOKEN)) {
    if (to.path === loginRoutePath) {
      next({ path: defaultRoutePath })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        store.dispatch('GetInfo').then(res => {
          // 添加路由
          store.dispatch('GenerateRoutes').then(() => {
            // 动态添加可访问路由表
            router.addRoutes(store.getters.addRouters)

            // 全部重定向到首页
            next({ path: '/dashboard/workplace' })

            // 请求带有 redirect 重定向时，登录自动重定向到该地址
            // const redirect = decodeURIComponent(from.query.redirect || to.path)
            // if (to.path === redirect) {
            //   // set the replace: true so the navigation will not leave a history record
            //   next({ ...to, replace: true })
            // } else {
            //   // 跳转到目的路由
            //   next({ path: redirect })
            // }
          })
        })
          .catch(() => {
            // notification.error({
            //   message: '错误',
            //   description: '请求用户信息失败，请重试'
            // })
            // 失败时，获取用户信息失败时，调用登出，来清空历史保留信息
            store.dispatch('Logout').then(() => {
              // next({ path: loginRoutePath, query: { redirect: to.fullPath } })
            })
            next({ path: loginRoutePath, query: { redirect: to.fullPath } })
          })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.name)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next({ path: loginRoutePath, query: { redirect: to.fullPath } })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
