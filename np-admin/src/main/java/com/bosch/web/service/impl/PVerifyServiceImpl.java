package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.web.domain.PVerify;
import com.bosch.web.domain.dto.PVerifyDTO;
import com.bosch.web.domain.vo.PVerifyVO;


import com.bosch.web.mapper.PVerifyMapper;
import com.bosch.web.service.PVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【p_verify】的数据库操作Service实现
* @createDate 2024-05-27 12:51:39
*/
@Service
public class PVerifyServiceImpl extends ServiceImpl<PVerifyMapper, PVerify>
    implements PVerifyService {

    @Autowired
    private PVerifyMapper mapper;


    /**
     * 同一标签扫码次数过多。标签累计扫码次数超过5次。
     */
    public boolean check(PVerifyDTO dto){

        List<PVerifyVO> pVerifyVOS = mapper.selectBy(dto);
        return pVerifyVOS.size()>5?false:true;
    }
    /**
     * 同一标签一个月内在至少3个不同地方被扫。
     */

    public boolean checkLoc(PVerifyDTO dto){

        List<PVerifyVO> pVerifyVOS = mapper.selectBy(dto);
        return pVerifyVOS.size()>5?false:true;
    }

    /**
     * 同一标签一天内不同地点不同用户扫码超过2次。
     */



    /**
     * 第一次扫描为真后，7天内同IP同用户通过
     */



    /**
     * 第一次为假
     */



}




