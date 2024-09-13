package com.bosch.framework.web.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.bosch.common.constant.CacheConstants;
import com.bosch.common.constant.Constants;
import com.bosch.common.constant.UserConstants;
import com.bosch.common.core.domain.entity.SysUser;
import com.bosch.common.core.domain.model.LoginUser;
import com.bosch.common.core.redis.RedisCache;
import com.bosch.common.exception.ServiceException;
import com.bosch.common.exception.user.*;
import com.bosch.common.utils.DateUtils;
import com.bosch.common.utils.MessageUtils;
import com.bosch.common.utils.StringUtils;
import com.bosch.common.utils.ip.IpUtils;
import com.bosch.framework.config.AdfsConfig;
import com.bosch.framework.manager.AsyncManager;
import com.bosch.framework.manager.factory.AsyncFactory;
import com.bosch.framework.security.context.AuthenticationContextHolder;
import com.bosch.system.service.ISysConfigService;
import com.bosch.system.service.ISysUserService;
import com.bosch.system.service.impl.SysUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdfsConfig  adfsConfig;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        // 验证码校验
//        validateCaptcha(username, code, uuid);
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally
        {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }


    /**
     * bosch 通过Azure AD验证进行单点登录
     * @param code
     */
    public void ssoLogin(String code, HttpServletResponse response) throws IOException {
//        try {
//            RestTemplate restTemplate =new RestTemplate();
//            String authHeader = "Basic " + new String(Base64Utils.encode((adfsConfig.getClientId() + ":" + adfsConfig.getClientSecret()).getBytes()));
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.set("Authorization", authHeader);
//
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("grant_type", "authorization_code");
//            params.add("code", code);
//            params.add("redirect_uri", "https://setsantifake.honeywell.com.cn/ssoLogin");
//
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//            ResponseEntity<String> result = restTemplate.postForEntity("https://authn.honeywell.com/as/token.oauth2", request, String.class);
//
//            if (result.getStatusCode() == HttpStatus.OK) {
//                // TODO: 解析响应体以获取 access_token
//                String responseBody = result.getBody();
//                // 这里需要根据实际响应体格式解析 access_token
//
//            } else {
//                // 处理错误情况
//                System.out.println("Error: " + result.getStatusCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        System.out.println("sso code: "+code);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("client_id", adfsConfig.getClientId());
        paramMap.put("client_secret", adfsConfig.getClientSecret());
        paramMap.put("grant_type", adfsConfig.getGrantTypeAuthorizationCode());
        paramMap.put("code", code);
        paramMap.put("redirect_uri", "https://setsantifake.honeywell.com.cn/api/ssoLogin");
        System.out.println(paramMap);
        String responseStr = HttpUtil.post(adfsConfig.getTokenEndpoint(), paramMap);
        System.out.println(responseStr);
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        if (jsonObject.containsKey("access_token")) {
            String accessToken = jsonObject.get("access_token").toString();
            Map<String, String > heads = new HashMap<>(10);
            // 使用json发送请求，下面的是必须的
            heads.put("Authorization","Bearer "+ accessToken);
            String userInoResStr = HttpRequest.get(adfsConfig.getUserInfoUrl())
                    .headerMap(heads, false)
                    .timeout(20000) //有个坑超过21秒会导致失效，注意
                    .execute().body();
            JSONObject userInoResObject = JSONObject.parseObject(userInoResStr);
            if (userInoResObject.containsKey("sub")) {
                String ntAccount = userInoResObject.get("sub").toString();
                String ming = userInoResObject.get("given_name").toString();
                String xing = userInoResObject.get("family_name").toString();
                String email = userInoResObject.get("email").toString();

                SysUser user = userService.selectUserByUserName(ntAccount);
                if (user != null) {
                    LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
                    recordLoginInfo(loginUser.getUserId());
                    // 生成token

                    String token = tokenService.createToken(loginUser);
                    log.info("SsoLogin:" + "token=" + token);
                    response.sendRedirect("https://setsantifake.honeywell.com.cn/ssoLogin" + "?token=" + token);

//                    response.sendRedirect(loginUrl + "?token=" + token);
                } else {
                    log.error("Can not find user in system！: \n" + ntAccount);
//                    response.sendRedirect(noRoleUrl);
                    //throw new ServiceException("账号："+ntAccount+"不在库中，请联系管理员添加");
                    response.sendRedirect("https://setsantifake.honeywell.com.cn/401" + "?message=" + "Account: "+ntAccount+" does not exist, please contact the administrator to add it");
                }

//                String ntAccount = userInoResObject.get("sub").toString().split("@")[0];
//                SysUser user = userService.selectUserByUserName(ntAccount);
//                if (user != null) {
////                    LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
//                    recordLoginInfo(loginUser.getUserId());
//                    // 生成token
//                    String token = tokenService.createToken(loginUser);
////                    log.info("SsoLogin:" + loginUrl + "?token=" + token);
//                    response.sendRedirect(loginUrl + "?token=" + token);
//                } else {
////                    log.error("Can not find user in system！\n" + jsonObject.get("error_description"));
//                    response.sendRedirect(noRoleUrl);
//                }
            } else {
                log.error("sso获取userInfo失败！\n" + jsonObject.get("error_description"));
//                response.sendRedirect("noRoleUrl");
                throw new ServiceException("Failed to obtain user info!");
            }
        }
        else if (jsonObject.containsKey("error"))
        {
            log.error("sso登录失败！\n" + jsonObject.get("error").toString()+":"+jsonObject.get("error_description").toString());
            throw new ServiceException(jsonObject.get("error_description").toString());
        }
        else {
            log.error("sso登录失败！\n" + jsonObject.get("error_description"));
            //response.sendRedirect("noRoleUrl");
            throw new ServiceException("Failed to obtain Microsoft AD token!");
        }
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha))
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
