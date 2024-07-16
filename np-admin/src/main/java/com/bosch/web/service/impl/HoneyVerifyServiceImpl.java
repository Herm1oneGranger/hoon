package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.constant.MsgConstants;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyVerify;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.domain.vo.HoneyVerifyVO;
import com.bosch.web.mapper.HoneyProMapper;
import com.bosch.web.service.HoneyVerifyService;
import com.bosch.web.mapper.HoneyVerifyMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author GUZ1CGD4
 * @description 针对表【honey_verify(校验信息表)】的数据库操作Service实现
 * @createDate 2024-06-24 17:01:10
 */
@Service
public class HoneyVerifyServiceImpl extends ServiceImpl<HoneyVerifyMapper, HoneyVerify>
        implements HoneyVerifyService {

    @Autowired
    private HoneyVerifyMapper mapper;


    @Override
    public int insertHoney(HoneyVerifyDTO dto) {
        HoneyVerify honeyVerify = BeanConverUtil.conver(dto, HoneyVerify.class);


        int insert = mapper.insert(honeyVerify);

        return insert;
    }

    @Override
    public int updateHoney(HoneyVerifyDTO dto) {
        HoneyVerify honeyVerify = BeanConverUtil.conver(dto, HoneyVerify.class);
        int i = mapper.updateById(honeyVerify);
        return i;
    }

    @Override
    public List<HoneyVerifyResultVO> getList(HoneyVerifyDTO dto) {
        List<HoneyVerifyResultVO> list = mapper.getList(dto);
        return list;
    }

    @Override
    public int deleteAreaByIds(Integer[] ids) {
        return mapper.deleteAreaByIds(ids);
    }

    /**
     * 通过pro_id获取记录
     * @param dto
     * @return
     */
    @Override
    public HoneyVerifyVO algoVerify(HoneyVerifyDTO dto) {
        List<HoneyVerifyResultVO> list = mapper.getList(dto);
        HoneyVerifyVO vo=new HoneyVerifyVO();

        //  1.同一标签扫码次数过多。标签累计扫码次数超过5次。
        if(list.size()>5){
            vo.setResult(MsgConstants.TEXTUREFAIL);
            vo.setMsg(MsgConstants.OVERFIVE);
            return vo;
        }
        //  2.同一标签一个月内在至少3个不同地方被扫。
        List<String> formattedPositions = list.stream()
                .map(HoneyVerifyResultVO::getPosition) // 提取 position 字段
                .map(HoneyVerifyServiceImpl::formatPosition) // 格式化到小数点后 3 位
                .distinct() // 去重
                .collect(Collectors.toList());
        if (formattedPositions!=null&&formattedPositions.size()>3){
            vo.setResult(MsgConstants.TEXTUREFAIL);
            vo.setMsg(MsgConstants.OVERTHREE);
            return vo;
        }
        //  3.同一标签一天内不同地点不同用户扫码超过2次。
        // 按日期（天）分组
        Map<String, List<HoneyVerifyResultVO>> groupedByDate = list.stream()
                .collect(Collectors.groupingBy(item -> new SimpleDateFormat("yyyy-MM-dd").format(item.getCreateTime())));
        //判断是否存在多个地点 多个人的数据
        for (List<HoneyVerifyResultVO> dailyRecords : groupedByDate.values()) {
            for (int i = 0; i < dailyRecords.size(); i++) {
                HoneyVerifyResultVO item1 = dailyRecords.get(i);
                for (int j = 0; j < dailyRecords.size(); j++) {
                    if (i != j) {
                        HoneyVerifyResultVO item2 = dailyRecords.get(j);
                        if (!item1.getAppId().equals(item2.getAppId()) && !item1.getPosition().equals(item2.getPosition())) {
                            vo.setResult(MsgConstants.TEXTUREFAIL);
                            vo.setMsg(MsgConstants.OVERTWO);
                            return vo;
                        }
                    }
                }
            }
        }

        //  4.第一次扫描为真后，7天内同IP同用户通过


        return vo;
    }
    private static String formatPosition(String position) {
        String[] coords = position.split(",");
        double lat = Double.parseDouble(coords[0]);
        double lon = Double.parseDouble(coords[1]);

        // 格式化到小数点后 3 位
        String formattedLat = String.format("%.3f", lat);
        String formattedLon = String.format("%.3f", lon);

        return formattedLat + "," + formattedLon;
    }
}




