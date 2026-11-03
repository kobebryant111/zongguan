<template>
  <div>
    <el-select v-model="svalue" :selectOptins.sync="selectOptins" :placeholder="placeholder" :multiple="multiple" :disabled="disabled"  clearable style="width:100%" filterable @change="change">
      <el-option v-for="item in options" :key="item.dictId" :label="item.dictName" :value="item.dictId" />
    </el-select>
  </div>
</template>

<script>
import request from '@/utils/request'
import {getDictName ,getTypeDicts} from '@/utils/dict'

export default {
  name: 'MeSelect',
  props: {
    defaultVal:{
      type:Array,
      default: null
    },
    filters:{
      type: Array,
      default: null
    },
    selectOptins:{
      type: Array,
      default: null
    },
    dicttypeid: {
      type: String,
      default: null
    }, // 条件参数
    value: {
      type: [String, Array,Boolean],
      default: null
    }, // 接受外部v-model传入的值   可能设置为单选或者多选
    placeholder: {
      type: String,
      default: null
    },
    fileType: {
      type: Object,
      default: null
    }, // 定义请求回来的json数据格式
    multiple: { // 是否多选，默认false，非必传。
      type: Boolean,
      default: false
    },
    disabled: { // 是否多选，默认false，非必传。
      type: Boolean,
      default: false
    } 
    
  },
  data() {
    return {
      options: [],
      svalue: []
    }
  },
  watch: {
    // 判断下拉框的值是否有改变
    svalue(val, oldVal) {
      if (val != oldVal) {
        // console.info(this.svalue+'===svalue=='+this.value)
        this.$emit('input', this.svalue)
      }
    },

    value(svalue) { // 接受父组件的 v-mode 双向绑定
      this.svalue = svalue
    }
  },
  mounted() {
    console.info(getTypeDicts(this.dicttypeid),this.dicttypeid+'dddddddddddddddd');
    this.options =getTypeDicts(this.dicttypeid);
    //  console.log("options===",this.options);
    if(this.filters!=null&&this.filters!=undefined&&this.options!=null&&this.options!=undefined){
      let filterArray=this.filters;
      for(var j=0;j<this.options.length;j++){
        for(var i=0;i<filterArray.length;i++){
          if(this.options[j].dictId==filterArray[i]){
           
            this.options.splice(j,1);
          }
        }
      }
        
    }
    this.svalue = this.value
     this._dataTransform(this.options)
  

  },

  methods: {
    _dataTransform(data) {
      if(! (this.fileType !== undefined && this.fileType !== null)) {
        return ;
      }
      const _data = []
      for (let i = 0; i < data.length; i++) {
        _data[i] = {}
        if (this.fileType !== undefined && this.fileType !== null) {
          _data[i].dictName = data[i][this.fileType.label]
          _data[i].dictId = data[i][this.fileType.value]
        }
      }
      this.options = _data
      return _data
    },

    reset() {
      if (this.multiple) {
        this.svalue = []
      } else {
        this.svalue = ''
      }
    },
    setOptions(options){
      this.options= options;
    },
    setDictTypeId(dicttypeid){
      this.options = getTypeDicts(dicttypeid);
      if (this.svalue !== this.value) {
        this.svalue = this.value
      }
    },

    /**
		* 选择框change事件，将所选值和所选值所在的对象回传给父组件
		* @val {String} 当前选中值
		**/
    change(val) {
      let valueKey = 'dictId'
      if (this.optionKey) {
        valueKey = this.optionKey.value
      }
      if (this.multiple) {
        const valObjList = []
        val.forEach(element => {
          const valObj = this.options.find(itemO => itemO[valueKey] === element)
          valObjList.push(valObj)
        })
        this.$emit('input', val)
        this.$emit('change', val, valObjList)
      } else {
        const valOption = this.options.filter(itemO => itemO[valueKey] === val)
        const [valObj] = valOption
        this.$emit('input', val)
        this.$emit('change', val, valObj)
      }
    }
  }
}
</script>

<style scoped>
</style>
