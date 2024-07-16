package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.HoneyVerify;
import com.bosch.web.domain.dto.HoneyTypeDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.domain.vo.HoneyVerifyVO;
import com.bosch.web.service.HoneyTypeService;
import com.bosch.web.service.HoneyVerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼校验")
@RestController
@RequestMapping("/honey/verify")
public class HoneyVerifyController extends BaseController {

    private final String logTitle = "霍尼校验结果管理";
   @Autowired
   private HoneyVerifyService service;
    /**
     * 获取校验信息
     */
    @ApiOperation("获取校验结果列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyVerifyDTO dto)
    {
        startPage();
        List<HoneyVerifyResultVO> list = service.getList(dto);
        return getDataTable(list);
    }

    /**
     * 新增产品
     */
    @ApiOperation("新增校验结果")
    @PreAuthorize("@ss.hasPermi('product:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody HoneyVerifyDTO dto) {

        //校验重复

        //插入

        int i = service.insertHoney( dto);

        return toAjax(i);
    }

    /**
     * 新增产品
     */
    @ApiOperation("更新校验结果")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyVerifyDTO dto ) {


        int i = service.updateHoney(dto);

        return toAjax(i);
    }

    @ApiOperation("删除校验结果")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(service.deleteAreaByIds(ids));
    }

    @ApiOperation("算法防伪")
    @PostMapping("/getVerify")
    public AjaxResult getVerify(@RequestBody HoneyVerifyDTO dto)
    {
        HoneyVerifyVO algoVerify= service.algoVerify(dto);
        return success(algoVerify);
    }
}