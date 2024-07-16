package com.bosch.web.service;

import com.bosch.web.domain.HoneyMail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_mail】的数据库操作Service
* @createDate 2024-07-02 13:35:24
*/
public interface HoneyMailService extends IService<HoneyMail> {

    void send();
    int insertHoney(HoneyMailDTO dto);


    int updateHoney(HoneyMailDTO dto);

    List<HoneyMail> getList(HoneyMailDTO dto);

    public int deleteAreaByIds(Integer[] ids);

}
