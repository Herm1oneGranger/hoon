package com.bosch.web.controller.product;

import com.bosch.common.core.domain.R;
import com.bosch.web.domain.vo.RecordVo;
import com.bosch.web.domain.vo.ReportData;
import com.bosch.web.service.IPrintService;
import com.bosch.web.service.IProductService;
import com.bosch.web.service.IVerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("首页")
@CrossOrigin("*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final String logTitle = "首页Dashboard";

    @Autowired
    private IVerifyService verifyService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IPrintService printService;

    /**
     * 总数统计
     * 统计:
     * 1. 激活个数
     * 2. 参与用户
     * 3. 校验次数
     * 4. 风险次数
     */
    @ApiOperation("统计总数(激活个数, 参与用户, 校验次数, 风险次数)")
    @GetMapping("/getTotalCount")
    public R getTotalCount() {
        ReportData reportData;
        try {
            reportData = verifyService.getReportData();
            Map<String, Long> data = new HashMap<>();

            data.put("allCheckedRecord", reportData.getAllCheckedRecord());
            data.put("allCheckedPeople", reportData.getAllCheckedPeople());
            data.put("allActivatedLabel", productService.allActivatedLabel());
            data.put("allRiskRecord", reportData.getAllRiskRecord());
            return R.ok(data);
        } catch (Exception e) {
            return R.fail("getReportData信息出错了");
        }
    }

    /**
     * 近一周产品被激活数
     */
    @ApiOperation("近一周产品被激活数")
    @GetMapping("/getWeekActivatedLabel")
    public R getWeekActivatedLabel() {
        List<RecordVo> recordList = printService.getWeekActivatedLabel();
        return R.ok(recordList);
    }

    /**
     * 近一周每天参与用户数
     */
    @ApiOperation("近一周每天参与用户数")
    @GetMapping("/getWeekCheckedPeople")
    public R getWeekCheckedPeople() {
        List<RecordVo> recordList = verifyService.getWeekCheckedPeople();
        return R.ok(recordList);
    }

    /**
     * 近一周出现风险记录
     */
    @ApiOperation("近一周出现风险记录")
    @GetMapping("/getWeekRiskRecord")
    public R getWeekFake() {
        List<RecordVo> recordList = verifyService.getWeekRiskRecord();
        return R.ok(recordList);
    }

    /**
     * 近12个月风险次数
     *
     * @return
     */
    @ApiOperation("近一年出现风险记录的数量统计")
    @GetMapping("/getYearRiskRecord")
    public R getYearFake() {
        List<RecordVo> recordList = verifyService.getYearRiskRecord();
        return R.ok(recordList);
    }

    /**
     * 校验地址数据Table
     *
     * @return
     */
    @ApiOperation("校验地址数据Table")
    @GetMapping("/getAddressData")
    public R getRecordList() {
        List<RecordVo> recordList = verifyService.getAddressData();
        return R.ok(recordList);
    }

    /**
     * 校验地址柱状图数据
     */
    @ApiOperation("校验地址柱状图数据")
    @GetMapping("/getAddressByProvince")
    public R getBarData() {
        List<RecordVo> recordList = verifyService.getAddressByProvince();
        return R.ok(recordList);
    }

    /**
     * 近一周每天校验数量
     */
    @ApiOperation("近一周每天校验数量")
    @GetMapping("/getWeekCheckedRecord")
    public R getWeekCheckedCount() {
        List<RecordVo> recordList = verifyService.getWeekCheckedRecord();
        return R.ok(recordList);
    }

    /**
     * 近一年每月校验频率
     */
    @ApiOperation("近一年每月校验频率")
    @GetMapping("/getYearCheckedRecord")
    public R getYearCheckedCount() {
        List<RecordVo> recordList = verifyService.getYearCheckedRecord();
        return R.ok(recordList);
    }

}
