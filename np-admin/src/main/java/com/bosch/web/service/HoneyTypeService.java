package com.bosch.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyTypeDTO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_type】的数据库操作Service
* @createDate 2024-06-11 10:56:39
*/
public interface HoneyTypeService extends IService<HoneyType> {


     int insertHoney(HoneyTypeDTO dto);


    int updateHoney(HoneyTypeDTO dto);
    String valid(HoneyTypeDTO dto);
    List<HoneyType> getList(HoneyTypeDTO dto);

    public int deleteAreaByIds(Integer[] ids);
}
