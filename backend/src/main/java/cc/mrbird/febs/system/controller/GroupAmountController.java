package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.dao.GroupUserMapper;
import cc.mrbird.febs.system.domain.GroupAmount;
import cc.mrbird.febs.system.domain.GroupUser;
import cc.mrbird.febs.system.service.GroupAmountService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("groupAmount")
public class GroupAmountController extends BaseController {

    private String message;

    @Autowired
    private GroupAmountService groupAmountService;
    @Autowired
    private GroupUserMapper groupUserMapper;

    @GetMapping
    @RequiresPermissions("groupAmount:view")
    public Map<String, Object> groupAmountList(QueryRequest queryRequest, GroupAmount groupAmount) {
        return getDataTable(groupAmountService.findGroupAmountDetail(groupAmount, queryRequest));
    }

    @Log("新增充值")
    @PostMapping
    @RequiresPermissions("groupAmount:add")
    public void addUser(@Valid GroupAmount groupAmount) throws FebsException {
        try {
            GroupUser groupUser=groupUserMapper.findDetail(groupAmount.getGroupCode());
            if(null!=groupUser)
            {
                groupAmount.setGroupUserId(groupUser.getGroupUserId());
                this.groupAmountService.createGroupAmount(groupAmount);
                //充值成功修改群金额
                groupUser.setGroupUserAmount(String.valueOf(Double.valueOf(groupUser.getGroupUserAmount())+Double.valueOf(groupAmount.getGroupUserAmount())));
                groupUserMapper.updateById(groupUser);
            }

        } catch (Exception e) {
            message = "新增充值失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改充值")
    @PutMapping
    @RequiresPermissions("groupAmount:update")
    public void updateUser(@Valid GroupAmount groupAmount) throws FebsException {
        try {
            this.groupAmountService.updateGroupAmount(groupAmount);
        } catch (Exception e) {
            message = "修改充值记录失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @PostMapping("excel")
    @RequiresPermissions("groupAmount:export")
    public void export(QueryRequest queryRequest, GroupAmount groupAmount, HttpServletResponse response) throws FebsException {
        try {
            List<GroupAmount> groupAmounts = this.groupAmountService.findGroupAmountDetail(groupAmount, queryRequest).getRecords();
            ExcelKit.$Export(GroupAmount.class, response).downXlsx(groupAmounts, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
