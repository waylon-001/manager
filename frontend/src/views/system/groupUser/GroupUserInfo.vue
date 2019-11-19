<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @cancel="handleCancleClick"
    title="群成员信息">
    <a-layout class="user-info">
      <a-layout-sider class="user-info-side">
        <a-avatar shape="square" :size="115" icon="user" :src="`static/avatar/${groupUserData.avatar}`"/>
      </a-layout-sider>
      <a-layout-content class="user-content-one">
        <p><a-icon type="user"/>群号：{{groupUserData.groupCode}}</p>
        <p><a-icon type="skin"/>用户ID：{{groupUserData.groupUserId}}</p>
        <p><a-icon type="clock-circle"/>创建时间：{{groupUserData.createTime}}</p>
      </a-layout-content>
      <a-layout-content class="user-content-two">
        <p>
          <a-icon type="smile" v-if="groupUserData.groupUserStatus === '1'"/>
          <a-icon type="frown" v-else/>状态：
          <template v-if="groupUserData.groupUserStatus === '0'">
            <a-tag color="red">待审核</a-tag>
          </template>
          <template v-else-if="groupUserData.groupUserStatus === '1'">
            <a-tag color="cyan">通过</a-tag>
          </template>
          <template v-else-if="groupUserData.groupUserStatus === '2'">
            <a-tag color="red">拒绝</a-tag>
          </template>
          <template v-else>
            {{groupUserData.groupStatus}}
          </template>
        </p>
        <p>
          <a-icon type="smile" v-if="groupUserData.groupUserType === '1'"/>
          <a-icon type="frown" v-else/>类型：
          <template v-if="groupUserData.groupUserType === '0'">
            <a-tag color="cyan">群主</a-tag>
          </template>
          <template v-else-if="groupUserData.groupUserType === '1'">
            <a-tag color="red">群成员</a-tag>
          </template>
          <template v-else>
            {{groupUserData.groupStatus}}
          </template>
        </p>
        <p><a-icon type="clock-circle"/>虚拟币：{{groupUserData.groupUserAmount}}</p>
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  name: 'GroupUserInfo',
  props: {
    groupUserVisiable: {
      require: true,
      default: false
    },
    groupUserData: {
      require: true
    }
  },
  computed: {
    show: {
      get: function () {
        return this.groupUserVisiable
      },
      set: function () {
      }
    }
  },
  methods: {
    handleCancleClick () {
      this.$emit('close')
    }
  }
}
</script>
<style lang="less" scoped>
@import "GroupInfo";
</style>
