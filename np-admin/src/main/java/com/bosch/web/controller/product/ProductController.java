package com.bosch.web.controller.product;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.bosch.common.annotation.Log;
import com.bosch.common.constant.ProductConstants;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.R;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.framework.web.domain.server.SysFile;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.ProductExcel;
import com.bosch.web.domain.dto.ProductAddDTO;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.dto.ProductEditDTO;
import com.bosch.web.service.IProductService;
import com.bosch.web.utils.EasyExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @Author: pink
 * @Description: 产品管理 Controller 层
 * @Date: 10/19/2023 4:38 PM
 */
@Api("产品信息管理")
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {
    private final String logTitle = "产品信息管理";

    @Autowired
    private IProductService productService;

    /**
     * 获取产品信息
     */
    @ApiOperation("获取产品信息列表")
    @GetMapping("/list")
    public TableDataInfo list(ProductDTO productDTO) {
        startPage();
        List<Product> list = productService.selectProductList(productDTO);
        return getDataTable(list);
    }

    @ApiOperation("导出打印列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductDTO productDTO) {
        List<Product> list = productService.selectProductList(productDTO);
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        util.exportExcel(response, list, "产品数据");
    }

    /**
     * 根据产品编号获取产品信息
     */
    @ApiOperation("获取产品信息详细")
    @PreAuthorize("@ss.hasPermi('product:query')")
    @GetMapping(value = {"/getInfo/{productId}"})
    public AjaxResult getInfo(@PathVariable Long productId) {
        return success(productService.selectProductVoById(productId));
    }

    /**
     * 新增产品
     */
    @ApiOperation("新增产品信息")
    @PreAuthorize("@ss.hasPermi('product:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody ProductAddDTO productAddDTO) {
        ProductDTO productDTO = BeanConverUtil.conver(productAddDTO, ProductDTO.class);
        if (!productService.checkProductUnique(productDTO)) {
            return error("新增产品(MNR: " + productDTO.getMnr()
                    + ", FD: "+ productDTO.getFd()
                    +", SN: "+ productDTO.getSn()
                    + ")失败，产品已存在");
        }
        productDTO.setCreateBy(getUsername());
        return toAjax(productService.insertProduct(productDTO));
    }

    /**
     * 修改产品信息
     * status可以是开或者关
     * verifyStatus人工可以改为正常或风险，但不可以改为未校验
     * 未校验时不可以改为正常或风险。
     */
    @ApiOperation("修改产品信息")
    @PreAuthorize("@ss.hasPermi('product:edit')")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody ProductEditDTO productEditDTO) {
        Product oldProduct = productService.selectProductById(productEditDTO.getId());
        String oldVerifyStatus = oldProduct.getVerifyStatus();
        String newVerifyStatus = productEditDTO.getVerifyStatus();

        if(!Objects.equals(oldVerifyStatus, ProductConstants.VERIFY_UNCHECK)
                && Objects.equals(newVerifyStatus, ProductConstants.VERIFY_UNCHECK)){
            return error("不可以将校验状态改回未校验");
        }
        if (Objects.equals(oldVerifyStatus, ProductConstants.VERIFY_UNCHECK)
                && !Objects.equals(newVerifyStatus, ProductConstants.VERIFY_UNCHECK)){
            return error("不可改变未校验的产品的校验状态");
        }

        ProductDTO productDTO = BeanConverUtil.conver(productEditDTO, ProductDTO.class);
        productDTO.setUpdateBy(getUsername());

        return toAjax(productService.updateProduct(oldProduct, productDTO));
    }

    @ApiOperation("批量新增")
    @PostMapping(value = "/productImport")
    public  R masterDataImport(@RequestPart(value = "file") MultipartFile file) throws Exception {
        try {

            EasyExcelUtil.headerRead(file.getInputStream());
            List<ProductExcel> read = EasyExcelUtil.read(file.getInputStream());
            //有唯一键 重复值直接忽略
            int i = productService.insertProduct(read);

            return R.ok(i);
        } catch (Exception e) {

            return R.fail(e.getMessage());
        }

    }

}