package com.bosch.web.mapper;

import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.HoneyMailDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_mail_detail】的数据库操作Mapper
* @createDate 2024-07-09 15:48:42
* @Entity com.bosch.web.domain.HoneyMailDetail
*/
public interface HoneyMailDetailMapper extends BaseMapper<HoneyMailDetail> {


    List<HoneyMailDetail> getList(HoneyMailDetailDTO dto);

    int deleteByIds(Integer[] ids);
}




