package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.service.HoneyMailService;
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
@RequestMapping("/honey/mail")
public class HoneyMailController extends BaseController {

    private final String logTitle = "霍尼邮箱信息管理";
   @Autowired
   private HoneyMailService service;
    /**
     * 获取校验信息
     */
    @ApiOperation("获取列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyMailDTO dto)
    {
        startPage();
        List<HoneyMail> list = service.getList(dto);
        return getDataTable(list);
    }

    /**
     * 新增产品
     */
    @ApiOperation("新增")
    //@PreAuthorize("@ss.hasPermi('mail:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody HoneyMailDTO dto) {

        //校验重复
        //插入
        int i = service.insertHoney( dto);
        return toAjax(i);
    }

    /**
     * 新增产品
     */
    @ApiOperation("更新")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyMailDTO dto ) {


        int i = service.updateHoney(dto);

        return toAjax(i);
    }

    @ApiOperation("删除")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(service.deleteAreaByIds(ids));
    }


}