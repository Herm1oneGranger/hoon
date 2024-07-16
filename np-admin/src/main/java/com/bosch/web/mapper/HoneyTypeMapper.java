package com.bosch.web.mapper;

import com.bosch.web.domain.HoneyType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.dto.HoneyTypeDTO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_type】的数据库操作Mapper
* @createDate 2024-06-11 10:56:39
* @Entity com.bosch.web.domain.HoneyType
*/
public interface HoneyTypeMapper extends BaseMapper<HoneyType> {

    List<HoneyType> getList(HoneyTypeDTO dto);

    int deleteAreaByIds(Integer[] ids);
}




