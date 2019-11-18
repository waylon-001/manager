package cc.mrbird.febs.system.domain;

import cc.mrbird.febs.common.converter.TimeConverter;
import cc.mrbird.febs.common.domain.RegexpConstant;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("q_group_info")
@Excel("群信息表")
public class GroupInfo implements Serializable {

    private static final long serialVersionUID = -4852732617765810959L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private  String groupName;

    private String groupHeadPic;

    private String groupStatus;

    private String createTime;

    private String createBy;

    private String updateTime;

    private String updateBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHeadPic() {
        return groupHeadPic;
    }

    public void setGroupHeadPic(String groupHeadPic) {
        this.groupHeadPic = groupHeadPic;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
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
