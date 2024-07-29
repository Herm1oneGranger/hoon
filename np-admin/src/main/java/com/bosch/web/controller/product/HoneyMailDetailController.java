package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.HoneyMailDetail;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.service.HoneyMailDetailService;
import com.bosch.web.service.HoneyMailService;
import com.bosch.web.utils.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼校验")
@RestController
@RequestMapping("/honey/mailDetail")
public class HoneyMailDetailController extends BaseController {

    private final String logTitle = "霍尼报警信息管理";
    @Autowired
    private HoneyMailDetailService service;


    @Resource
    private MailUtil mailUtil;

    /**
     *
     */
    @ApiOperation("")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/send")
    public AjaxResult send(@RequestBody HoneyMailDTO dto)   {
        try {

            mailUtil.sendSimpleMail();
        } catch (Exception e) {

            return error(e.getMessage());
        }

        return success();
    }

    /**
     * 获取校验信息
     */
    @ApiOperation("获取列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyMailDetailDTO dto)
    {
        startPage();
        List<HoneyMailDetail> list = service.getList(dto);
        return getDataTable(list);
    }

    @ApiOperation("导出告警信息列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('honey:verify:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, HoneyMailDetailDTO dto) {
//        startPage();
        List<HoneyMailDetail> list = service.getList(dto);
        ExcelUtil<HoneyMailDetail> util = new ExcelUtil<HoneyMailDetail>(HoneyMailDetail.class);
        util.exportExcel(response, list, "告警信息数据");

    }

    /**
     * 新增产品
     */
    @ApiOperation("新增")
    @PreAuthorize("@ss.hasPermi('mail:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody HoneyMailDetailDTO dto) {

        //校验重复
        //插入
        int i = service.insert( dto);
        return toAjax(i);
    }


    @ApiOperation("更新")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyMailDetailDTO dto ) {


        int i = service.update(dto);

        return toAjax(i);
    }



}