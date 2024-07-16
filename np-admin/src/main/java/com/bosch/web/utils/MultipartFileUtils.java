package com.bosch.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

/**
 * @author external.Xin.Yu5@cn.bosch.com
 * @date 2020/12/28 16:53
 **/
public class MultipartFileUtils {

    private static final Logger logger = LoggerFactory.getLogger(MultipartFileUtils.class);

    /**
     * base64转MultipartFile
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        logger.info("base64{}", base64.contains(","));
        String[] baseStrs = base64.split(",");
        byte[] b;
        b = Base64.getDecoder().decode(baseStrs[1]);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        logger.info("Base64前缀>>>>" + baseStrs[0]);
        return new Base64DecodedMultipartFile(b, baseStrs[0]);
    }
}
