package com.bosch;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.bosch.web.domain.vo.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Paths;
import java.util.Map;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
public class test {
    private static final String URL = "http://143.64.233.91:8090/validate";
    public static void main(String[] args) {
        ApiResponse stringObjectMap = validateImage("/home/soroot/dc/nameplate/algo/images/verify/n-2.jpg");
    }


    public static ApiResponse validateImage(String imagePath) {
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

        // 发送请求
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);

        // 返回响应结果
        try {
            // 将响应的 JSON 字符串转换为 ApiResponse 对象
            return objectMapper.readValue(response.getBody(), ApiResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
