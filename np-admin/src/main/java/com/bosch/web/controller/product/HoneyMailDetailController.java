package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.dto.HoneyMailDTO;
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
    private HoneyMailService service;


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




}