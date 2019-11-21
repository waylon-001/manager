<template>
  <a-drawer
    title="充值"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="GroupAmountAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='群' v-bind="formItemLayout">
        <a-tree-select
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="deptTreeData"
          v-decorator="['groupCode']">
        </a-tree-select>
      </a-form-item>

      <a-form-item label='金额'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus"
                   :help="help">
        <a-input
                 v-decorator="['groupUserAmount',{rules: [{ required: true, message: '金额不能为空'}]}]"/>
      </a-form-item>

    </a-form>
      <div class="drawer-bootom-button">
        <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
          <a-button style="margin-right: .8rem">取消</a-button>
        </a-popconfirm>
        <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
      </div>
  </a-drawer>
</template>
<script>
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'GroupAmountAdd',
  props: {
    GroupAmountAddVisiable: {
      default: false
    }
  },
  data () {
    return {
      groupAmount: {
      },
      loading: false,
      roleData: [],
      deptTreeData: [],
      formItemLayout,
      form: this.$form.createForm(this),
      validateStatus: '',
      help: ''
    }
  },
  methods: {
    reset () {
      this.validateStatus = ''
      this.help = ''
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      if (this.validateStatus !== 'success') {
        this.validateStatus = 'success'
      }
      this.form.validateFields((err, values) => {
        if (!err && this.validateStatus === 'success') {
          this.setUserFields()
          this.loading = true
          this.$post('groupAmount', {
            ...this.groupAmount
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },

    setUserFields () {
      let values = this.form.getFieldsValue(['groupCode', 'groupUserAmount'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.groupAmount[_key] = values[_key] })
      }
    }
  },
  watch: {
    GroupAmountAddVisiable () {
      if (this.GroupAmountAddVisiable) {
        this.$get('role').then((r) => {
          this.roleData = r.data.rows
        })
        this.$get('groupInfo/groupList').then((r) => {
          this.deptTreeData = r.data.rows.children
        })
      }
    }
  }
}
</script>
