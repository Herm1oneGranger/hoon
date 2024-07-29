package com.bosch.web.mapper;

import com.bosch.web.domain.HoneyFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.HoneyMailDetail;
import com.bosch.web.domain.Print;
import com.bosch.web.domain.dto.HoneyFeedbackDTO;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;
import com.bosch.web.domain.vo.HoneyFeedbackVO;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_feedback】的数据库操作Mapper
* @createDate 2024-07-18 16:34:05
* @Entity com.bosch.web.domain.HoneyFeedback
*/
public interface HoneyFeedbackMapper extends BaseMapper<HoneyFeedback> {

    List<HoneyFeedbackVO> getList(HoneyFeedbackDTO dto);

    int deleteByIds(Integer[] ids);

    int updateById(HoneyFeedback honeyFeedback);
}




