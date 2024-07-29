package com.bosch.web.service;

import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.HoneyMailDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_mail_detail】的数据库操作Service
* @createDate 2024-07-09 15:48:42
*/
public interface HoneyMailDetailService extends IService<HoneyMailDetail> {

    int insert(HoneyMailDetailDTO dto);


    int update(HoneyMailDetailDTO dto);

    List<HoneyMailDetail> getList(HoneyMailDetailDTO dto);
}
