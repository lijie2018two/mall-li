package com.ccairbag.api.domain.dto;

import com.ruoyi.system.api.domain.ccairbag.CcairbagCarousel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class IndexDto {
    @ApiParam(value = "轮播图")
    private List<CcairbagCarousel> carousel;


}
