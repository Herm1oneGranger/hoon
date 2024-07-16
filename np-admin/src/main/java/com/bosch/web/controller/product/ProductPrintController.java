package com.bosch.web.controller.product;
import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.web.domain.dto.PrintDTO;
import com.bosch.web.domain.vo.PrintVO;
import com.bosch.web.service.IPrintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("打印信息管理")
@RestController
@RequestMapping("/product/print")
public class ProductPrintController extends BaseController {
    private final String logTitle = "打印信息管理";

    @Autowired
    private IPrintService printService;

    /**
     * 获取打印信息
     */
    @ApiOperation("获取打印列表")
    @GetMapping("/list")
    public TableDataInfo list(PrintDTO printDTO)
    {
        startPage();
        List<PrintVO> list = printService.selectPrintList(printDTO);
        return getDataTable(list);
    }

    @ApiOperation("导出打印列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:print:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PrintDTO printDTO)
    {
        List<PrintVO> list = printService.selectPrintList(printDTO);
        ExcelUtil<PrintVO> util = new ExcelUtil<PrintVO>(PrintVO.class);
        util.exportExcel(response, list, "产品打印数据");
    }

    /**
     * 根据打印编号获取详细信息
     */
    @ApiOperation("获取打印详细")
    @PreAuthorize("@ss.hasPermi('product:print:query')")
    @GetMapping(value = {"/getInfo/{printId}" })
    public AjaxResult getInfo(@PathVariable Long printId)
    {
        return success(printService.selectPrintById(printId));
    }

    /**
     * 导入打印文件
     */
    @ApiOperation("导入打印文件")
    @Log(title = logTitle, businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('product:print:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, String remark) throws Exception
    {
        if(file==null) return error("文件为空");
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".xml")) {
            return error("文件类型错误");
        }
        if (!printService.checkPrintNameUnique(fileName))
        {
            return error("新增文件'" + fileName + "'失败，文件名称已存在");
        }
        return toAjax(printService.insertPrint(file, getUsername()));
    }


    /**
     * 修改打印信息
     */
    @ApiOperation("修改打印信息")
    @PreAuthorize("@ss.hasPermi('product:print:edit')")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody PrintDTO printDTO)
    {
        if (!printService.selectPrintById(printDTO.getId()).getName().equals(printDTO.getName())
                &&!printService.checkPrintNameUnique(printDTO.getName()))
        {
            return error("修改打印文件名'" + printDTO.getName() + "'失败，文件名称已存在");
        }
        printDTO.setUpdateBy(getUsername());
        return toAjax(printService.updatePrint(printDTO));
    }

}