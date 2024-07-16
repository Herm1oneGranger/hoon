package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.PImages;
import com.bosch.web.domain.dto.*;
import com.bosch.web.domain.vo.VerifyRecordVO;
import com.bosch.web.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.description.field.FieldList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("霍尼产品类型")
@RestController
@RequestMapping("/honey/type")
public class HoneyTypeController extends BaseController {

    private final String logTitle = "霍尼产品类型管理";
   @Autowired
   private HoneyTypeService honeyTypeService;
    /**
     * 获取校验信息
     */
    @ApiOperation("获取产品列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyTypeDTO dto)
    {
        startPage();
        List<HoneyType> list = honeyTypeService.getList(dto);
        return getDataTable(list);
    }

    /**
     * 新增产品
     */
    @ApiOperation("新增产品信息")
    @PreAuthorize("@ss.hasPermi('product:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody HoneyTypeDTO dto) {

        //校验重复

        //插入

        int i = honeyTypeService.insertHoney( dto);

        return toAjax(i);
    }

    /**
     * 新增产品
     */
    @ApiOperation("更新产品信息")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyTypeDTO dto ) {


        int i = honeyTypeService.updateHoney(dto);

        return toAjax(i);
    }

    @ApiOperation("删除产品信息")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)

    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(honeyTypeService.deleteAreaByIds(ids));
    }
}