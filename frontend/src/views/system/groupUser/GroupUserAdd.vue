<template>
  <a-drawer
    title="新增群成员"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="groupUserAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='群名字' v-bind="formItemLayout">
        <a-tree-select
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="deptTreeData"
          v-decorator="['id']">
        </a-tree-select>
      </a-form-item>

      <a-form-item label='类型' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="['groupUserType',{rules: [{ required: true, message: '请选择类型'}]}]">
          <a-radio value="0">群主</a-radio>
          <a-radio value="1">群成员</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label='状态' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="['groupStatus',{rules: [{ required: true, message: '请选择状态'}]}]">
          <a-radio value="0">待审核</a-radio>
          <a-radio value="1">通过</a-radio>
          <a-radio value="1">拒绝</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label='虚拟币'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus"
                   :help="help">
        <a-input @blur="handleUserNameBlur"
                 v-decorator="['groupUserAmount',{rules: [{ required: true, message: '虚拟币不能为空'}]}]"/>
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
  name: 'GroupAdd',
  props: {
    groupUserAddVisiable: {
      default: false
    }
  },
  data () {
    return {
      groupInfo: {
        groupName: ''
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
      this.groupInfo.groupName = ''
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      if (this.validateStatus !== 'success') {
        this.handleUserNameBlur()
      }
      this.form.validateFields((err, values) => {
        if (!err && this.validateStatus === 'success') {
          this.setUserFields()
          this.loading = true
          this.$post('groupInfo', {
            ...this.groupInfo
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    handleUserNameBlur () {
      let groupName = this.form.getFieldValue('groupName')
      groupName = typeof groupName === 'undefined' ? '' : groupName.trim()
      if (groupName.length) {
        if (groupName.length > 10) {
          this.validateStatus = 'error'
          this.help = '群名不能超过10个字符'
          // eslint-disable-next-line no-undef
        } else if (groupName.length < 2) {
          this.validateStatus = 'error'
          this.help = '群名不能少于2个字符'
        } else {
          this.validateStatus = 'validating'
          this.$get(`groupInfo/check/${groupName}`).then((r) => {
            if (r.data) {
              this.validateStatus = 'success'
              this.help = ''
            } else {
              this.validateStatus = 'error'
              this.help = '抱歉，该群名已存在'
            }
          })
        }
      } else {
        this.validateStatus = 'error'
        this.help = '用群名不能为空'
      }
    },
    setUserFields () {
      let values = this.form.getFieldsValue(['groupName', 'groupHeadPic', 'groupStatus'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.groupInfo[_key] = values[_key] })
      }
    }
  },
  watch: {
    groupUserAddVisiable () {
      if (this.groupUserAddVisiable) {
        this.$get('role').then((r) => {
          this.roleData = r.data.rows
        })
        this.$get('groupInfo/groupList').then((r) => {
          this.deptTreeData = r.data.rows.children
          console.log('deptTreeData', this.deptTreeData)
        })
      }
    }
  }
}
</script>
