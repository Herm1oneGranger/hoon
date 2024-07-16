package com.bosch.web.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bosch.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName p_print
 */
@TableName(value ="p_print")
@Data
public class Print extends BaseEntity {
    /**
     * 打印ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 打印的产品标牌个数
     */
    private String printNumber;

    /**
     * 录入状态（0正常 1失败）
     */
    private String status;

    /**
     * xml文件信息
     */
    private String xml;

}