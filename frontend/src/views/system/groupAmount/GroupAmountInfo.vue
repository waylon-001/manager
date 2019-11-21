<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @cancel="handleCancleClick"
    title="充值信息">
    <a-layout class="user-info">
      <a-layout-sider class="user-info-side">
        <a-avatar shape="square" :size="115" icon="user" :src="`static/avatar/${groupAmountData.avatar}`"/>
      </a-layout-sider>
      <a-layout-content class="user-content-one">
        <p><a-icon type="user"/>群号：{{groupAmountData.groupCode}}</p>
        <p><a-icon type="skin"/>群主：{{groupAmountData.groupUserId}}</p>
      </a-layout-content>
      <a-layout-content class="user-content-two">
        <p><a-icon type="skin"/>金额：{{groupAmountData.groupUserAmount}}</p>
        <p><a-icon type="clock-circle"/>创建时间：{{groupAmountData.createTime}}</p>
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  name: 'GroupAmountInfo',
  props: {
    groupAmountVisiable: {
      require: true,
      default: false
    },
    groupAmountData: {
      require: true
    }
  },
  computed: {
    show: {
      get: function () {
        return this.groupAmountVisiable
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
