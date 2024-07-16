package com.bosch.web.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: pink
 * @Description:
 * @Date: 11/3/2023 1:30 PM
 */
@Data
@Accessors(chain = true)
public class VerifyResult {

    private boolean riskStatus;

    private String remark;

}
