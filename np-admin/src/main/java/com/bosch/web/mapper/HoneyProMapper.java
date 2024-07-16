package com.bosch.web.mapper;

import com.bosch.web.domain.HoneyPro;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.domain.dto.HoneyTypeDTO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_pro(产品信息)】的数据库操作Mapper
* @createDate 2024-06-13 14:21:54
* @Entity com.bosch.web.domain.HoneyPro
*/
public interface HoneyProMapper extends BaseMapper<HoneyPro> {

    List<HoneyPro> getList(HoneyProDTO dto);

    int deleteAreaByIds(Integer[] ids);
}




