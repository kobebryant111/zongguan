<script>
    export default {
        beforeRouteEnter (to, from, next) {
            console.log(from.path)
            next(vm => {
                vm.$router.replace(from.path)
            })
        },
        render: function(h) {
            return h() // avoid warning message
        }
    }
</script>