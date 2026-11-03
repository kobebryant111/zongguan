// 路由动态缓存
const keepAlive = {
    state: {
        keepAlives: [],
    },

    mutations: {
        SET_KEEP_ALIVES(state, keepAlives) {
            state.keepAlives = keepAlives
        },
    }
}

export default keepAlive