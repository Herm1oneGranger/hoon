package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.mapper.HoneyTypeMapper;
import com.bosch.web.service.HoneyProService;
import com.bosch.web.mapper.HoneyProMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_pro(产品信息)】的数据库操作Service实现
* @createDate 2024-06-13 14:21:54
*/
@Service
public class HoneyProServiceImpl extends ServiceImpl<HoneyProMapper, HoneyPro>
    implements HoneyProService{


    @Autowired
    private HoneyProMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHoney(HoneyProDTO dto) {
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);


        int insert = mapper.insert(honeyPro);

        return insert;
    }

    @Override
    public int updateHoney(HoneyProDTO dto) {
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);
        int i = mapper.updateById(honeyPro);
        return i;
    }

    @Override
    public List<HoneyPro> getList(HoneyProDTO dto) {
        List<HoneyPro> list = mapper.getList(dto);
        return list;
    }

    @Override
    public int deleteAreaByIds(Integer[] ids) {
        return mapper.deleteAreaByIds(ids);
    }

    //token生成
    public String tokenGen(HoneyProDTO dto){
        String token=new String();


        return token;
    }
}




