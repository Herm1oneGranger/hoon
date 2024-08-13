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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


        //todo  token
        String s = tokenGen(dto);
        honeyPro.setToken(s);
        int insert = mapper.insert(honeyPro);

        return insert;
    }

    @Override
    public int updateHoney(HoneyProDTO dto) {
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);
        //todo  token

        int i = mapper.updateById(honeyPro);
        return i;
    }

    @Override
    public List<HoneyPro> getList(HoneyProDTO dto) {
        List<HoneyPro> list = mapper.getList(dto);
        return list;
    }
    @Override
    public List<HoneyPro> getToken(String token) {
        List<HoneyPro> list = mapper.getToken(token);
        return list;
    }

    @Override
    public int deleteAreaByIds(Integer[] ids) {
        return mapper.deleteAreaByIds(ids);
    }

    //todo token生成
    public String tokenGen(HoneyProDTO dto){

        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,10);
    }

    public int updateStatus(HoneyProDTO dto){
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);
        //判断人员权限
        int i = mapper.updateStatus(honeyPro);
        return i;
    }

    @Override
    public  List<String> selectDate(String materialCode){

        List<String> strings = mapper.selectDate(materialCode);
        return  strings;
    }

    @Override
    public  List<String> selectYear(){

        List<String> strings = mapper.selectYear();
        return  strings;
    }
}




