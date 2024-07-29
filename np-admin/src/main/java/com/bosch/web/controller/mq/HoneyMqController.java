package com.bosch.web.controller.mq;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.service.HoneyMailService;
import com.bosch.web.utils.RocketMQProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼校验")
@RestController
@RequestMapping("/honey/mq")
public class HoneyMqController extends BaseController {

    private final String logTitle = "";
    @Autowired
    private RocketMQProducer producer;

    @PostMapping("/send")
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        for (int i = 0; i < 10; i++) {
            producer.sendMessage("my-topic","",String.valueOf(i));

        }


    }
}