package com.bosch.web.service;

import com.bosch.web.domain.HoneyFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.HoneyMailDetail;
import com.bosch.web.domain.dto.HoneyFeedbackDTO;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;
import com.bosch.web.domain.vo.HoneyFeedbackVO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_feedback】的数据库操作Service
* @createDate 2024-07-18 16:34:05
*/
public interface HoneyFeedbackService extends IService<HoneyFeedback> {

    int insert(HoneyFeedbackDTO dto);

    int update(HoneyFeedbackDTO dto);

    List<HoneyFeedbackVO> getList(HoneyFeedbackDTO dto);

    int deleteAreaByIds(Integer[] ids);
}
