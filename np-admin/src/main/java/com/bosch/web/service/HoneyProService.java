package com.bosch.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.dto.HoneyProDTO;

import java.util.List;

/**
 * @author GUZ1CGD4
 * @description 针对表【honey_pro(产品信息)】的数据库操作Service
 * @createDate 2024-06-13 14:21:54
 */
public interface HoneyProService extends IService<HoneyPro> {


    String checkDuplicates(List<HoneyPro> doList);
    List<HoneyPro> processHoneyProList(List<HoneyPro> list);
    int insertHoney(HoneyProDTO dto);


    int updateHoney(HoneyProDTO dto);

    List<HoneyPro> getList(HoneyProDTO dto);

    List<HoneyPro> getToken(String token);

    List<HoneyPro> getAToken(String token);

    int deleteAreaByIds(Integer[] ids);

    String tokenGen();

    int updateStatus(HoneyProDTO dto);

    List<String> selectDate(String materialCode);

    List<String> selectYear();
}
