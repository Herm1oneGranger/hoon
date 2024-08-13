package com.bosch.web.controller.product;

import com.alibaba.fastjson2.JSON;
import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.R;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.web.domain.dto.HoneyFeedbackDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.vo.HoneyDashVO;
import com.bosch.web.domain.vo.HoneyFeedbackVO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.service.HoneyFeedbackService;
import com.bosch.web.service.HoneyProService;
import com.bosch.web.service.HoneyVerifyService;
import com.bosch.web.service.PImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼报表")
@RestController
@RequestMapping("/honey/dash")
public class HoneyDashController extends BaseController {

    private final String logTitle = "霍尼报表管理";


    @Autowired
    private HoneyVerifyService service;
    @Autowired
    private HoneyProService proService;
    /**
     *
     */
    @ApiOperation("获取列表1")
    @PostMapping("/list")
    public R list(@RequestBody HoneyVerifyDTO dto) {

        List<HoneyDashVO> list=new ArrayList<>();
        List<HoneyVerifyResultVO> dash = service.getDash(dto);
        if ("月".equals(dto.getDateType())){
            list = service.getMonthlyStats(dash);
        }else if("季度".equals(dto.getDateType())){
            list = service.getQuarterlyStats(dash);
        }else if("年".equals(dto.getDateType())){
            list = service.getYearlyStats(dash);
        }


        return R.ok(list);
    }

    @ApiOperation("获取列表2")
    @PostMapping("/list2")
    public R list2(@RequestBody HoneyVerifyDTO dto) {

        List<HoneyDashVO> list=new ArrayList<>();
        // 假设dto.getCreateTime()返回一个Date对象
        Date createTime = dto.getCreateTime();
        // 创建SimpleDateFormat对象，并设置所需的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 将createTime格式化为字符串
        String formattedDate = dateFormat.format(createTime);
        dto.setCreateTimeFormat(formattedDate);
        List<HoneyVerifyResultVO> dash = service.getDash2(dto);

            list = service.getMonthlyStats(dash);


        return R.ok(list);
    }
    @ApiOperation("获取年")
    @GetMapping("/selectYear")
    public R selectYear() {

        List<String> list = proService.selectYear();
        return R.ok(list);
    }
    @ApiOperation("通过产品获取生产时间")
    @GetMapping("/selectDate")
    public R selectDate(@RequestParam String materialCode) {

        List<String> list = proService.selectDate(materialCode);
        return R.ok(list);
    }




}