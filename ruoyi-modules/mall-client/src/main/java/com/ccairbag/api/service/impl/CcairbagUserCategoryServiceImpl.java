package  com.ccairbag.api.service.impl;

import com.ccairbag.api.mapper.CcairbagCategoryMapper;
import com.ccairbag.api.mapper.CcairbagUserCategoryMapper;
import com.ccairbag.api.service.ICcairbagUserCategoryService;
import com.ruoyi.system.api.domain.ccairbag.CcairbagCategory;
import com.ruoyi.system.api.domain.ccairbag.CcairbagUserCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 用户喜好分类Service业务层处理
 * 
 * @author lidabai
 * @date 2025-05-10
 */
@Service
@Slf4j
public class CcairbagUserCategoryServiceImpl implements ICcairbagUserCategoryService
{
    @Resource
    private CcairbagUserCategoryMapper ccairbagUserCategoryMapper;

    @Resource
    private CcairbagCategoryMapper ccairbagCategoryMapper;

    @Transactional
    public void recordUserPreference(Long userId, Long categoryId) {
        // 检查分类是否存在（可选）
        CcairbagCategory category = ccairbagCategoryMapper.selectCcairbagCategoryByCategoryId(categoryId);
        if (category == null) {
            log.warn("Category not found: {}", categoryId);
            return;
        }
        // 创建新的喜好记录
        CcairbagUserCategory preference = new CcairbagUserCategory();
        preference.setUserId(userId);
        preference.setCategoryId(categoryId);
        preference.setAccessTime(new Date());
        // 插入新记录
        ccairbagUserCategoryMapper.insertCcairbagUserCategory(preference);

        // 限制每个用户最多保存10条记录（删除最旧的记录）
        int count = ccairbagUserCategoryMapper.countByUserId(userId);
        log.info("count: {} maxRecords:{}", count,10);
        // 如果超过最大记录数，则删除最旧的记录
        if (count > 10) {
            int recordsToDelete = count - 10;
            List<Long> ids = ccairbagUserCategoryMapper.findOldestRecordIds(userId, recordsToDelete);

            int i = ccairbagUserCategoryMapper.deleteOldestRecords(ids);
            log.info("Deleted {} records for user {}", i, userId);
        }
    }


}
