<template>
  <a-drawer
    title="修改用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="groupEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='群名' v-bind="formItemLayout">
        <a-input readOnly v-decorator="['groupName']"/>
      </a-form-item>

      <a-form-item label='群头像' v-bind="formItemLayout">
        <a-input  v-decorator="['groupHeadPic']"/>
      </a-form-item>

      <a-form-item label='状态' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="[
            'groupStatus',
            {rules: [{ required: true, message: '请选择状态' }]}
          ]">
          <a-radio value="0">正常</a-radio>
          <a-radio value="1">停用</a-radio>
        </a-radio-group>
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
import {mapState, mapMutations} from 'vuex'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'GroupEdit',
  props: {
    groupEditVisiable: {
      default: false
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      deptTreeData: [],
      loading: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  methods: {
    ...mapMutations({
      /* setUser: 'account/setUser' */
    }),
    onClose () {
      this.loading = false
      this.form.resetFields()
      this.$emit('close')
    },
    setFormValues ({...groupInfo}) {
      this.id = groupInfo.id
      let fields = ['groupName', 'groupHeadPic', 'groupStatus']
      Object.keys(groupInfo).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = groupInfo[key]
          this.form.setFieldsValue(obj)
        }
      })
    },
    onDeptChange (value) {
      this.userDept = value
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          let groupInfo = this.form.getFieldsValue()
          groupInfo.id = this.id
          this.$put('groupInfo', {
            ...groupInfo
          }).then((r) => {
            this.loading = false
            this.$emit('success')
            // 如果修改用户就是当前登录用户的话，更新其state
            /*            if (groupInfo.username === this.currentUser.username) {
              this.$get(`groupInfo/${groupInfo.username}`).then((r) => {
                this.setUser(r.data)
              })
            } */
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  },
  watch: {
    groupEditVisiable () {
      if (this.groupEditVisiable) {
        this.$get('role').then((r) => {
          this.roleData = r.data.rows
        })
        this.$get('dept').then((r) => {
          this.deptTreeData = r.data.rows.children
        })
      }
    }
  }
}
</script>
