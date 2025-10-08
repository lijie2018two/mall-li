package com.ccairbag.api.mapper;

import com.ruoyi.system.api.domain.ccairbag.CcairbagUserCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户喜好分类Mapper接口
 * 
 * @author lidabai
 * @date 2025-05-10
 */
public interface CcairbagUserCategoryMapper 
{
    /**
     * 查询用户喜好分类
     * 
     * @param id 用户喜好分类主键
     * @return 用户喜好分类
     */
    public CcairbagUserCategory selectCcairbagUserCategoryById(Long id);

    int countByUserId(Long userId);



    List<Long> findOldestRecordIds(@Param("userId") Long userId, @Param("count") int count);

    int deleteOldestRecords(@Param("ids") List<Long> ids);

    List<CcairbagUserCategory> findRecentByUserId(Long userId);

    /**
     * 查询用户喜好分类列表
     * 
     * @param ccairbagUserCategory 用户喜好分类
     * @return 用户喜好分类集合
     */
    public List<CcairbagUserCategory> selectCcairbagUserCategoryList(CcairbagUserCategory ccairbagUserCategory);

    /**
     * 新增用户喜好分类
     * 
     * @param ccairbagUserCategory 用户喜好分类
     * @return 结果
     */
    public int insertCcairbagUserCategory(CcairbagUserCategory ccairbagUserCategory);

    /**
     * 修改用户喜好分类
     * 
     * @param ccairbagUserCategory 用户喜好分类
     * @return 结果
     */
    public int updateCcairbagUserCategory(CcairbagUserCategory ccairbagUserCategory);

    /**
     * 删除用户喜好分类
     * 
     * @param id 用户喜好分类主键
     * @return 结果
     */
    public int deleteCcairbagUserCategoryById(Long id);

    /**
     * 批量删除用户喜好分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCcairbagUserCategoryByIds(Long[] ids);
}
