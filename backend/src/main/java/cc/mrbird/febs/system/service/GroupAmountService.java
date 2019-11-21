package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.system.domain.GroupAmount;
import cc.mrbird.febs.system.domain.GroupInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


public interface GroupAmountService extends IService<GroupAmount> {

    /**
     * 查询充值表信息
     *
     * @param groupAmount  groupAmount
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<GroupAmount> findGroupAmountDetail(GroupAmount groupAmount, QueryRequest queryRequest);


    /**
     * 新增充值记录
     *
     * @param groupAmount groupAmount
     */
    void createGroupAmount(GroupAmount groupAmount) throws Exception;

    /**
     * 修改充值记录
     *
     * @param groupAmount groupAmount
     */
    void updateGroupAmount(GroupAmount groupAmount) throws Exception;

}
