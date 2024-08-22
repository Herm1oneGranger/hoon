package com.bosch.web.controller.product;

import com.alibaba.fastjson2.JSON;
import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.web.domain.HoneyFeedback;
import com.bosch.web.domain.dto.HoneyFeedbackDTO;
import com.bosch.web.domain.vo.HoneyFeedbackVO;
import com.bosch.web.service.HoneyFeedbackService;
import com.bosch.web.service.PImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (dto.getId()!=null){
            HoneyFeedback byId = service.getById(dto.getId());
            if (byId!=null){
                if (1==byId.getStatus()){
                    return error("已处理状态无法更新");
                }
            }
        }

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