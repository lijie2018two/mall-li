package com.ccairbag.api.service;

import java.util.List;
import com.ruoyi.system.domain.CcairbagCarBrands;

/**
 * 汽车品牌型号Service接口
 * 
 * @author lidabai
 * @date 2025-03-12
 */
public interface ICcairbagCarBrandsService 
{
   List<CcairbagCarBrands> getMakeList();

    List<CcairbagCarBrands> getModelListByMake(String make);

    List<CcairbagCarBrands> getYearListByModel(String model);


}
