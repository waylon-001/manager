package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.system.domain.GroupInfo;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface GroupService extends IService<GroupInfo> {

    /**
     * 通过用户名查找群
     *
     * @param groupName groupName
     * @return groupInfo
     */
    GroupInfo findByName(String groupName);

    /**
     * 查询群基本信息
     *
     * @param groupInfo    groupInfo
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<GroupInfo> findGroupInfoDetail(GroupInfo groupInfo, QueryRequest queryRequest);


    /**
     * 新增用户
     *
     * @param groupInfo groupInfo
     */
    void createGroupInfo(GroupInfo groupInfo) throws Exception;

    /**
     * 修改用户
     *
     * @param groupInfo groupInfo
     */
    void updateGroupInfo(GroupInfo groupInfo) throws Exception;

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds) throws Exception;

}
