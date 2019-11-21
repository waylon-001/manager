package cc.mrbird.febs.system.dao;

        import cc.mrbird.febs.system.domain.GroupInfo;
        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import com.baomidou.mybatisplus.core.metadata.IPage;
        import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
        import org.apache.ibatis.annotations.Param;

public interface GroupInfoMapper extends BaseMapper<GroupInfo> {

    IPage<GroupInfo> findGroupInfoDetail(Page page, @Param("groupInfo") GroupInfo groupInfo);

}
