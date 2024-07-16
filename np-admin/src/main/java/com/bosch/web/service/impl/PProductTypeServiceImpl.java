package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.web.domain.PProductType;
import com.bosch.web.service.PProductTypeService;
import com.bosch.web.mapper.PProductTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author ZPO2SGH
* @description 针对表【p_product_type】的数据库操作Service实现
* @createDate 2023-11-10 09:49:13
*/
@Service
public class PProductTypeServiceImpl extends ServiceImpl<PProductTypeMapper, PProductType>
    implements PProductTypeService{

}
