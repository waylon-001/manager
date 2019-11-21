package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.dao.GroupAmountMapper;
import cc.mrbird.febs.system.domain.GroupAmount;
import cc.mrbird.febs.system.service.GroupAmountService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service("groupAmountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupAmountServiceImpl extends ServiceImpl<GroupAmountMapper, GroupAmount> implements GroupAmountService {


    @Autowired
    private CacheService cacheService;


    @Override
    public IPage<GroupAmount> findGroupAmountDetail(GroupAmount groupAmount, QueryRequest request) {

        try {
            Page<GroupAmount> page = new Page<>();
            SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_ASC, false);
            return this.baseMapper.findGroupAmountDetail(page, groupAmount);
        } catch (Exception e) {
            log.error("查询群异常", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createGroupAmount(GroupAmount groupAmount) throws Exception {
        // 创建群
        groupAmount.setCreateTime(DateUtil.getDateFormat(new Date(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        save(groupAmount);
    }

    @Override
    @Transactional
    public void updateGroupAmount(GroupAmount groupAmount) throws Exception {
        // 更新用户
        groupAmount.setUpdateTime(DateUtil.getDateFormat(new Date(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        updateById(groupAmount);

    }


}
