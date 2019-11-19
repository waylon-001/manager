package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.dao.GroupInfoMapper;
import cc.mrbird.febs.system.dao.GroupUserMapper;
import cc.mrbird.febs.system.domain.GroupInfo;
import cc.mrbird.febs.system.domain.GroupUser;
import cc.mrbird.febs.system.service.GroupService;
import cc.mrbird.febs.system.service.GroupUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("groupUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {


    @Autowired
    private CacheService cacheService;


    @Override
    public IPage<GroupUser> findGroupUserDetail(GroupUser groupUser, QueryRequest request) {

        try {
            Page<GroupUser> page = new Page<>();
            SortUtil.handlePageSort(request, page, "ID", FebsConstant.ORDER_ASC, false);
            return this.baseMapper.findGroupUserDetail(page, groupUser);
        } catch (Exception e) {
            log.error("查询群成员异常", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createGroupUser(GroupUser groupUser) throws Exception {
        // 创建群成员
        groupUser.setCreateTime(DateUtil.getDateFormat(new Date(),DateUtil.FULL_TIME_SPLIT_PATTERN));
        save(groupUser);

    }

    @Override
    @Transactional
    public void updateGroupUser(GroupUser groupUser) throws Exception {
        // 更新用户
        groupUser.setUpdateTime(DateUtil.getDateFormat(new Date(),DateUtil.FULL_TIME_SPLIT_PATTERN));
        updateById(groupUser);

    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) throws Exception {

        List<String> list = Arrays.asList(userIds);

        removeByIds(list);

    }

}
