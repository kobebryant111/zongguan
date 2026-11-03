/*
@Author: chenhuocan
@Date: 2020-06-28 11:30:13
@LastEditors:
@LastEditTime:
@Description: 这是一个vue语法模块 / 额度展示
*/

<template>
	<div>
		<ts-multi-table  :options="options">
			<el-table-column prop="id" label="额度类型" width="180">
				<template slot-scope="scope">
					<div @click="spanClick()">{{scope.row.id}}</div>
				</template>
			</el-table-column>
			<el-table-column prop="jobCron" label="额度编号" min-width="180"></el-table-column>
			<el-table-column prop="author" label="授信额度" min-width="180"></el-table-column>
			<el-table-column prop="jobStatus" label="授信额度已用值" min-width="100"></el-table-column>
		</ts-multi-table>
		<div class="ba123"></div>
	</div>
</template>

<script>
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      options: {
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 0, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        layout: 'total, prev, pager, next', // 选填 分页器轮廓
        tbMockData: [
          {
            id: '非专业授信',
            jobCron: 123412341234,
            author: 12341234,
            jobStatus: '123'
          },
          {
            id: '专业授信',
            jobCron: 123412341234,
            author: 12341234,
            jobStatus: '123'
          },
          {
            id: '非授信类额度',
            jobCron: 123412341234,
            author: 12341234,
            jobStatus: '123'
          },
          {
            id: '香港分行额度',
            jobCron: 123412341234,
            author: 12341234,
            jobStatus: '123'
          }
        ]
      }
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    spanClick() {
      console.log('-333')
    }
  }
}
</script>

<style lang=scss scoped>
	.ba123{

		background: linear-gradient(#1f2350,#06070b);
		width: 50px;
		height: 50px;
	}
</style>
