package com.bosch.web.mapper;

import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyVerify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_verify(校验信息表)】的数据库操作Mapper
* @createDate 2024-06-24 17:01:10
* @Entity com.bosch.web.domain.HoneyVerify
*/
public interface HoneyVerifyMapper extends BaseMapper<HoneyVerify> {

    List<HoneyVerifyResultVO> getList(HoneyVerifyDTO dto);

    List<HoneyVerifyResultVO> getDash(HoneyVerifyDTO dto);

    List<HoneyVerifyResultVO> getDash2(HoneyVerifyDTO dto);
    int insertHoneyVerify(HoneyVerify honeyVerify);
    int deleteAreaByIds(Integer[] ids);
}




