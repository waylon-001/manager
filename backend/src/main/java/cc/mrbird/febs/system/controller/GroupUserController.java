package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.GroupInfo;
import cc.mrbird.febs.system.domain.GroupUser;
import cc.mrbird.febs.system.service.GroupService;
import cc.mrbird.febs.system.service.GroupUserService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("groupUser")
public class GroupUserController extends BaseController {

    private String message;

    @Autowired
    private GroupUserService groupUserService;


    @GetMapping
    @RequiresPermissions("groupUser:view")
    public Map<String, Object> groupInfoList(QueryRequest queryRequest, GroupUser groupUser) {
        return getDataTable(groupUserService.findGroupUserDetail(groupUser, queryRequest));
    }

    @Log("新增群成员")
    @PostMapping
    @RequiresPermissions("groupUser:add")
    public void addUser(@Valid GroupUser groupUser) throws FebsException {
        try {
            this.groupUserService.createGroupUser(groupUser);
        } catch (Exception e) {
            message = "新增群成员失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改群成员")
    @PutMapping
    @RequiresPermissions("groupUser:update")
    public void updateUser(@Valid GroupUser groupUser) throws FebsException {
        try {
            this.groupUserService.updateGroupUser(groupUser);
        } catch (Exception e) {
            message = "修改群成员失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除群成员")
    @DeleteMapping("/{userIds}")
    @RequiresPermissions("groupUser:delete")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.groupUserService.deleteUsers(ids);
        } catch (Exception e) {
            message = "删除群成员失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @PostMapping("excel")
    @RequiresPermissions("groupUser:export")
    public void export(QueryRequest queryRequest, GroupUser groupUser, HttpServletResponse response) throws FebsException {
        try {
            List<GroupUser> groupUsers = this.groupUserService.findGroupUserDetail(groupUser, queryRequest).getRecords();
            ExcelKit.$Export(GroupUser.class, response).downXlsx(groupUsers, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
