package com.bosch.web.service;

import com.bosch.web.domain.PVerify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.dto.PVerifyDTO;

/**
* @author GUZ1CGD4
* @description 针对表【p_verify】的数据库操作Service
* @createDate 2024-05-27 12:51:39
*/
public interface PVerifyService extends IService<PVerify> {

    boolean check(PVerifyDTO dto);
}
