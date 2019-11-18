package cc.mrbird.febs.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("q_group_room")
@Excel("群房间信息表")
public class GroupRoom implements Serializable {

    private static final long serialVersionUID = -4852732617765810959L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long ID;

    private  String groupCode;

    private String groupRoomType;

    private String groupRoomStatus;

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

    public String getGroupRoomType() {
        return groupRoomType;
    }

    public void setGroupRoomType(String groupRoomType) {
        this.groupRoomType = groupRoomType;
    }

    public String getGroupRoomStatus() {
        return groupRoomStatus;
    }

    public void setGroupRoomStatus(String groupRoomStatus) {
        this.groupRoomStatus = groupRoomStatus;
    }
}
