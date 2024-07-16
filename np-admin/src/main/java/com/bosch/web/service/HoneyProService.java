package com.bosch.web.service;

import com.bosch.web.domain.HoneyPro;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.domain.dto.HoneyTypeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_pro(产品信息)】的数据库操作Service
* @createDate 2024-06-13 14:21:54
*/
public interface HoneyProService extends IService<HoneyPro> {

    int insertHoney(HoneyProDTO dto);


    int updateHoney(HoneyProDTO dto);
    List<HoneyPro> getList(HoneyProDTO dto);

    public int deleteAreaByIds(Integer[] ids);
}
