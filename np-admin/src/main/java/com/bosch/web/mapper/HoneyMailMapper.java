package com.bosch.web.mapper;

import com.bosch.web.domain.HoneyMail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.domain.dto.HoneyProDTO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_mail】的数据库操作Mapper
* @createDate 2024-07-02 13:35:24
* @Entity com.bosch.web.domain.HoneyMail
*/
public interface HoneyMailMapper extends BaseMapper<HoneyMail> {

    List<HoneyMail> getList(HoneyMailDTO dto);

    int deleteByIds(Integer[] ids);
}




