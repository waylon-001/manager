package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.enums.CommonParameterEnum;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.GroupInfoMapper;
import cc.mrbird.febs.system.dao.GroupRoomMapper;
import cc.mrbird.febs.system.domain.*;
import cc.mrbird.febs.system.manager.UserManager;
import cc.mrbird.febs.system.service.GroupService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service("groupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements GroupService {


    @Autowired
    private CacheService cacheService;


    @Autowired
    private GroupRoomMapper groupRoomMapper;


    @Override
    public Map<String, Object> findGroupInfos(QueryRequest request, GroupInfo groupInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<GroupInfo> groupInfos = findGroupInfos(groupInfo, request);
            List<Tree<GroupInfo>> trees = new ArrayList<>();
            buildTrees(trees, groupInfos);
            Tree<GroupInfo> GroupInfoTree =new Tree<>();
            GroupInfoTree.setChildren(trees);
            result.put("rows", GroupInfoTree);
            result.put("total", groupInfos.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    private void buildTrees(List<Tree<GroupInfo>> trees, List<GroupInfo> groupInfos) {
        groupInfos.forEach(groupInfo -> {
            Tree<GroupInfo> tree = new Tree<>();
            tree.setId(groupInfo.getId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(groupInfo.getId().toString());
            tree.setText(groupInfo.getGroupName());
            //tree.setCreateTime(groupInfo.getCreateTime());
            //tree.setModifyTime(groupInfo.getModifyTime());
            //tree.setOrder(groupInfo.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }

    public List<GroupInfo> findGroupInfos(GroupInfo groupInfo, QueryRequest request) {
        QueryWrapper<GroupInfo> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(groupInfo.getGroupName()))
            queryWrapper.lambda().eq(GroupInfo::getGroupName, groupInfo.getGroupName());
/*        if (StringUtils.isNotBlank(dept.getCreateTimeFrom()) && StringUtils.isNotBlank(dept.getCreateTimeTo()))
            queryWrapper.lambda()
                    .ge(Dept::getCreateTime, dept.getCreateTimeFrom())
                    .le(Dept::getCreateTime, dept.getCreateTimeTo());*/
       // SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", FebsConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    public GroupInfo findByName(String groupName) {
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupInfo>().eq(GroupInfo::getGroupName, groupName));
    }

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
        GroupInfo groupInfo1= baseMapper.selectOne(new LambdaQueryWrapper<GroupInfo>().eq(GroupInfo::getGroupName, groupInfo.getGroupName()));
        if(null!=groupInfo1)
        {
            for(int i=0;i<5;i++)
            {
                GroupRoom groupRoom_nn=new GroupRoom();
                groupRoom_nn.setGroupCode(String.valueOf(groupInfo1.getId()));
                groupRoom_nn.setGroupRoomType(String.valueOf(CommonParameterEnum.GROUP_ROOM_TYPE_NN.getId()));
                groupRoom_nn.setGroupRoomStatus(String.valueOf(CommonParameterEnum.GROUP_ROOM_STATUS_ZC.getId()));
                groupRoom_nn.setCreateTime(DateUtil.getDateFormat(new Date(),DateUtil.FULL_TIME_SPLIT_PATTERN));
                groupRoomMapper.insert(groupRoom_nn);
                GroupRoom groupRoom_jh=new GroupRoom();
                groupRoom_jh.setGroupCode(String.valueOf(groupInfo1.getId()));
                groupRoom_jh.setGroupRoomType(String.valueOf(CommonParameterEnum.GROUP_ROOM_TYPE_JH.getId()));
                groupRoom_jh.setGroupRoomStatus(String.valueOf(CommonParameterEnum.GROUP_ROOM_STATUS_ZC.getId()));
                groupRoom_jh.setCreateTime(DateUtil.getDateFormat(new Date(),DateUtil.FULL_TIME_SPLIT_PATTERN));
                groupRoomMapper.insert(groupRoom_jh);
            }
        }
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
