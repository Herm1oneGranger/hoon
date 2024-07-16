package com.bosch.web.domain;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductExcel extends BaseEntity {
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

    @ExcelProperty(value = "产品名",index = 0)
    private String name;

    /**
     * 物料号
     */
    @ApiModelProperty(value = "物料号")
    @ExcelProperty(value = "物料号",index = 1)
    private String mnr;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value = "订单号",index = 2)
    private String fd;

    /**
     * 产品序列号
     */
    @ApiModelProperty(value = "产品序列号")
    @ExcelProperty(value = "数量",index = 3)
    private String number;


}