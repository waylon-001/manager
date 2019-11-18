package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.dao.GroupInfoMapper;
import cc.mrbird.febs.system.domain.GroupInfo;
import cc.mrbird.febs.system.service.GroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service("groupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements GroupService {


    @Autowired
    private CacheService cacheService;


    @Override
    public IPage<GroupInfo> findGroupInfoDetail(GroupInfo groupInfo, QueryRequest request) {

        try {
            Page<GroupInfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_ASC, false);
            return this.baseMapper.findGroupInfoDetail(page, groupInfo);
        } catch (Exception e) {
            log.error("查询群异常", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createGroupInfo(GroupInfo groupInfo) throws Exception {
        // 创建群
        save(groupInfo);

    }

    @Override
    @Transactional
    public void updateGroupInfo(GroupInfo groupInfo) throws Exception {
        // 更新用户
        updateById(groupInfo);

    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) throws Exception {

        List<String> list = Arrays.asList(userIds);

        removeByIds(list);

    }

}
