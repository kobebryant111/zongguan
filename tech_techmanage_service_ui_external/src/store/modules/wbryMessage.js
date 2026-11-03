import store from '@/store'

const wbryMessage = {
    state: {
        learnExperience: [],
        mainJobs: [],
        mainItem: [],
        mainReward: [],
        mainFamily: [],
    },

    mutations: {
        SET_learnExperience(state, learn) {
            state.learnExperience = learn
        },
        SET_mainJobs(state, mainJobs) {
            state.mainJobs = mainJobs
        },
        SET_mainItem(state, mainItem) {
            state.mainItem = mainItem
        },
        SET_mainReward(state, mainReward) {
            state.mainReward = mainReward
        },
        SET_mainFamily(state, mainFamily) {
            state.mainFamily = mainFamily
        },
    }
}

export default wbryMessage