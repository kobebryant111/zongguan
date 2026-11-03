<template>
    <!-- <el-table  :row-style="showRow" :data="formatData" v-bind="$attrs" highlight-current-row stripe> -->
    <!-- bin改 -->
    <el-table :cell-style="showRow" :data="formatData" v-bind="$attrs" highlight-current-row stripe>
        <el-table-column v-for="(item) in columns" :key="item.value" :label="item.text" :width="item.width">
            <template slot-scope="scope">
                <span :class="['level' + scope.row._level]">
                    <i v-if="scope.row.children" @click="toggleExpanded(scope.$index)" :class="[scope.row._expanded ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                </span>
                {{scope.row[item.value]}}
            </template>
        </el-table-column>
        <slot/>
    </el-table>
    <!-- bin -->
    <!-- <el-table :data="formatData" row-key="routeid" v-bind="$attrs" default-expand-all :tree-props="{children: 'children'}" highlight-current-row stripe>
            <el-table-column v-for="(item) in columns" :key="item.value" :label="item.text" :width="item.width" >
               
            </el-table-column>
        </el-table> -->
</template>
<script>
    import Vue from 'vue';
    import commonUtils from '@/api/common'
    export default {
        name: 'tableTree',
        props: {
            data: [Array, Object],
            columns: {
                type: Array,
                default: () => []
            },
            expandAll: {
                type: Boolean,
                default: () => false
            }
        },
        created() {

        },
        computed: {
            formatData: function () {
                let newData = [];
                if (!Array.isArray(this.data)) {
                    newData = [this.data];
                } else {
                    newData = this.data;
                }
                return this.arrayToTree(newData, this.expandAll);;
            }
        },
        mounted() {
            // this.init();
        },
        methods: {
            // 数组转树
            arrayToTree: function (data, expandAll, parent = null, level = null) {
                let arrays = []
                let _this = this;
                Array.from(data).forEach(function (item) {
                    // 判断展开属性
                    if (!item._expanded) {
                        Vue.set(item, '_expanded', expandAll);
                    }
                    // 判断层级
                    let _level = 1;
                    if (level) {
                        _level = level + 1;
                    }
                    Vue.set(item, '_level', _level)
                    // 是否有父类
                    if (parent) {
                        Vue.set(item, 'parent', parent)
                    }
                    arrays.push(item)
                    // 是否存在子节点
                    if (item.children && item.children.length > 0) {
                        let children = _this.arrayToTree(item.children, expandAll, item, _level)
                        arrays = arrays.concat(children);
                    }
                })
                return arrays;
            },
            // 树形菜单
            showRow: function (rowVal) {
                const show = rowVal.row.parent ? (rowVal.row.parent._expanded && rowVal.row.parent._show) : true;
                rowVal.row._show = show;
                // console.log("树形菜单", rowVal.row.parent, rowVal);
                return show ? 'animation:treeTableShow 1s;-webkit-animation:treeTbaleShow 1s;' : 'display:none';
            },
            // 切换下级是否展开
            toggleExpanded(trIndex) {
                let row = this.formatData[trIndex]
                console.log("切换下级是否展开", trIndex, row);
                row._expanded = !row._expanded
            },

        }
    };
</script>
<style scoped>
    .level1,
    .level2,
    .level3,
    .level4 {
        display: inline-block;
        width: 20px;
    }

    .level1 {
        margin-left: 5px;
    }

    .level2 {
        margin-left: 20px;
    }

    .level3 {
        margin-left: 35px
    }

    .level4 {
        margin-left: 50px
    }
</style>