package com.bosch.web.controller.product;

import com.bosch.common.annotation.Log;
import com.bosch.common.core.controller.BaseController;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.R;
import com.bosch.common.core.page.TableDataInfo;
import com.bosch.common.enums.BusinessType;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.poi.ExcelUtil;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyProExcel;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.service.HoneyProService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
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
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    @GetMapping("/list")
    public TableDataInfo list(HoneyProDTO dto)
    {
        startPage();
        List<HoneyPro> list = honeyProService.getList(dto);
        return getDataTable(list);
    }

    @ApiOperation("获取产品")
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    @GetMapping("/getByToken")
    public R getByToken(@RequestParam("code") String code) {

        List<HoneyPro> list = honeyProService.getToken(code);
        if (CollectionUtils.isEmpty(list)){
            return R.fail("根据二维码编号："+code+" 未查询到相关产品信息");
        }
        HoneyPro honeyPro = list.get(0);
        return R.ok(honeyPro);
    }

    @ApiOperation("导入")
    @Log(title = "产品管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        if(file==null) return error("文件为空");

        ExcelUtil<HoneyPro> util = new ExcelUtil<HoneyPro>(HoneyPro.class);
        List<HoneyPro> list = util.importExcel(file.getInputStream());

        //赋值
        List<HoneyPro> doList = honeyProService.processHoneyProList(list);
        //校验重复

        String check = honeyProService.checkDuplicates(doList);
        if (check!=null){
            return error(check);
        }

        boolean b = honeyProService.saveBatch(doList);
        return b?success():error();
    }

    @ApiOperation("导出产品列表")
    @Log(title = logTitle, businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody HoneyProDTO dto) {
        List<HoneyPro> list = honeyProService.getList(dto);

        list.forEach(r->
        {
            //域名?token=
            r.setToken("https://setsantifake.honeywell.com.cn/cert/cert?token="+r.getToken());
        });
        ExcelUtil<HoneyPro> util = new ExcelUtil<HoneyPro>(HoneyPro.class);
        util.exportExcel(response, list, "产品列表数据");

    }

    /**
     * 新增产品
     */
    @ApiOperation("新增产品信息")
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    @Log(title = logTitle, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add( @RequestBody HoneyProDTO dto) {

        //校验重复
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);
        List<HoneyPro> doList=new ArrayList<>();
        doList.add(honeyPro);
        String check = honeyProService.checkDuplicates(doList);
        if (check!=null){
            return error("存在重复数据:"+check);
        }

        //插入
        int i = honeyProService.insertHoney( dto);

        return toAjax(i);
    }


    @ApiOperation("更新产品信息")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    public AjaxResult update(@RequestBody HoneyProDTO dto ) {

        //校验重复
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);
        List<HoneyPro> doList=new ArrayList<>();
        doList.add(honeyPro);
        String check = honeyProService.checkDuplicates(doList);
        if (check!=null){
            return error("存在重复数据:"+check);
        }

        int i = honeyProService.updateHoney(dto);

        return toAjax(i);
    }

    @ApiOperation("激活")

    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PostMapping("/updateStatus")
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    public AjaxResult updateStatus(@RequestBody HoneyProDTO dto ) {

        //获取判断是否有已激活
        List<HoneyPro> list = honeyProService.getAToken(dto.getToken());
        if (CollectionUtils.isEmpty(list)){
            int i = honeyProService.updateStatus(dto);

            return i>0?success("激活成功"):success("激活失败，请重试");
        }else {
            return success("产品已激活");
        }

    }

    @ApiOperation("删除产品信息")
    @Log(title = logTitle, businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(honeyProService.deleteAreaByIds(ids));
    }

    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    @PreAuthorize("@ss.hasAnyRoles('administer,admin,scheduling')")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<HoneyProExcel> util = new ExcelUtil<HoneyProExcel>(HoneyProExcel.class);
        util.importTemplateExcel(response, "产品信息");
    }
}