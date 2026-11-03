<!-- 待办-履职评估 -->
<template>
    <div class="lzpg-container">
        <lzpg-shang @tongji="getTongJi"></lzpg-shang>
    </div>
</template>
<script>
    import lzpgShang from '@/views/bankAssessment/dbKpiPjPgPy/lzpg/lzpgShang';
    export default {
        name: 'dbLzpgEval',
        components: { lzpgShang },
        data() {
            return {

            }
        },
        props: {
            getTongJi: {
                type: Function,
                default: null
            }
        },
    }
</script>
<style scoped>
    .lzpg-container {
        margin-top: 10px;
    }
</style>