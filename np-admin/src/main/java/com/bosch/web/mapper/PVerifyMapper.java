package com.bosch.web.mapper;

import com.bosch.web.domain.PVerify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.dto.PVerifyDTO;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.vo.PVerifyVO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【p_verify】的数据库操作Mapper
* @createDate 2024-05-27 12:51:39
* @Entity com.bosch.web.domain.PVerify
*/
public interface PVerifyMapper extends BaseMapper<PVerify> {


    public List<PVerifyVO> selectBy(PVerifyDTO dto);


    public List<PVerifyVO> selectByLoc(PVerifyDTO dto);
}




