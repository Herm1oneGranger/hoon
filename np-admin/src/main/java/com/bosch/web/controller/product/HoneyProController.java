package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.domain.dto.HoneyTypeDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.service.HoneyProService;
import com.bosch.web.service.HoneyTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼产品类型")
@RestController
@RequestMapping("/honey/product")
public class HoneyProController extends BaseController {

    private final String logTitle = "霍尼产品管理";


    @Autowired
    private HoneyProService honeyProService;


    /**
     * 获取校验信息
     */
    @ApiOperation("获取产品列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyProDTO dto)
    {
        startPage();
        List<HoneyPro> list = honeyProService.getList(dto);
        return getDataTable(list);
    }


    @ApiOperation("导出产品列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('honey:verify:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, HoneyProDTO dto) {
//        startPage();
        List<HoneyPro> list = honeyProService.getList(dto);
        ExcelUtil<HoneyPro> util = new ExcelUtil<HoneyPro>(HoneyPro.class);
        util.exportExcel(response, list, "产品列表数据");

    }

    /**
     * 新增产品
     */
    @ApiOperation("新增产品信息")
    @PreAuthorize("@ss.hasPermi('product:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add( @RequestBody HoneyProDTO dto) {

        //校验重复

        //插入
        int i = honeyProService.insertHoney( dto);

        return toAjax(i);
    }

    /**
     * 新增产品
     */
    @ApiOperation("更新产品信息")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyProDTO dto ) {
        int i = honeyProService.updateHoney(dto);

        return toAjax(i);
    }

    @ApiOperation("激活")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody HoneyProDTO dto ) {

        int i = honeyProService.updateStatus(dto);

        return toAjax(i);
    }

    @ApiOperation("删除产品信息")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)

    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(honeyProService.deleteAreaByIds(ids));
    }
}