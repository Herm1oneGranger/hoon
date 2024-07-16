package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.annotation.DataScope;
import com.bosch.common.constant.ProductConstants;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.DateUtils;
import com.bosch.system.domain.SysNotice;
import com.bosch.system.service.ISysNoticeService;
import com.bosch.web.domain.PProductType;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.VerifyRecord;
import com.bosch.web.domain.VerifyResult;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.dto.VerifyDTO;
import com.bosch.web.domain.dto.VerifyRecordDTO;
import com.bosch.web.domain.vo.*;
import com.bosch.web.mapper.ProductMapper;
import com.bosch.web.mapper.VerifyMapper;
import com.bosch.web.service.IVerifyService;
import com.bosch.web.service.PProductTypeService;
import com.bosch.web.utils.LabelScannerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author ZPO2SGH
* @description Service实现
* @createDate 2023-10-18 15:46:30
*/
@Service
public class VerifyServiceImpl extends ServiceImpl<VerifyMapper, VerifyRecord>
    implements IVerifyService {

    @Value("${depts}")
    private String a;

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private VerifyMapper verifyMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PProductTypeService pProductTypeService;

    @Autowired
    private LabelScannerUtils labelScannerUtils;

    /**
     * 根据条件分页查询产品校验信息列表
     *
     * @param verifyRecordDTO 校验信息
     * @return 校验信息集合信息
     */
    @DataScope(deptAlias = "d", userAlias = "s")
    public List<VerifyRecordVO> selectVerifyRecordList(VerifyRecordDTO verifyRecordDTO){
        return verifyMapper.selectVerifyRecordListByData(verifyRecordDTO);
    }

    /**
     * 通过产品ID查询小程序所需展示的产品信息
     *
     * @param productId 产品ID
     * @return 产品信息ProductMiniVO
     */
    public ProductMiniVO selectProductMiniVoById(Long productId) {
        Map<String, String> map = new HashMap<>();
        String productName;
        String path = "";
        ProductMiniVO productMiniVO = verifyMapper.selectProductMiniVoById(productId);
        List<PProductType> list = pProductTypeService.list();
        if (!CollectionUtils.isEmpty(list)) {
            map = list.stream()
                    .collect(Collectors.toMap(PProductType::getName, PProductType::getImgPath));
        }

        if (productMiniVO == null) {
            return null;
        }

        productName = productMiniVO.getProductName();

        if (productName.contains("/")) {
            String[] split = productName.split("/");
            productName = split[0].replaceAll("\\s", "");
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 检查键是否包含空格
            if (key.contains(" ")) {
                boolean flag = true;
                String[] s = key.split(" ");
                for (String string : s) {
                    if (!productName.contains(string)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    // 符合条件，输出对应的值
                    path = value;
                }
            } else {
                if (productName.contains(key)) {
                    // 符合条件，输出对应的值
                    path = value;
                    break;
                }
            }
        }
        productMiniVO.setImgPath(path);
        return productMiniVO;
    }

    /**
     * 校验标签
     *
     * @param verifyDTO 校验信息
     * @return VerifyResultVO校验结果
     */
    @Transactional(rollbackFor = Exception.class)
    public VerifyResultVO verifyLabel(VerifyDTO verifyDTO){
        VerifyRecord verifyRecord = BeanConverUtil.conver(verifyDTO, VerifyRecord.class);
        VerifyResultVO verifyResultVO = new VerifyResultVO();
        Product checkRequiredProduct = checkProduct(verifyDTO);

        if (checkRequiredProduct == null
                || checkRequiredProduct.getStatus().equals(ProductConstants.PRODUCT_DISABLED_STATE)) {
            return verifyResultVO.setCheckedResult(ProductConstants.PRODUCT_ABSENCE_STATE);
        }

        Long checkRequiredProductId = checkRequiredProduct.getId();
        verifyRecord.setProductId(checkRequiredProductId);
        verifyMapper.insert(verifyRecord);

        String checkedResult = ProductConstants.VERIFY_RISK;

        if(!checkRequiredProduct.getVerifyStatus().equals(ProductConstants.VERIFY_RISK)){
            VerifyResult checkLabelResult = checkLabelStatus(checkRequiredProduct);
            if(!checkLabelResult.isRiskStatus()){
                checkedResult = ProductConstants.VERIFY_NORMAL;
            }
            verifyRecord.setRemark(checkLabelResult.getRemark());
        }

        // 更新数据操作  更新product
        if(!checkRequiredProduct.getVerifyStatus().equals(checkedResult)){
            checkRequiredProduct.setVerifyStatus(checkedResult);
            productMapper.updateProduct(checkRequiredProduct);
        }

        // 更新数据操作  更新verifyRecord
        verifyRecord.setCheckedResult(checkedResult);
        verifyMapper.updateRecord(verifyRecord);

        if (checkedResult.equals(ProductConstants.VERIFY_RISK)) {
            exceptionNotice(checkRequiredProduct);
        }

        verifyResultVO.setProductID(checkRequiredProductId);
        verifyResultVO.setCheckedResult(checkedResult);
        return verifyResultVO;
    }


    @Transactional(rollbackFor = Exception.class)
    public VerifyResultVO verifyLabelNoMnr(VerifyDTO verifyDTO){
        VerifyRecord verifyRecord = BeanConverUtil.conver(verifyDTO, VerifyRecord.class);
        VerifyResultVO verifyResultVO = new VerifyResultVO();
        Product checkRequiredProduct = checkProductNoMnr(verifyDTO);

        if (checkRequiredProduct == null
                || checkRequiredProduct.getStatus().equals(ProductConstants.PRODUCT_DISABLED_STATE)) {
            return verifyResultVO.setCheckedResult(ProductConstants.PRODUCT_ABSENCE_STATE);
        }

        Long checkRequiredProductId = checkRequiredProduct.getId();
        verifyRecord.setProductId(checkRequiredProductId);
        verifyMapper.insert(verifyRecord);

        String checkedResult = ProductConstants.VERIFY_RISK;

        if(!checkRequiredProduct.getVerifyStatus().equals(ProductConstants.VERIFY_RISK)){
            VerifyResult checkLabelResult = checkLabelStatus(checkRequiredProduct);
            if(!checkLabelResult.isRiskStatus()){
                checkedResult = ProductConstants.VERIFY_NORMAL;
            }
            verifyRecord.setRemark(checkLabelResult.getRemark());
        }

        // 更新数据操作  更新product
        if(!checkRequiredProduct.getVerifyStatus().equals(checkedResult)){
            checkRequiredProduct.setVerifyStatus(checkedResult);
            productMapper.updateProduct(checkRequiredProduct);
        }

        // 更新数据操作  更新verifyRecord
        verifyRecord.setCheckedResult(checkedResult);
        verifyMapper.updateRecord(verifyRecord);

        if (checkedResult.equals(ProductConstants.VERIFY_RISK)) {
            exceptionNotice(checkRequiredProduct);
        }

        verifyResultVO.setProductID(checkRequiredProductId);
        verifyResultVO.setCheckedResult(checkedResult);
        return verifyResultVO;
    }
    /**
     * 异常通知
     *
     * @param product 产品信息
     */
    public void exceptionNotice(Product product){
        SysNotice notice = new SysNotice();
        notice.setNoticeTitle("产品"+product.getId()+"校验报警");
        notice.setNoticeContent("产品(MNR:"+product.getMnr()+",FD:"+product.getFd()
                +",SN:"+product.getSn()+")校验结果异常，请及时查看处理！");
        notice.setCreateBy("系统");
        notice.setRemark("系统算法提醒");
        notice.setNoticeType(SysNotice.TYPE_NOTICE);
        noticeService.insertNotice(notice);
    }

    /**
     * 判断产品是否存在
     *
     * @param verifyDTO 校验信息
     * @return 查询结果
     */
    public Product checkProduct(VerifyDTO verifyDTO){
        List<Product> list = productMapper.selectProductList(
                new ProductDTO().setMnr(verifyDTO.getMnr())
                        .setSn(verifyDTO.getSn())
                        .setFd(verifyDTO.getFd()));
        if (!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }

    public Product checkProductNoMnr(VerifyDTO verifyDTO){

        ProductDTO productDTO = new ProductDTO().setMnr(verifyDTO.getMnr())
                .setSn(verifyDTO.getSn())
                .setFd(verifyDTO.getFd())
                .setDepts(a);
        List<Product> list = productMapper.selectProductListNoMnr(productDTO);
        if (!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 校验标签
     *
     * @param product 产品
     * @return 校验结果
     */
    public VerifyResult checkLabelStatus(Product product){
        VerifyRecordDTO verifyRecordDTO = new VerifyRecordDTO();
        verifyRecordDTO.setProductId(product.getId());

        Date beginTime = product.getCreateTime();
        Date updateTime = product.getUpdateTime();
        if(updateTime!=null){
            beginTime = DateUtils.getGreaterDate(beginTime, updateTime);
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("beginTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, beginTime));
        verifyRecordDTO.setParams(params);

        VerifyResult verifyResult = new VerifyResult();
        boolean riskStatus = true;
        if(labelScannerUtils.isOverScanLimit(verifyRecordDTO)){
            verifyResult.setRemark("已扫描"+labelScannerUtils.getVerifyCount()+"次，超过扫描总数限制");
        }else if(labelScannerUtils.isScannedInMultipleLocations(verifyRecordDTO)){
            verifyResult.setRemark("一个月内已在"+labelScannerUtils.getLocations()+"个不同地点扫描，超过扫描次数限制");
        }else if(labelScannerUtils.isOverUserScanLimit(verifyRecordDTO)){
            verifyResult.setRemark("一个小时内已被"+labelScannerUtils.getUsers()+"个用户扫描，超过扫描次数限制");
        }else {
            riskStatus = false;
        }
        verifyResult.setRiskStatus(riskStatus);

        return verifyResult;
    }

    /**
     * 查询ReportData中的相关数据
     *
     * @return ReportData
     */
    public ReportData getReportData(){
        return verifyMapper.selectReportData();
    }

    /**
     * 查询近一周每天参与校验的用户数量
     */
    public List<RecordVo> getWeekCheckedPeople(){
        return verifyMapper.selectWeekCheckedPeople();
    }

    /**
     * 查询近一周每天风险校验的数量
     */
    public List<RecordVo> getWeekRiskRecord(){
        return verifyMapper.getWeekRiskRecord();
    }

    /**
     * 查询近一年每月风险校验的数量
     */
    public List<RecordVo> getYearRiskRecord(){
        return verifyMapper.getYearRiskRecord();
    }

    /**
     * 查询近一周每天校验的数量
     */
    public List<RecordVo> getWeekCheckedRecord(){
        return verifyMapper.getWeekCheckedRecord();
    }

    /**
     * 查询近一年每月校验的数量
     */
    public List<RecordVo> getYearCheckedRecord(){
        return verifyMapper.getYearCheckedRecord();
    }

    /**
     * 查询所有地址数量
     */
    public List<RecordVo> getAddressData(){
        return verifyMapper.getAddressData();
    }

    /**
     * 查询所有省级地址数量
     */
    public List<RecordVo> getAddressByProvince(){
        return verifyMapper.getAddressByProvince();
    }


}