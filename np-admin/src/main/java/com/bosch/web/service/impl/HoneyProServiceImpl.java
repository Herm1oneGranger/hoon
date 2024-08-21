package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyProDTO;
import com.bosch.web.mapper.HoneyTypeMapper;
import com.bosch.web.service.HoneyProService;
import com.bosch.web.mapper.HoneyProMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author GUZ1CGD4
 * @description 针对表【honey_pro(产品信息)】的数据库操作Service实现
 * @createDate 2024-06-13 14:21:54
 */
@Service
public class HoneyProServiceImpl extends ServiceImpl<HoneyProMapper, HoneyPro>
        implements HoneyProService {


    @Autowired
    private HoneyProMapper mapper;


    public String checkDuplicates(List<HoneyPro> doList) {
        if (CollectionUtils.isNotEmpty(doList)) {
            for (HoneyPro honeyPro : doList) {
                LambdaQueryWrapper<HoneyPro> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(HoneyPro::getMaterial, honeyPro.getMaterial())
                        .eq(HoneyPro::getSo, honeyPro.getSo())
                        .eq(HoneyPro::getItemNo, honeyPro.getItemNo())
                        .eq(HoneyPro::getOrderNo, honeyPro.getOrderNo())
                        .eq(HoneyPro::getDeleteFlag, "0");
                // 判断数据库中是否存在相同的记录
                if (this.count(queryWrapper) > 0){
                    return "material:"+honeyPro.getMaterial() +
                            " so:"+honeyPro.getSo()+
                            " itemNo:"+honeyPro.getItemNo()+
                            " 订单序列号:"+honeyPro.getOrderNo();
                }

            }

        }
        return null;
    }

    public List<HoneyPro> processHoneyProList(List<HoneyPro> list) {
        List<HoneyPro> doList = new ArrayList<>();

        for (HoneyPro honeyPro : list) {
            // 获取 orderQua 的值并将其转换为整数
            int orderQua = Integer.parseInt(honeyPro.getOrderQua());

            // 根据 orderQua 的数值复制数据
            for (int i = 1; i <= orderQua; i++) {
                HoneyPro newHoneyPro = new HoneyPro();
                BeanUtils.copyProperties(honeyPro, newHoneyPro); // 复制所有属性
                newHoneyPro.setOrderNo(String.valueOf(i)); // 修改 orderNo

                String s = tokenGen();
                newHoneyPro.setToken(s);

                doList.add(newHoneyPro);
            }
        }

        return doList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHoney(HoneyProDTO dto) {
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);


        //todo  token
        String s = tokenGen();
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
    public String tokenGen() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 10);
    }

    public int updateStatus(HoneyProDTO dto) {
        HoneyPro honeyPro = BeanConverUtil.conver(dto, HoneyPro.class);
        //判断人员权限
        int i = mapper.updateStatus(honeyPro);
        return i;
    }

    @Override
    public List<String> selectDate(String materialCode) {

        List<String> strings = mapper.selectDate(materialCode);
        return strings;
    }

    @Override
    public List<String> selectYear() {

        List<String> strings = mapper.selectYear();
        return strings;
    }
}




