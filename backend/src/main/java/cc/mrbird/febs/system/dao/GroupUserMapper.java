package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.system.domain.GroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface GroupUserMapper extends BaseMapper<GroupUser> {

    IPage<GroupUser> findGroupUserDetail(Page page, @Param("groupUser") GroupUser groupUser);

}
