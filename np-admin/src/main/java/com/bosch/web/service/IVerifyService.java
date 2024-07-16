package com.bosch.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.VerifyRecord;
import com.bosch.web.domain.dto.VerifyRecordDTO;
import com.bosch.web.domain.dto.VerifyDTO;
import com.bosch.web.domain.vo.*;

import java.util.List;

/**
* @author ZPO2SGH
* @description 真伪校验 业务层
* @createDate 2023-10-18 15:46:30
*/
public interface IVerifyService extends IService<VerifyRecord> {

    /**
     * 根据条件分页查询产品校验信息列表
     *
     * @param verifyRecordDTO 校验信息
     * @return 校验信息集合信息
     */
    public List<VerifyRecordVO> selectVerifyRecordList(VerifyRecordDTO verifyRecordDTO);

    /**
     * 通过产品ID查询小程序所需展示的产品信息
     *
     * @param productId 产品ID
     * @return 产品信息ProductMiniVO
     */
    public ProductMiniVO selectProductMiniVoById(Long productId);

    /**
     * 校验标签
     *
     * @param verifyDTO 校验信息
     * @return VerifyResultVO校验结果
     */
    public VerifyResultVO verifyLabel(VerifyDTO verifyDTO);

    public VerifyResultVO verifyLabelNoMnr(VerifyDTO verifyDTO);
    /**
     * 查询ReportData中的相关数据
     *
     * @return ReportData
     */
    public ReportData getReportData();

    /**
     * 近一周每天参与用户数
     */
    public List<RecordVo> getWeekCheckedPeople();

    public List<RecordVo> getWeekRiskRecord();

    public List<RecordVo> getYearRiskRecord();

    public List<RecordVo> getWeekCheckedRecord();

    public List<RecordVo> getYearCheckedRecord();

    public List<RecordVo> getAddressData();

    public List<RecordVo> getAddressByProvince();

}