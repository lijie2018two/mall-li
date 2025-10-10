package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.CollectionUtils;

import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppVersionMapper;
import com.ruoyi.system.api.domain.ccairbag.AppVersion;
import com.ruoyi.system.service.IAppVersionService;

import javax.annotation.Resource;

/**
 * App版本信息Service业务层处理
 * 
 * @author lidabai
 * @date 2025-10-09
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService 
{
    @Resource
    private AppVersionMapper appVersionMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 查询App版本信息
     * 
     * @param id App版本信息主键
     * @return App版本信息
     */
    @Override
    public AppVersion selectAppVersionById(Long id)
    {
        return appVersionMapper.selectAppVersionById(id);
    }

    /**
     * 查询App版本信息列表
     * 
     * @param appVersion App版本信息
     * @return App版本信息
     */
    @Override
    public List<AppVersion> selectAppVersionList(AppVersion appVersion)
    {
        return appVersionMapper.selectAppVersionList(appVersion);
    }

    /**
     * 新增App版本信息
     * 
     * @param appVersion App版本信息
     * @return 结果
     */
    @Override
    public int insertAppVersion(AppVersion appVersion)
    {
        appVersion.setCreateTime(DateUtils.getNowDate());
        appVersion.setVersionCode("app_version_code_"+ DateUtils.dateTimeNow());
        appVersion.setDeleted(0);
        return appVersionMapper.insertAppVersion(appVersion);
    }

    /**
     * 修改App版本信息
     * 
     * @param appVersion App版本信息
     * @return 结果
     */
    @Override
    public int updateAppVersion(AppVersion appVersion)
    {
        appVersion.setUpdateTime(DateUtils.getNowDate());
        return appVersionMapper.updateAppVersion(appVersion);
    }

    /**
     * 批量删除App版本信息
     * 
     * @param ids 需要删除的App版本信息主键
     * @return 结果
     */
    @Override
    public int deleteAppVersionByIds(Long[] ids)
    {
        return appVersionMapper.deleteAppVersionByIds(ids);
    }

    /**
     * 删除App版本信息信息
     * 
     * @param id App版本信息主键
     * @return 结果
     */
    @Override
    public int deleteAppVersionById(Long id)
    {
        return appVersionMapper.deleteAppVersionById(id);
    }


    /**
     * 批量新增App版本信息
     *
     * @param appVersions App版本信息List
     * @return 结果
     */
    @Override
    public int batchInsertAppVersion(List<AppVersion> appVersions)
    {

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(appVersions)) {
            try {
                for (int i = 0; i < appVersions.size(); i++) {
                    int row = appVersionMapper.insertAppVersion(appVersions.get(i));
                    // 防止内存溢出，没100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%3 == 0) || i == appVersions.size() - 1;
                    if (bool){
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            }finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }
}
