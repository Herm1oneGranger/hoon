package com.bosch.web.controller.system;

import com.bosch.common.constant.Constants;
import com.bosch.common.core.domain.AjaxResult;
import com.bosch.common.core.domain.entity.SysMenu;
import com.bosch.common.core.domain.entity.SysUser;
import com.bosch.common.core.domain.model.LoginBody;
import com.bosch.common.utils.SecurityUtils;
import com.bosch.framework.web.service.SysLoginService;
import com.bosch.framework.web.service.SysPermissionService;
import com.bosch.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 单点登录 SSO
     */
    @GetMapping ("/ssoLogin")
    public void ssoLogin(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        System.out.println("sso登录开始 code :"+code);
        String getCode="https://authn.honeywell.com/as/authorization.oauth2?client_id=Client_bvva92fcse2u&scope=email+openid+profile&response_type=code&redirect_uri=https://setsantifake.honeywell.com.cn/ssoLogin";
        loginService.ssoLogin(code, response);
        //String token = loginService.ssoLogin(code, response);
//        AjaxResult ajax = AjaxResult.success();
//
//
//        ajax.put(Constants.TOKEN, token);
//        return ajax;
    }
    @GetMapping ("/ssoLogin1")
    public void ssoLogin1(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        System.out.println("sso登录开始 code :"+code);
        response.sendRedirect("https://www.baidu.com");;
        //response.sendRedirect("https://authn.honeywell.com/as/authorization.oauth2?client_id=Client_bvva92fcse2u&scope=email+openid+profile&response_type=code&redirect_uri=https://setsantifake.honeywell.com.cn/api/ssoLogin");;
    }
    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
