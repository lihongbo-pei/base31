<template>
  <a-radio-group v-if="tagType=='radio'" @change="handleInput" :value="getValueSting" :disabled="disabled">
    <a-radio v-for="(item, key) in dictOptions" :key="key" :value="item.value">{{ item.text }}</a-radio>
  </a-radio-group>

  <a-radio-group v-else-if="tagType=='radioButton'" buttonStyle="solid" @change="handleInput" :value="getValueSting" :disabled="disabled">
    <a-radio-button v-for="(item, key) in dictOptions" :key="key" :value="item.value">{{ item.text }}</a-radio-button>
  </a-radio-group>

  <a-select
    v-else-if="tagType=='select'"
    :getPopupContainer="(target) => target.parentNode"
    :placeholder="placeholder"
    :disabled="disabled"
    :value="getValueSting"
    @change="handleInput">
    <a-select-option :value="undefined">请选择</a-select-option>
    <a-select-option v-for="(item, key) in dictOptions" :key="key" :value="item.value">
      <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
        {{ item.text || item.label }}
      </span>
    </a-select-option>
  </a-select>
</template>

<script>
  import { ajaxGetDictItems } from '@/api/admin/dict/info/index'

  export default {
    name: 'JDictSelectTag',
    props: {
      dictCode: { type: String, default: null },
      placeholder: { type: String, default: null },
      triggerChange: Boolean,
      disabled: Boolean,
      value: { type: [String, Number], default: null },
      type: { type: String, default: null }
    },
    data () {
      return {
        dictOptions: [],
        tagType: ''
      }
    },
    watch: {
      dictCode: {
        immediate: true,
        handler () {
          this.initDictData()
        }
      }
    },
    created () {
      // console.log(this.dictCode);
      if (!this.type || this.type === 'list') {
        this.tagType = 'select'
      } else {
        this.tagType = this.type
      }
      // 获取字典数据
      // this.initDictData();
    },
    computed: {
      getValueSting () {
        return this.value != null ? this.value.toString() : null
      }
    },
    methods: {
      initDictData () {
        // 根据字典Code, 初始化字典数组
        ajaxGetDictItems(this.dictCode, null).then((res) => {
         if (res.code === 0) {
            this.dictOptions = res.data
          }
        })
      },
      handleInput (e) {
        let val
        if (this.tagType === 'radio') {
          val = e.target.value
        } else {
          val = e
        }
        console.log(val)
        if (this.triggerChange) {
          this.$emit('change', val)
        } else {
          this.$emit('input', val)
        }
      },
      setCurrentDictOptions (dictOptions) {
        this.dictOptions = dictOptions
      },
      getCurrentDictOptions () {
        return this.dictOptions
      }
    }
  }
</script>

<style scoped>
</style>
