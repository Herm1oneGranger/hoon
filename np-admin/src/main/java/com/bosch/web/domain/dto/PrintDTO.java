package com.bosch.web.domain.dto;
import com.bosch.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 打印集合返回对象 printDTO
 */
@Data
public class PrintDTO extends BaseEntity {

    /**
     * 打印ID
     */
    @ApiModelProperty(value = "打印序号")
    private Long id;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "打印文件名称")
    private String name;

    /**
     * 打印的产品标牌个数
     */
    @ApiModelProperty(value = "标牌打印个数")
    private String printNumber;

    /**
     * 录入状态（0正常 1失败）
     */
    @ApiModelProperty(value = "信息录入状态（0正常 1失败）")
    private String status;

    /**
     * xml文件信息
     */
    @ApiModelProperty(value = "xml文件")
    private String xml;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}