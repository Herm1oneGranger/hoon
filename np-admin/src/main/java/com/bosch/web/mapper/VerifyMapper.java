package com.bosch.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.VerifyRecord;
import com.bosch.web.domain.dto.VerifyDTO;
import com.bosch.web.domain.dto.VerifyRecordDTO;
import com.bosch.web.domain.vo.ProductMiniVO;
import com.bosch.web.domain.vo.RecordVo;
import com.bosch.web.domain.vo.ReportData;
import com.bosch.web.domain.vo.VerifyRecordVO;
import java.util.List;

/**
* @author ZPO2SGH
* @description 针对表【p_verify_record】的数据库操作Mapper
* @createDate 2023-10-18 15:46:30
* @Entity com.bosch.web.domain.VerifyRecord
*/
public interface VerifyMapper extends BaseMapper<VerifyRecord> {

    /**
     * 查询校验列表
     *
     * @param verifyRecord 校验记录
     * @return 校验记录集合
     */
    public List<VerifyRecordVO> selectVerifyRecordList(VerifyRecordDTO verifyRecord);

    public List<VerifyRecordVO> selectVerifyRecordListByData(VerifyRecordDTO verifyRecord);
    /**
     * 通过产品ID查询小程序所需展示的产品信息
     *
     * @param productId 产品ID
     * @return 产品信息ProductMiniVO
     */
    public ProductMiniVO selectProductMiniVoById(Long productId);

    /**
     * 查询校验数量
     *
     * @param verifyRecord 校验记录
     * @return 校验记录总数
     */
    public int countVerifyRecord(VerifyRecordDTO verifyRecord);

    /**
     * 查询校验位置数量
     *
     * @param verifyRecord 校验记录
     * @return 位置记录
     */
    public int countLabelScannedLocations(VerifyRecordDTO verifyRecord);

    /**
     * 查询校验用户数量
     *
     * @param verifyRecord 校验记录
     * @return 用户记录
     */
    public int countLabelUsersScanned(VerifyRecordDTO verifyRecord);

    /**
     * 新增校验记录
     *
     * @param verifyDTO 校验记录
     * @return 结果
     */
    public int insertRecord(VerifyDTO verifyDTO);

    /**
     * 更新校验记录
     *
     * @param verifyRecord 校验记录
     * @return 结果
     */
    public int updateRecord(VerifyRecord verifyRecord);

    /**
     * 查询ReportData中的相关数据
     *
     * @return ReportData
     */
    public ReportData selectReportData();

    /**
     * 查询近一周每天参与校验的用户数量
     */
    public List<RecordVo> selectWeekCheckedPeople();

    /**
     * 查询近一周每天风险校验的数量
     */
    public List<RecordVo> getWeekRiskRecord();

    /**
     * 查询近一年每月风险校验的数量
     */
    public List<RecordVo> getYearRiskRecord();

    /**
     * 查询近一周每日校验的数量
     */
    public List<RecordVo> getWeekCheckedRecord();

    /**
     * 查询近一年每月校验的数量
     */
    public List<RecordVo> getYearCheckedRecord();

    /**
     * 查询所有地址数量
     */
    public List<RecordVo> getAddressData();

    /**
     * 查询所有省级地址数量
     */
    public List<RecordVo> getAddressByProvince();
}