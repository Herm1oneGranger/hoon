package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.domain.HoneyFeedback;
import com.bosch.web.domain.HoneyMailDetail;
import com.bosch.web.domain.dto.HoneyFeedbackDTO;
import com.bosch.web.domain.vo.HoneyFeedbackVO;
import com.bosch.web.mapper.HoneyMailDetailMapper;
import com.bosch.web.service.HoneyFeedbackService;
import com.bosch.web.mapper.HoneyFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bosch.common.utils.SecurityUtils.getUsername;

/**
* @author GUZ1CGD4
* @description 针对表【honey_feedback】的数据库操作Service实现
* @createDate 2024-07-18 16:34:05
*/
@Service
public class HoneyFeedbackServiceImpl extends ServiceImpl<HoneyFeedbackMapper, HoneyFeedback>
    implements HoneyFeedbackService{

    @Autowired
    private HoneyFeedbackMapper mapper;

    @Override
    public int insert(HoneyFeedbackDTO dto) {
        HoneyFeedback feedback = BeanConverUtil.conver(dto, HoneyFeedback.class);


        int insert = mapper.insert(feedback);

        return insert;
    }

    @Override
    public int update(HoneyFeedbackDTO dto) {
        HoneyFeedback honeyFeedback = BeanConverUtil.conver(dto, HoneyFeedback.class);
        honeyFeedback.setUpdateBy(getUsername());
        int i = mapper.updateById(honeyFeedback);

        return i;
    }

    @Override
    public List<HoneyFeedbackVO> getList(HoneyFeedbackDTO dto) {
        List<HoneyFeedbackVO> list = mapper.getList(dto);
        return list;
    }

    @Override
    public int deleteAreaByIds(Integer[] ids) {
        return mapper.deleteByIds(ids);
    }
}




