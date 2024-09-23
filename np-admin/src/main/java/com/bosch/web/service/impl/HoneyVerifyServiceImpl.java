package com.bosch.web.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.StringUtils;
import com.bosch.framework.web.service.UserDetailsServiceImpl;
import com.bosch.web.constant.MsgConstants;
import com.bosch.web.domain.HoneyPro;
import com.bosch.web.domain.HoneyVerify;
import com.bosch.web.domain.dto.HoneyMailDetailDTO;
import com.bosch.web.domain.dto.HoneyVerifyDTO;
import com.bosch.web.domain.dto.PVerifyDTO;
import com.bosch.web.domain.vo.ApiResponse;
import com.bosch.web.domain.vo.HoneyDashVO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.domain.vo.HoneyVerifyVO;
import com.bosch.web.mapper.HoneyVerifyMapper;
import com.bosch.web.service.HoneyMailDetailService;
import com.bosch.web.service.HoneyProService;
import com.bosch.web.service.HoneyVerifyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author GUZ1CGD4
 * @description 针对表【honey_verify(校验信息表)】的数据库操作Service实现
 * @createDate 2024-06-24 17:01:10
 */
@Service
public class HoneyVerifyServiceImpl extends ServiceImpl<HoneyVerifyMapper, HoneyVerify>
        implements HoneyVerifyService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HoneyVerifyMapper mapper;

    @Autowired
    private HoneyProService proService;

    @Autowired
    private HoneyMailDetailService mailDetailService;



    @Value("${api.python-url}")
    private String baseUrl;


    @Override
    public String getTotalResult(PVerifyDTO dto,String algoResult, String textureResult) {
        logger.info("合并纹理结果："+"algoResult:"+algoResult + " textureResult:"+textureResult);
        HoneyMailDetailDTO mailDetailDTO =new HoneyMailDetailDTO();
        mailDetailDTO.setMail("cherry.chen2@honeywell.com");
        mailDetailDTO.setName("cherry");
        mailDetailDTO.setTitle(dto.getToken());
        String baseString ="token : "+dto.getToken()+" ";
        //纹理真  算法真
        if (MsgConstants.TRUE.equals(textureResult) && MsgConstants.ALOSUCCESS.equals(algoResult)) {

            return MsgConstants.TRUE;
        }
        //纹理真  算法假
        if (MsgConstants.TRUE.equals(textureResult) && MsgConstants.ALOFAIL.equals(algoResult)) {
            mailDetailDTO.setContent(baseString +"纹理算法识别为真，逻辑算法识别为假。");
            mailDetailService.insert(mailDetailDTO);
            return MsgConstants.TRUE;
        }
        //纹理假  算法真
        if (MsgConstants.FAKE.equals(textureResult) && MsgConstants.ALOSUCCESS.equals(algoResult)) {
            mailDetailDTO.setContent(baseString +"纹理算法识别为假，逻辑算法识别为真。");
            mailDetailService.insert(mailDetailDTO);
            return MsgConstants.MANUAL;
        }
        //纹理假  算法假
        if (MsgConstants.FAKE.equals(textureResult) && MsgConstants.ALOFAIL.equals(algoResult)) {
            mailDetailDTO.setContent(baseString +"纹理算法识别为假，逻辑算法识别为假。");
            mailDetailService.insert(mailDetailDTO);
            return MsgConstants.FAKE;
        }
        // 纹理假  算法假(时间久)
        if (MsgConstants.FAKE.equals(textureResult) && MsgConstants.ALOTIMEFAIL.equals(algoResult)) {
            mailDetailDTO.setContent(baseString +"纹理算法识别为假，逻辑算法识别为假（产品时间过久）。");
            mailDetailService.insert(mailDetailDTO);
            return MsgConstants.MANUAL;
        }
        return null;
    }

    public HoneyVerifyVO getValidate(String imagePath, String type) {
        HoneyVerifyVO vo = new HoneyVerifyVO();


        ApiResponse apiResponse = validateImage(imagePath, type);

        if (apiResponse == null) {
            vo.setResult(MsgConstants.Retry);
            vo.setMsg("算法api请求失败");
        } else {
            //假
            if (apiResponse.getCode()==0) {
                vo.setResult(MsgConstants.FAKE);
                vo.setMsg("纹理识别为假");
            }
            //真
            if (apiResponse.getCode()==1) {
                vo.setResult(MsgConstants.TRUE);
                vo.setMsg("纹理识别为真");
            }
            //未过IQA
            if (apiResponse.getCode()==2) {
                vo.setResult(MsgConstants.Retry);
                vo.setMsg(apiResponse.getMessage());
            }

        }

        return vo;
    }

    public ApiResponse validateImage(String imagePath, String type) {
        String URL = "";
        if ("0".equals(type)) {
            URL = baseUrl + "/iqa";
        } else {
            URL = baseUrl + "/validate";
        }
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 设置请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("imageTokenPath", imagePath);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        logger.info("发起纹理请求："+ JSON.toJSONString(body));
        // 发送请求
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);

        // 返回响应结果
        try {
            logger.info("纹理识别返回结果:"+ JSON.toJSONString(response.getBody()));
            System.out.println("纹理识别返回结果:"+JSON.toJSONString(response.getBody()));
            // 将响应的 JSON 字符串转换为 ApiResponse 对象
            return objectMapper.readValue(response.getBody(), ApiResponse.class);
        } catch (Exception e) {
            log.error("请求算法接口异常 :" + e.getMessage());
            return null;
        }

    }

    @Override
    public int insertHoney(HoneyVerifyDTO dto) {
        HoneyVerify honeyVerify = BeanConverUtil.conver(dto, HoneyVerify.class);


        int insert = mapper.insert(honeyVerify);

        return insert;
    }

    @Override
    public HoneyVerify insertHoneyGetId(HoneyVerifyDTO dto) {
        HoneyVerify honeyVerify = BeanConverUtil.conver(dto, HoneyVerify.class);


        int insert = mapper.insert(honeyVerify);

        return honeyVerify;
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
     *
     * @param dto
     * @return
     */
    @Override
    public HoneyVerifyVO algoVerify(HoneyVerifyDTO dto) {
        List<HoneyVerifyResultVO> list = mapper.getList(dto);
        HoneyVerifyVO vo = new HoneyVerifyVO();
        vo.setResult(MsgConstants.ALOSUCCESS);
        vo.setMsg(MsgConstants.ALOSUCCESSTEXT);
        //特殊：时间过久
        List<HoneyPro> proList = proService.getToken(dto.getToken());
        if (CollectionUtils.isNotEmpty(proList)) {
            HoneyPro honeyPro = proList.get(0);// 获取今天的日期
            Date createTime = honeyPro.getCreateTime();
            LocalDate today = LocalDate.now();
            // 将 java.util.Date 转换为 java.time.LocalDate
            LocalDate creationDate = createTime.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            // 计算两个日期之间的差距
            Period period = Period.between(creationDate, today);
            // 判断是否超过一年
            if (period.getYears() >= 1) {
                vo.setResult(MsgConstants.ALOTIMEFAIL);
                vo.setMsg(MsgConstants.OVERONEYEAR);
                return vo;
            }
        }

        //  1.同一标签扫码次数过多。标签累计扫码次数超过5次。
        if (list.size() > 5) {
            vo.setResult(MsgConstants.ALOFAIL);
            vo.setMsg(MsgConstants.OVERFIVE);
            return vo;
        }
        //  2.同一标签一个月内在至少3个不同地方被扫。
        List<String> formattedPositions = list.stream()
                .map(HoneyVerifyResultVO::getPosition) // 提取 position 字段
                .map(HoneyVerifyServiceImpl::formatPosition) // 格式化到小数点后 3 位
                .distinct() // 去重
                .collect(Collectors.toList());
        if (formattedPositions != null && formattedPositions.size() > 3) {
            vo.setResult(MsgConstants.ALOFAIL);
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
                            vo.setResult(MsgConstants.ALOFAIL);
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

    @Override
    public HoneyVerifyVO iqa(PVerifyDTO dto) {
        // iqa校验
        RestTemplate restTemplate = new RestTemplate();

        // post 请求获取一个对象
        HoneyVerifyVO temp = restTemplate.postForObject(baseUrl, dto.getImgBase(), HoneyVerifyVO.class);

        if (temp != null) {
            return temp;
        } else {
            return new HoneyVerifyVO();
        }
    }

    @Override
    public HoneyVerifyVO checkToken(PVerifyDTO dto) {
        List<HoneyPro> tokens = proService.getToken(dto.getToken());
        if (CollectionUtils.isEmpty(tokens)) {
            HoneyVerifyVO honeyVerifyVO = new HoneyVerifyVO();
            honeyVerifyVO.setResult(MsgConstants.FAKE);
            honeyVerifyVO.setMsg(MsgConstants.NOTOKENDES);
            return honeyVerifyVO;
        }
        return new HoneyVerifyVO();
    }

    @Override
    public List<HoneyVerifyResultVO> getDash(HoneyVerifyDTO dto) {
        return mapper.getDash(dto);
    }

    @Override
    public List<HoneyVerifyResultVO> getDash2(HoneyVerifyDTO dto) {
        return mapper.getDash2(dto);
    }

    private static String formatPosition(String position) {
        if(StringUtils.isEmpty(position)){
            return "";
        }
        String[] coords = position.split(",");
        double lat = Double.parseDouble(coords[0]);
        double lon = Double.parseDouble(coords[1]);

        // 格式化到小数点后 3 位
        String formattedLat = String.format("%.3f", lat);
        String formattedLon = String.format("%.3f", lon);

        return formattedLat + "," + formattedLon;
    }

    @Override
    public List<HoneyDashVO> getMonthlyStats(List<HoneyVerifyResultVO> dash) {

        // 当前年份
        int currentYear = YearMonth.now().getYear();

        // 过滤出当前年份的数据
        List<HoneyVerifyResultVO> filteredDash = dash.stream()
                .filter(vo -> {
                    LocalDateTime localDateTime = vo.getCreateTime().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return localDateTime.getYear() == currentYear;
                })
                .collect(Collectors.toList());

        // 初始化包含所有12个月份的空统计表
        List<HoneyDashVO> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth currentYearMonth = YearMonth.now();
        for (int i = 1; i <= 12; i++) {
            int year = currentYearMonth.getYear();
            //String formattedMonth = month.format(formatter);

            HoneyDashVO honeyDashVO = new HoneyDashVO();
            if (i<10){
                honeyDashVO.setDate(String.valueOf(year)+"-0"+i);
            }else {
                honeyDashVO.setDate(String.valueOf(year)+"-"+i);
            }

            honeyDashVO.setTrueNum(0);
            honeyDashVO.setFakeNum(0);
            honeyDashVO.setTotal(0);

            result.add(honeyDashVO);
        }

        // 按月份分组
        Map<String, List<HoneyVerifyResultVO>> groupedByMonth = filteredDash.stream()
                .collect(Collectors.groupingBy(vo -> {
        // 将 Date 转换为 LocalDateTime
                    LocalDateTime localDateTime = vo.getCreateTime().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 按照 yyyy-MM 格式化
                    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                }));

        // 遍历分组结果，更新对应月份的数据
        for (Map.Entry<String, List<HoneyVerifyResultVO>> entry : groupedByMonth.entrySet()) {
            String month = entry.getKey();
            List<HoneyVerifyResultVO> monthlyResults = entry.getValue();

             // 统计 totalResults 的数量 Manual
            long trueCount = monthlyResults.stream().filter(vo -> "true".equalsIgnoreCase(vo.getTotalResults())).count();
            long fakeCount = monthlyResults.stream().filter(vo -> "Fake".equalsIgnoreCase(vo.getTotalResults())).count();
            long manualCount = monthlyResults.stream().filter(vo -> "Manual".equalsIgnoreCase(vo.getTotalResults())).count();
            long totalCount = monthlyResults.size();

            // 查找并更新结果中的相应月份
            for (HoneyDashVO honeyDashVO : result) {
                if (honeyDashVO.getDate().equals(month)) {
                    honeyDashVO.setTrueNum((int) trueCount);
                    honeyDashVO.setFakeNum((int) fakeCount+(int) manualCount);
                    honeyDashVO.setTotal((int) totalCount);
                    break;
                }
            }
        }
        // 使用 Collections.sort() 按日期从小到大排序
        Collections.sort(result, new Comparator<HoneyDashVO>() {
            @Override
            public int compare(HoneyDashVO o1, HoneyDashVO o2) {
                YearMonth date1 = YearMonth.parse(o1.getDate(), formatter);
                YearMonth date2 = YearMonth.parse(o2.getDate(), formatter);
                return date1.compareTo(date2);
            }
        });
        return result;
    }

    public List<HoneyDashVO> getQuarterlyStats(List<HoneyVerifyResultVO> dash) {
        // 获取当前年份
        int currentYear = LocalDateTime.now().getYear();

        // 过滤出当前年份的数据
        List<HoneyVerifyResultVO> filteredDash = dash.stream()
                .filter(vo -> {
                    LocalDateTime localDateTime = vo.getCreateTime().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return localDateTime.getYear() == currentYear;
                })
                .collect(Collectors.toList());

        // 按季度分组
        Map<String, List<HoneyVerifyResultVO>> groupedByQuarter = filteredDash.stream()
                .collect(Collectors.groupingBy(vo -> {
                    LocalDateTime localDateTime = vo.getCreateTime().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    int month = localDateTime.getMonthValue();
                    int quarter = (month - 1) / 3 + 1; // 计算季度
                    return localDateTime.getYear() + "-Q" + quarter;
                }));

        // 初始化包含所有4个季度的空统计表
        List<HoneyDashVO> result = new ArrayList<>();
        for (int quarter = 1; quarter <= 4; quarter++) {
            String quarterKey = currentYear + "-Q" + quarter;

            HoneyDashVO honeyDashVO = new HoneyDashVO();
            honeyDashVO.setDate(quarterKey); // 格式化为 "yyyy-Qx" 的字符串
            honeyDashVO.setTrueNum(0);
            honeyDashVO.setFakeNum(0);
            honeyDashVO.setTotal(0);

            result.add(honeyDashVO);
        }

        // 遍历分组结果，更新对应季度的数据
        for (Map.Entry<String, List<HoneyVerifyResultVO>> entry : groupedByQuarter.entrySet()) {
            String quarter = entry.getKey();
            List<HoneyVerifyResultVO> quarterlyResults = entry.getValue();

        // 统计 totalResults 的数量
            long trueCount = quarterlyResults.stream().filter(vo -> "true".equalsIgnoreCase(vo.getTotalResults())).count();
            long fakeCount = quarterlyResults.stream().filter(vo -> "Fake".equalsIgnoreCase(vo.getTotalResults())).count();

            long manualCount = quarterlyResults.stream().filter(vo -> "Manual".equalsIgnoreCase(vo.getTotalResults())).count();

            long totalCount = quarterlyResults.size();

        // 查找并更新结果中的相应季度
            for (HoneyDashVO honeyDashVO : result) {
                if (honeyDashVO.getDate().equals(quarter)) {
                    honeyDashVO.setTrueNum((int) trueCount);
                    honeyDashVO.setFakeNum((int) fakeCount+(int) manualCount);
                    honeyDashVO.setTotal((int) totalCount);
                    break;
                }
            }
        }

        // 按季度排序
        result.sort(new Comparator<HoneyDashVO>() {
            @Override
            public int compare(HoneyDashVO o1, HoneyDashVO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        return result;
    }

    // 按年分组统计
    public List<HoneyDashVO> getYearlyStats(List<HoneyVerifyResultVO> dash) {
        // 按年分组
        Map<Integer, List<HoneyVerifyResultVO>> groupedByYear = dash.stream()
                .collect(Collectors.groupingBy(vo -> {
                    LocalDateTime localDateTime = vo.getCreateTime().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return localDateTime.getYear();
                }));

        // 遍历分组结果，统计每个年份的数量
        List<HoneyDashVO> result = new ArrayList<>();
        for (Map.Entry<Integer, List<HoneyVerifyResultVO>> entry : groupedByYear.entrySet()) {
            int year = entry.getKey();
            List<HoneyVerifyResultVO> yearlyResults = entry.getValue();

            // 统计 totalResults 的数量
            long trueCount = yearlyResults.stream().filter(vo -> "true".equalsIgnoreCase(vo.getTotalResults())).count();
            long fakeCount = yearlyResults.stream().filter(vo -> "Fake".equalsIgnoreCase(vo.getTotalResults())).count();

            long manualCount = yearlyResults.stream().filter(vo -> "Manual".equalsIgnoreCase(vo.getTotalResults())).count();

            long totalCount = yearlyResults.size();

            // 构建 HoneyDashVO
            HoneyDashVO honeyDashVO = new HoneyDashVO();
            honeyDashVO.setDate(String.valueOf(year)); // 直接设置为年份
            honeyDashVO.setTrueNum((int) trueCount);
            honeyDashVO.setFakeNum((int) fakeCount+(int) manualCount);
            honeyDashVO.setTotal((int) totalCount);

            result.add(honeyDashVO);
        }

        return result;
    }

}


