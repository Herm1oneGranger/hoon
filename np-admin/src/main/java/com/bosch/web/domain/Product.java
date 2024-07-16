package com.bosch.web.domain;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName p_product
 */
@TableName(value ="p_product")
@Data
public class Product extends BaseEntity {
    /**
     * 产品ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "产品ID")
    @Excel(name = "产品ID", cellType = Excel.ColumnType.NUMERIC, prompt = "产品编号")
    private Long id;

    /**
     * 产品名
     */
    @ApiModelProperty(value = "产品名")
    @Excel(name = "产品名")
    @ExcelProperty(value = "",index = 0)
    private String name;

    /**
     * 物料号
     */
    @ApiModelProperty(value = "物料号")
    @Excel(name = "物料号")
    private String mnr;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    @Excel(name = "产品序列号")
    private String sn;

    /**
     * 使用状态（0启用 1停用）
     */
    @ApiModelProperty(value = "使用状态（0启用 1停用）")
    @Excel(name = "使用状态", readConverterExp = "0=启用,1=停用")
    private String status;

    /**
     * 校验状态（0正常 1风险 2未校验）
     */
    @ApiModelProperty(value = "（0正常 1风险 2未校验）")
    @Excel(name = "校验状态", readConverterExp = "0=正常,1=风险,2=未校验")
    private String verifyStatus;

    /**
     * 打印ID
     */
    @ApiModelProperty(value = "打印ID")
    @Excel(name = "打印ID")
    private Long printId;

}