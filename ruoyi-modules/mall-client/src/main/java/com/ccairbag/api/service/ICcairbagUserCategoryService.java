package com.ccairbag.api.service;

/**
 * 用户喜好分类Service接口
 * 
 * @author lidabai
 * @date 2025-05-10
 */
public interface ICcairbagUserCategoryService 
{
    void recordUserPreference(Long userId, Long categoryId);


}
