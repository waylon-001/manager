<template>
  <a-drawer
    title="修改群用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="groupUserEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='群号' v-bind="formItemLayout">
        <a-input readOnly v-decorator="['groupCode']"/>
      </a-form-item>

      <a-form-item label='用户ID' v-bind="formItemLayout">
        <a-input  v-decorator="['groupUserId']"/>
      </a-form-item>

      <a-form-item label='状态' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="[
            'groupUserStatus',
            {rules: [{ required: true, message: '请选择状态' }]}
          ]">
          <a-radio value="0">待审核</a-radio>
          <a-radio value="1">通过</a-radio>
          <a-radio value="2">拒绝</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label='类型' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="[
            'groupUserType',
            {rules: [{ required: true, message: '请选择类型' }]}
          ]">
          <a-radio value="0">群主</a-radio>
          <a-radio value="1">群成员</a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item label='虚拟币' v-bind="formItemLayout">
        <a-input  v-decorator="['groupUserAmount']"/>
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
  name: 'GroupUserEdit',
  props: {
    groupUserEditVisiable: {
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
    setFormValues ({...groupUser}) {
      this.ID = groupUser.id
      let fields = ['groupCode', 'groupUserId', 'groupUserStatus', 'groupUserType', 'groupUserAmount']
      Object.keys(groupUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = groupUser[key]
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
          let groupUser = this.form.getFieldsValue()
          groupUser.ID = this.ID
          console.log('groupUser', groupUser)
          this.$put('groupUser', {
            ...groupUser
          }).then((r) => {
            this.loading = false
            this.$emit('success')
            // 如果修改用户就是当前登录用户的话，更新其state
            /*            if (groupUser.username === this.currentUser.username) {
              this.$get(`groupUser/${groupUser.username}`).then((r) => {
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
    groupUserEditVisiable () {
      if (this.groupUserEditVisiable) {

      }
    }
  }
}
</script>
