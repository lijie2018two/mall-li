package com.ccairbag.api.service.impl;

import java.util.Collections;
import java.util.List;

import com.ccairbag.api.mapper.CcairbagCarBrandsMapper;
import com.ccairbag.api.service.ICcairbagCarBrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.CcairbagCarBrands;

import javax.annotation.Resource;

/**
 * 汽车品牌型号Service业务层处理
 * 
 * @author lidabai
 * @date 2025-03-12
 */
@Service
public class CcairbagCarBrandsServiceImpl implements ICcairbagCarBrandsService
{
    @Resource
    private CcairbagCarBrandsMapper ccairbagCarBrandsMapper;


    @Override
    public List<CcairbagCarBrands> getMakeList() {
        return ccairbagCarBrandsMapper.getMakeList();
    }

    @Override
    public List<CcairbagCarBrands> getModelListByMake(String make) {
        return ccairbagCarBrandsMapper.getModelListByMake(make);
    }

    @Override
    public List<CcairbagCarBrands> getYearListByModel(String model) {
        return ccairbagCarBrandsMapper.getYearListByModel(model);
    }
}
