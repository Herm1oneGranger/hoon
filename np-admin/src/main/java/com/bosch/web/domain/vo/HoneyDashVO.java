package com.bosch.web.domain.vo;

import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName honey_feedback
 */

@Data
public class HoneyDashVO  {


    @ApiModelProperty(value = "时间")
    private String date;


    @ApiModelProperty(value = "真数量")
    private Integer trueNum;


    @ApiModelProperty(value = "假数量")
    private Integer fakeNum;


    @ApiModelProperty(value = "总数量")
    private Integer total;



}