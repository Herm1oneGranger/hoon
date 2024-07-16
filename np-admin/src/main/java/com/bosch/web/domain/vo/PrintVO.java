package com.bosch.web.domain.vo;
import com.bosch.common.annotation.Excel;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 打印信息
 */
@Data
public class PrintVO extends BaseEntity {

    /**
     * 打印ID
     */
    @ApiModelProperty(value = "打印序号")
    private Long id;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "打印文件名称")
    @Excel(name = "打印文件名称")
    private String name;

    /**
     * 打印的产品标牌个数
     */
    @ApiModelProperty(value = "标牌打印个数")
    @Excel(name = "标牌打印个数")
    private String printNumber;

    /**
     * 录入状态（0正常 1失败）
     */
    @ApiModelProperty(value = "信息录入状态（0正常 1失败）")
    @Excel(name = "信息录入状态", readConverterExp = "0=正常,1=失败")
    private String status;

    /**
     * xml文件信息
     */
    @ApiModelProperty(value = "xml文件")
    @Excel(name = "xml文件")
    private String xml;

}