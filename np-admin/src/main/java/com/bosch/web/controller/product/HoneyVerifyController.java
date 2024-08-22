package com.bosch.web.controller.product;

import com.alibaba.fastjson2.JSON;
import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.R;
import com.bosch.common.core.domain.model.LoginUser;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.StringUtils;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.framework.web.service.TokenService;
import com.bosch.web.constant.MsgConstants;
import com.bosch.web.domain.HoneyVerify;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.dto.PVerifyDTO;
import com.bosch.web.domain.dto.PicDTO;
import com.bosch.web.domain.dto.WxDTO;
import com.bosch.web.domain.vo.ApiResponse;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.domain.vo.HoneyVerifyVO;
import com.bosch.web.service.HoneyMailDetailService;
import com.bosch.web.service.HoneyVerifyService;
import com.bosch.web.service.PImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Value("${api.path-url}")
    private    String baseUrl;
    @Autowired
    private HoneyVerifyService service;

    @Autowired
    private PImagesService pImagesService;
    private static final String[] RESULTS = {MsgConstants.FAKE, MsgConstants.TRUE, MsgConstants.MANUAL, MsgConstants.Retry};
    private static final AtomicInteger INDEX = new AtomicInteger(0);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HoneyMailDetailService detailService;
    /**
     *
     */
    @ApiOperation("获取校验结果列表")
    @GetMapping("/list")
    public TableDataInfo list(HoneyVerifyDTO dto) {
        startPage();
        List<HoneyVerifyResultVO> list = service.getList(dto);
        return getDataTable(list);
    }

    @ApiOperation("导出校验列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('honey:verify:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody HoneyVerifyDTO dto) {
//        startPage();
        List<HoneyVerifyResultVO> list = service.getList(dto);
        ExcelUtil<HoneyVerifyResultVO> util = new ExcelUtil<HoneyVerifyResultVO>(HoneyVerifyResultVO.class);
        util.exportExcel(response, list, "校验信息数据");

    }

    /**
     *
     */
    @ApiOperation("新增校验结果")
    @PreAuthorize("@ss.hasPermi('product:add')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody HoneyVerifyDTO dto) {

        //校验重复
        //插入
        int i = service.insertHoney(dto);

        return toAjax(i);
    }

    /**
     *
     */
    @ApiOperation("更新校验结果")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody HoneyVerifyDTO dto) {
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
    public AjaxResult getVerify(@RequestBody HoneyVerifyDTO dto) {
        HoneyVerifyVO algoVerify = service.algoVerify(dto);
        return success(algoVerify);
    }


//    @ApiOperation("标签算法校验")
//    @PostMapping("/verify")
//    public AjaxResult verify(@RequestBody PVerifyDTO dto) {
//        HoneyVerifyVO vo = new HoneyVerifyVO();
//        // 示例 Base64 字符串
//        String base64Str = dto.getImgBase();
//        // 去掉头部信息
//        base64Str = base64Str.substring(base64Str.indexOf(",") + 1);
//        // 生成文件名
//        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
//        // 文件保存路径
//        String filePath = baseUrl+"/pic" + fileName;
//        // 检查并创建文件夹
//        File file = new File(filePath);
//        File dir = file.getParentFile();
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        // 解码Base64字符串
//        byte[] imageBytes = Base64.getDecoder().decode(base64Str);
//        // 判断token在不在库里
//
//        HoneyVerifyVO checkToken = service.checkToken(dto);
//        if (StringUtils.isNotEmpty(checkToken.getResult())) {
//            return success(checkToken);
//        }
//
//
//        //iqa校验
//        HoneyVerifyVO iqa = service.iqa(dto);
//
//        if (StringUtils.isNotEmpty(iqa.getResult()) || iqa.getResult().equals(MsgConstants.IQASUCCESS)) {
//            return success(iqa);
//        }
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(imageBytes);
//            // 保存图片的 地址
//            PImages image = new PImages();
//            image.setBase(filePath);
//            PImages pImages = pImagesService.insertImg(image);// 插入操作后，image 对象的 id 属性会被自动填充
//            // 获取生成的图片 ID
//            Integer imageId = pImages.getId();
//            vo.setMsg("二维码校验通过");
//            vo.setResult("1");
//            return success(vo);
//
//        } catch (IOException e) {
//
//            return error(e.getMessage());
//        }
//
//    }

    @ApiOperation("标签算法校验")
//    @Log(title = "标签算法校验verify1", businessType = BusinessType.INSERT)
    @PostMapping("/verify1")
    public AjaxResult verify1(HttpServletRequest request ,@RequestBody PVerifyDTO dto) {

        HoneyVerifyVO vo = new HoneyVerifyVO();

        // 示例 Base64 字符串
        String base64Str = dto.getImgBase();
        // 去掉头部信息
        base64Str = base64Str.substring(base64Str.indexOf(",") + 1);
        // 生成文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        // 文件保存路径
        String filePath = baseUrl+"/verify/" + fileName;
        String showPath = "/images/"+fileName;
        // 检查并创建文件夹
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 判断token在不在库里
        HoneyVerifyVO checkToken = service.checkToken(dto);
        if (StringUtils.isNotEmpty(checkToken.getResult())) {
            logger.info("checkToken:"+dto.getToken() +" 不在库中");
            return success(checkToken);
        }

        // 解码Base64字符串
        byte[] imageBytes = Base64.getDecoder().decode(base64Str);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageBytes);

            //iqa校验包含在算法中
            HoneyVerifyDTO honeyVerifyDTO = BeanConverUtil.conver(dto, HoneyVerifyDTO.class);
            honeyVerifyDTO.setPic(showPath);
            //算法校验
            HoneyVerifyVO honeyVerifyVO = service.algoVerify(honeyVerifyDTO);
            honeyVerifyDTO.setAlgoResults(honeyVerifyVO.getResult());

            //纹理校验
            HoneyVerifyVO validate = service.getValidate(filePath, "1");
            if(MsgConstants.IQAFAILED.equals(validate.getResult())){
                return success(validate);
            }
            honeyVerifyDTO.setTextureResults(validate.getResult());

            //合并校验结果
            String totalResult = service.getTotalResult(honeyVerifyDTO.getAlgoResults(), honeyVerifyDTO.getTextureResults());
            if(totalResult==null){
                return error("合并纹理校验结果与用户算法校验结果异常");
            }
            honeyVerifyDTO.setTotalResults(totalResult);
            //校验不记录
            LoginUser loginUser = tokenService.getLoginUser(request);
            HoneyVerify honeyVerify=new HoneyVerify();
            if (null==loginUser){
                honeyVerify = service.insertHoneyGetId(honeyVerifyDTO);
                vo.setReocrdId(honeyVerify.getId().intValue());
                logger.info("扫码校验：", JSON.toJSONString(vo));
            }

            logger.info("激活校验：", JSON.toJSONString(vo));
            return success(vo);

        } catch (IOException e) {
            return error(e.getMessage());
        }

    }

    @ApiOperation(value = "获取openid")
    @PostMapping(value = "/getOpenId")
    public R getOpenId(@RequestBody WxDTO dto) {
        StringBuffer info = new StringBuffer(WxDTO.WX_LINK);
        info.append("appid=").append(WxDTO.APP_ID).append("&");
        info.append("secret=").append(WxDTO.SECRET).append("&");
        info.append("js_code=").append(dto.getCode()).append("&");
        info.append("grant_type=").append("authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String strbody = restTemplate.exchange(info.toString().replace("\"", ""), HttpMethod.GET, entity, String.class)
                .getBody();

        return R.ok(JSON.parseObject(strbody));
    }


    @PostMapping("/upload")
    @ApiOperation(value = "upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        try {

            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // 指定保存路径
            String filePath =baseUrl+ "/verify/"+fileName+originalFilename;
            // 检查并创建文件夹
            File fileP = new File(filePath);
            File dir = fileP.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 文件保存路径
            String savePath = filePath ;
            //文件展示路径
            String showPath = "/images/"+fileName+originalFilename;
            // 保存文件
            file.transferTo(new File(savePath));
            // 返回图片路径
            return R.ok(showPath);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail("上传失败");
        }
    }

    @ApiOperation("采图上传base64")
    @PostMapping("/upload64")
    public AjaxResult upload64(@RequestBody PicDTO dto) {

        // 示例 Base64 字符串
        String base64Str = dto.getImgBase();
        // 去掉头部信息
        base64Str = base64Str.substring(base64Str.indexOf(",") + 1);
        // 生成文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        // 文件保存路径
       String filePath = baseUrl+"/caitu/" + fileName;
       // String filePath =  "/home/soroot/dc/nameplate/caitu/"+ fileName;
        String showPath = "/images/"+fileName;
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
            logger.info("采图完成：", filePath);
            //todo iqa
        } catch (IOException e) {
            return error(e.getMessage());
        }
            logger.info("开始iqa：", filePath);
            //iqa校验
            ApiResponse apiResponse = service.validateImage(filePath, "0");
            if (apiResponse==null){
                return error("调用iqa校验接口失败");
            }
            if (apiResponse!=null&&apiResponse.getCode()==0){
//                vo.setMsg(apiResponse.getMessage());
//                vo.setResult(MsgConstants.IQAFAILED);
                return error(apiResponse.getMessage());
            }

            return success();



    }
}