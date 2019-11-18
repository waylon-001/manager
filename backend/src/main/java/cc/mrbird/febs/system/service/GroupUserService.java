package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.system.domain.GroupInfo;
import cc.mrbird.febs.system.domain.GroupUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface GroupUserService extends IService<GroupUser> {


    /**
     * 查询群基本信息
     *
     * @param groupUser    groupUser
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<GroupUser> findGroupUserDetail(GroupUser groupUser, QueryRequest queryRequest);


    /**
     * 新增用户
     *
     * @param groupUser GroupUser
     */
    void createGroupUser(GroupUser groupUser) throws Exception;

    /**
     * 修改用户
     *
     * @param groupUser groupInfo
     */
    void updateGroupUser(GroupUser groupUser) throws Exception;

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(String[] userIds) throws Exception;

}
