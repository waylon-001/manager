<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @cancel="handleCancleClick"
    title="群信息">
    <a-layout class="user-info">
      <a-layout-sider class="user-info-side">
        <a-avatar shape="square" :size="115" icon="user" :src="`static/avatar/${groupInfoData.avatar}`"/>
      </a-layout-sider>
      <a-layout-content class="user-content-one">
        <p><a-icon type="user"/>群名：{{groupInfoData.groupName}}</p>
        <p><a-icon type="skin"/>群头像：{{groupInfoData.groupHeadPic}}</p>
      </a-layout-content>
      <a-layout-content class="user-content-two">
        <p>
          <a-icon type="smile" v-if="groupInfoData.groupStatus === '1'"/>
          <a-icon type="frown" v-else/>状态：
          <template v-if="groupInfoData.groupStatus === '0'">
            <a-tag color="cyan">正常</a-tag>
          </template>
          <template v-else-if="groupInfoData.groupStatus === '1'">
            <a-tag color="red">停用</a-tag>
          </template>
          <template v-else>
            {{groupInfoData.groupStatus}}
          </template>
        </p>
        <p><a-icon type="clock-circle"/>创建时间：{{groupInfoData.createTime}}</p>
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  name: 'GroupInfo',
  props: {
    groupInfoVisiable: {
      require: true,
      default: false
    },
    groupInfoData: {
      require: true
    }
  },
  computed: {
    show: {
      get: function () {
        return this.groupInfoVisiable
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
