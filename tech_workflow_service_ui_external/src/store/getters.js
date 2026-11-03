const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device, // 设备
  visitedViews: state => state.tagsView.visitedViews,
  // cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  userSession: state => state.user.session,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  routeMenu: state => state.permission.routeMenu,
  addRoutes: state => state.permission.addRoutes,
  enableSession: state => state.settings.enableSession,
  keepAlive: state => state.settings.keepAlive
}
export default getters
