package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.system.domain.GroupAmount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface GroupAmountMapper extends BaseMapper<GroupAmount> {

    IPage<GroupAmount> findGroupAmountDetail(Page page, @Param("groupAmount") GroupAmount groupAmount);

}
