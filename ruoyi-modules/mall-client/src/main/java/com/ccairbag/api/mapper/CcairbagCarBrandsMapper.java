package com.ccairbag.api.mapper;

import java.util.List;
import com.ruoyi.system.domain.CcairbagCarBrands;

/**
 * 汽车品牌型号Mapper接口
 * 
 * @author lidabai
 * @date 2025-03-12
 */
public interface CcairbagCarBrandsMapper 
{
    List<CcairbagCarBrands> getMakeList();

    List<CcairbagCarBrands> getModelListByMake(String make);

    List<CcairbagCarBrands> getYearListByModel(String model);

}
