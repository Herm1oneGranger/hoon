package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.HoneyMailDetail;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;
import com.bosch.web.mapper.HoneyMailMapper;
import com.bosch.web.service.HoneyMailDetailService;
import com.bosch.web.mapper.HoneyMailDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_mail_detail】的数据库操作Service实现
* @createDate 2024-07-09 15:48:42
*/
@Service
public class HoneyMailDetailServiceImpl extends ServiceImpl<HoneyMailDetailMapper, HoneyMailDetail>
    implements HoneyMailDetailService{

    @Autowired
    private HoneyMailDetailMapper mapper;
    @Override
    public int insert(HoneyMailDetailDTO dto) {
        HoneyMailDetail mailDetail = BeanConverUtil.conver(dto, HoneyMailDetail.class);


        int insert = mapper.insert(mailDetail);

        return insert;
    }

    @Override
    public int update(HoneyMailDetailDTO dto) {
        HoneyMailDetail mailDetail = BeanConverUtil.conver(dto, HoneyMailDetail.class);
        int i = mapper.updateById(mailDetail);
        return i;
    }

    @Override
    public List<HoneyMailDetail> getList(HoneyMailDetailDTO dto) {
        List<HoneyMailDetail> list = mapper.getList(dto);
        return list;
    }
}




