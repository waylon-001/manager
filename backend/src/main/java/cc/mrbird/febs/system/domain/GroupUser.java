package cc.mrbird.febs.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("q_group_user")
@Excel("群成员信息表")
public class GroupUser implements Serializable {

    private static final long serialVersionUID = -4852732617765810959L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long ID;

    private  String groupCode;

    private String groupUserId;

    private String groupUserType;

    private String groupUserStatus;

    private  String groupUserAmount;

    private String createTime;

    private String createBy;

    private String updateTime;

    private String updateBy;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(String groupUserId) {
        this.groupUserId = groupUserId;
    }

    public String getGroupUserType() {
        return groupUserType;
    }

    public void setGroupUserType(String groupUserType) {
        this.groupUserType = groupUserType;
    }

    public String getGroupUserStatus() {
        return groupUserStatus;
    }

    public void setGroupUserStatus(String groupUserStatus) {
        this.groupUserStatus = groupUserStatus;
    }

    public String getGroupUserAmount() {
        return groupUserAmount;
    }

    public void setGroupUserAmount(String groupUserAmount) {
        this.groupUserAmount = groupUserAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
