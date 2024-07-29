package com.bosch.web.controller.product;

import com.alibaba.fastjson2.JSON;
import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.R;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.StringUtils;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.web.constant.MsgConstants;
import com.bosch.web.domain.HoneyFeedback;
import com.bosch.web.domain.HoneyVerify;
import com.bosch.web.domain.PImages;
import com.bosch.web.domain.dto.HoneyFeedbackDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.dto.PVerifyDTO;
import com.bosch.web.domain.dto.WxDTO;
import com.bosch.web.domain.vo.HoneyFeedbackVO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.domain.vo.HoneyVerifyVO;
import com.bosch.web.service.HoneyFeedbackService;
import com.bosch.web.service.HoneyVerifyService;
import com.bosch.web.service.PImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼校验")
@RestController
@RequestMapping("/honey/feedback")
public class HoneyFeedBackController extends BaseController {

    private final String logTitle = "霍尼反馈管理";
    @Autowired
    private HoneyFeedbackService service;
    @Autowired
    private PImagesService pImagesService;

    /**
     *
     */
    @ApiOperation("获取列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyFeedbackDTO dto) {
        startPage();
        List<HoneyFeedbackVO> list = service.getList(dto);
        return getDataTable(list);
    }


    /**
     *
     */
    @ApiOperation("新增")

    @PostMapping("/add")
    public AjaxResult add(@RequestBody HoneyFeedbackDTO dto) {

        //校验重复
        //插入
        int i = service.insert(dto);
        System.out.println(JSON.toJSONString(dto));
        return toAjax(i);
    }

    /**
     *
     */
    @ApiOperation("更新")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyFeedbackDTO dto) {
        int i = service.update(dto);
        return toAjax(i);
    }

    @ApiOperation("删除")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(service.deleteAreaByIds(ids));
    }






}