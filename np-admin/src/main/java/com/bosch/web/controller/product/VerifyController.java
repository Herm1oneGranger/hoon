package com.bosch.web.controller.product;

import com.alibaba.fastjson2.JSON;
import com.bosch.common.annotation.Log;
import com.bosch.common.constant.Constants;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.R;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.StringUtils;
import com.bosch.common.utils.file.FileUploadUtils;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.common.utils.uuid.IdUtils;
import com.bosch.web.core.config.ProjectConfig;
import com.bosch.web.domain.PImages;
import com.bosch.web.domain.dto.*;
import com.bosch.web.domain.vo.HoneyVerifyVO;
import com.bosch.web.domain.vo.VerifyRecordVO;

import com.bosch.web.service.IProductService;
import com.bosch.web.service.IVerifyService;
import com.bosch.web.service.PImagesService;
import com.bosch.web.service.PVerifyService;
import com.bosch.web.utils.MultipartFileUtils;
import com.bosch.web.utils.ThreadPoolExecutorTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Author: pink
 * @Description:
 * @Date: 10/19/2023 4:38 PM
 */
@Api("校验管理")
@RestController
@RequestMapping("/product/verify")
public class VerifyController extends BaseController {

    private final String logTitle = "校验管理";

    @Autowired
    private IVerifyService verifyService;

    @Autowired
    private IProductService productService;

    @Autowired
    private PVerifyService pVerifyService;

    @Autowired
    private PImagesService pImagesService;
    /**
     * 获取校验信息
     */
    @ApiOperation("获取校验列表")
    @GetMapping("/list")
    public TableDataInfo list(VerifyRecordDTO verifyRecordDTO)
    {
        startPage();
        List<VerifyRecordVO> list = verifyService.selectVerifyRecordList(verifyRecordDTO);
        return getDataTable(list);
    }

    /**
     * 导出校验列表
     */
    @ApiOperation("导出校验列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('product:verify:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, VerifyRecordDTO verifyRecordDTO)
    {
        List<VerifyRecordVO> list = verifyService.selectVerifyRecordList(verifyRecordDTO);
        ExcelUtil<VerifyRecordVO> util = new ExcelUtil<VerifyRecordVO>(VerifyRecordVO.class);
        util.exportExcel(response, list, "校验信息数据");
    }

    /**
     * 校验产品真伪
     */
    @ApiOperation("校验产品真伪")
    @PostMapping("/check")
    public AjaxResult check(@RequestBody PVerifyDTO dto)
    {
        boolean check = pVerifyService.check(dto);
        return success();
    }


    @ApiOperation("标签算法校验")
    @PostMapping("/verify")
    public AjaxResult verify(@RequestBody PVerifyDTO dto) {
        HoneyVerifyVO vo=new HoneyVerifyVO();
        // 示例 Base64 字符串
        String base64Str = dto.getImgBase();
        // 去掉头部信息
        base64Str = base64Str.substring(base64Str.indexOf(",") + 1);
        // 生成文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";

        // 文件保存路径
        String filePath = "/home/soroot/dc/nameplate/" + fileName;

        // 检查并创建文件夹
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 解码Base64字符串
        byte[] imageBytes = Base64.getDecoder().decode(base64Str);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageBytes);
            // 保存图片的 地址
            PImages image=new PImages();
            image.setBase(filePath);
            PImages pImages = pImagesService.insertImg(image);// 插入操作后，image 对象的 id 属性会被自动填充
            // 获取生成的图片 ID
            Integer imageId = pImages.getId();
            vo.setMsg("二维码校验通过");
            vo.setResult("1");

            return success(vo);

        } catch (IOException e) {

            return error(e.getMessage());
        }

    }
}