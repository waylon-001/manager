<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row >
        <div :class="advanced ? null: 'fold'">
            <a-col :md="12" :sm="24" >
              <a-form-item
                label="群号"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.groupCode"/>
              </a-form-item>
            </a-col>
<!--            <a-col :md="12" :sm="24" >
              <a-form-item
                label="部门"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <dept-input-tree @change="handleDeptChange"
                                 ref="deptTree">
                </dept-input-tree>
              </a-form-item>
            </a-col>-->
          <template v-if="advanced">
            <a-col :md="12" :sm="24" >
              <a-form-item
                label="创建时间"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <range-date @change="handleDateChange" ref="createTime"></range-date>
              </a-form-item>
            </a-col>
          </template>
        </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
             <a @click="toggleAdvanced" style="margin-left: 8px">
              {{advanced ? '收起' : '展开'}}
              <a-icon :type="advanced ? 'up' : 'down'" />
            </a>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add" v-hasPermission="['groupAmount:add']">新增</a-button>
      <!--  <a-button @click="batchDelete" v-hasPermission="['groupAmount:delete']">删除</a-button>-->
<!--        <a-dropdown v-hasAnyPermission="['groupAmount:reset','groupAmount:export']">
          <a-menu slot="overlay">
            <a-menu-item v-hasPermission="['groupAmount:export']" key="export-data" @click="exportExcel">导出Excel</a-menu-item>
          </a-menu>
          <a-button>
            更多操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>-->
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
<!--        <template slot="email" slot-scope="text, record">
          <a-popover placement="topLeft">
            <template slot="content">
              <div>{{text}}</div>
            </template>
            <p style="width: 150px;margin-bottom: 0">{{text}}</p>
          </a-popover>
        </template>-->
        <template slot="operation" slot-scope="text, record">
          <a-icon v-hasPermission="['groupAmount:update']" type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修改用户"></a-icon>
          &nbsp;
          <a-icon v-hasPermission="['groupAmount:view']" type="eye" theme="twoTone" twoToneColor="#42b983" @click="view(record)" title="查看"></a-icon>
          <a-badge v-hasNoPermission="['groupAmount:update','groupAmount:view']" status="warning" text="无权限"></a-badge>
        </template>
      </a-table>
    </div>
    <!-- 用户信息查看 -->
    <group-amount-info
      :groupAmountData="groupAmount.data"
      :groupAmountVisiable="groupAmount.visiable"
      @close="handleGroupAmountClose">
    </group-amount-info>
    <!-- 新增用户 -->
    <group-amount-add
      @close="handleGroupAmountAddClose"
      @success="handleGroupAmountAddSuccess"
      :GroupAmountAddVisiable="GroupAmountAdd.visiable">
    </group-amount-add>
    <!-- 修改用户 -->
    <group-amount-edit
      ref="groupAmountEdit"
      @close="handleGroupAmountEditClose"
      @success="handleGroupAmountEditSuccess"
      :groupAmountEditVisiable="groupAmountEdit.visiable">
    </group-amount-edit>
  </a-card>
</template>

<script>
import GroupAmountInfo from './GroupAmountInfo'
import DeptInputTree from '../dept/DeptInputTree'
import RangeDate from '@/components/datetime/RangeDate'
import GroupAmountAdd from './GroupAmountAdd'
import GroupAmountEdit from './GroupAmountEdit'

export default {
  name: 'GroupAmount',
  components: {GroupAmountInfo, GroupAmountAdd, GroupAmountEdit, DeptInputTree, RangeDate},
  data () {
    return {
      advanced: false,
      groupAmount: {
        visiable: false,
        data: {}
      },
      GroupAmountAdd: {
        visiable: false
      },
      groupAmountEdit: {
        visiable: false
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      }
    }
  },
  computed: {
    columns () {
      // eslint-disable-next-line no-unused-vars
      let { sortedInfo, filteredInfo } = this
      sortedInfo = sortedInfo || {}
      filteredInfo = filteredInfo || {}
      return [{
        title: '群号',
        dataIndex: 'groupCode'
      },
      {
        title: '群主ID',
        dataIndex: 'groupUserId',
        sorter: true,
        sortOrder: sortedInfo.columnKey === 'groupUserId' && sortedInfo.order
      }, {
        title: '充值金额',
        dataIndex: 'groupUserAmount'
      }, {
        title: '创建时间',
        dataIndex: 'createTime',
        sorter: true,
        sortOrder: sortedInfo.columnKey === 'createTime' && sortedInfo.order
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' }
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.createTimeFrom = ''
        this.queryParams.createTimeTo = ''
      }
    },
    view (record) {
      this.groupAmount.data = record
      this.groupAmount.visiable = true
    },
    add () {
      this.GroupAmountAdd.visiable = true
    },
    handleGroupAmountAddClose () {
      this.GroupAmountAdd.visiable = false
    },
    handleGroupAmountAddSuccess () {
      this.GroupAmountAdd.visiable = false
      this.$message.success('新增成功！')
      this.search()
    },
    edit (record) {
      this.$refs.groupAmountEdit.setFormValues(record)
      this.groupAmountEdit.visiable = true
    },
    handleGroupAmountEditClose () {
      this.groupAmountEdit.visiable = false
    },
    handleGroupAmountEditSuccess () {
      this.groupAmountEdit.visiable = false
      this.$message.success('修改成功')
      this.search()
    },
    handleGroupAmountClose () {
      this.groupAmount.visiable = false
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    handleDateChange (value) {
      if (value) {
        this.queryParams.createTimeFrom = value[0]
        this.queryParams.createTimeTo = value[1]
      }
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let userIds = []
          let selectedRowKeysStr = ',' + that.selectedRowKeys.join(',') + ','
          userIds.push(that.dataSource.filter(f => { return selectedRowKeysStr.indexOf(',' + f.userId + ',') > -1 }).map(m => { return m.userId }))
          that.$delete('user/' + userIds.join(',')).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    exportExcel () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('user/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      // 清空部门树选择
      this.$refs.deptTree.reset()
      // 清空时间选择
      if (this.advanced) {
        this.$refs.createTime.reset()
      }
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.groupAmount.visiable = false
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      this.$get('groupAmount', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
