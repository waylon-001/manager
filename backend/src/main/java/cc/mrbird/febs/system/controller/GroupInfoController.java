package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.Dept;
import cc.mrbird.febs.system.domain.GroupInfo;
import cc.mrbird.febs.system.domain.Role;
import cc.mrbird.febs.system.service.GroupService;
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
@RequestMapping("groupInfo")
public class GroupInfoController extends BaseController {

    private String message;

    @Autowired
    private GroupService groupService;

    @GetMapping("/groupList")
    public Map<String, Object> deptList(QueryRequest request, GroupInfo groupInfo) {
        return this.groupService.findGroupInfos(request, groupInfo);
    }
/*    public Map<String, Object> roleList(QueryRequest queryRequest, GroupInfo groupInfo) {
        return getDataTable(groupService.findGroupInfos(groupInfo, queryRequest));
    }*/

    @GetMapping("check/{groupName}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String groupName) {
        return this.groupService.findByName(groupName) == null;
    }



    @GetMapping
    @RequiresPermissions("groupInfo:view")
    public Map<String, Object> groupInfoList(QueryRequest queryRequest, GroupInfo groupInfo) {
        return getDataTable(groupService.findGroupInfoDetail(groupInfo, queryRequest));
    }

    @Log("新增群")
    @PostMapping
    @RequiresPermissions("groupInfo:add")
    public void addUser(@Valid GroupInfo groupInfo) throws FebsException {
        try {
            this.groupService.createGroupInfo(groupInfo);
        } catch (Exception e) {
            message = "新增群失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改群")
    @PutMapping
    @RequiresPermissions("groupInfo:update")
    public void updateUser(@Valid GroupInfo groupInfo) throws FebsException {
        try {
            this.groupService.updateGroupInfo(groupInfo);
        } catch (Exception e) {
            message = "修改群失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除群")
    @DeleteMapping("/{userIds}")
    @RequiresPermissions("groupInfo:delete")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.groupService.deleteUsers(ids);
        } catch (Exception e) {
            message = "删除群失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @PostMapping("excel")
    @RequiresPermissions("groupInfo:export")
    public void export(QueryRequest queryRequest, GroupInfo groupInfo, HttpServletResponse response) throws FebsException {
        try {
            List<GroupInfo> groupInfos = this.groupService.findGroupInfoDetail(groupInfo, queryRequest).getRecords();
            ExcelKit.$Export(GroupInfo.class, response).downXlsx(groupInfos, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
