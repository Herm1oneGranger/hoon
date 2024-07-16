package com.bosch.web.domain.vo;
import com.bosch.common.annotation.Excel;
import com.bosch.web.domain.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 
 * 打印信息
 */
@Data
public class ProductVO extends Product {

    /**
     * 产品校验记录
     */
    @ApiModelProperty(value = "产品校验记录")
    @Excel(name = "产品校验记录")
    private List<VerifyRecordVO> verifyRecords;

}