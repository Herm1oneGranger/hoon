package com.bosch.web.service;

import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyVerify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.dto.PVerifyDTO;
import com.bosch.web.domain.vo.HoneyDashVO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.domain.vo.HoneyVerifyVO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_verify(校验信息表)】的数据库操作Service
* @createDate 2024-06-24 17:01:10
*/
public interface HoneyVerifyService extends IService<HoneyVerify> {

    int insertHoney(HoneyVerifyDTO dto);

    HoneyVerify  insertHoneyGetId(HoneyVerifyDTO dto);
    int updateHoney(HoneyVerifyDTO dto);
    List<HoneyVerifyResultVO> getList(HoneyVerifyDTO dto);

    public int deleteAreaByIds(Integer[] ids);

//  1.同一标签扫码次数过多。标签累计扫码次数超过5次。
//  2.同一标签一个月内在至少3个不同地方被扫。
//  3.同一标签一天内不同地点不同用户扫码超过2次。
//  4.第一次扫描为真后，7天内同IP同用户通过
    HoneyVerifyVO algoVerify(HoneyVerifyDTO dto);

    HoneyVerifyVO iqa(PVerifyDTO dto);

    HoneyVerifyVO checkToken(PVerifyDTO dto);


    List<HoneyVerifyResultVO> getDash(HoneyVerifyDTO dto);

    List<HoneyVerifyResultVO> getDash2(HoneyVerifyDTO dto);

    List<HoneyDashVO> getMonthlyStats(List<HoneyVerifyResultVO> dash);

    List<HoneyDashVO> getQuarterlyStats(List<HoneyVerifyResultVO> dash);

    List<HoneyDashVO> getYearlyStats(List<HoneyVerifyResultVO> dash);
}
