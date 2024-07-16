package com.bosch.web.utils;

import com.bosch.common.constant.ProductConstants;
import com.bosch.common.utils.DateUtils;
import com.bosch.web.domain.dto.VerifyRecordDTO;
import com.bosch.web.mapper.VerifyMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: pink
 * @Description:
 * @Date: 11/2/2023 1:37 PM
 */
@Component
public class LabelScannerUtils {

    @Autowired
    private VerifyMapper verifyMapper;

    @Getter
    private int verifyCount;
    @Getter
    private int locations;
    @Getter
    private int users;

    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern(DateUtils.YYYY_MM_DD_HH_MM_SS);

    public boolean isOverScanLimit(VerifyRecordDTO verifyRecordDTO) {
        verifyCount = verifyMapper.countVerifyRecord(verifyRecordDTO)+1;
        return verifyCount > ProductConstants.VERIFY_THRESHOLD;
    }

    public boolean isScannedInMultipleLocations(VerifyRecordDTO verifyRecordDTO) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        Date newBeginTime = DateUtils.parseDate(oneMonthAgo.format(DTF));
        Date OldBeginTime = DateUtils.parseDate(verifyRecordDTO.getParams().get("beginTime"));
        Date beginTime = DateUtils.getGreaterDate(OldBeginTime, newBeginTime);
        verifyRecordDTO.getParams().replace("beginTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, beginTime));
        locations = verifyMapper.countLabelScannedLocations(verifyRecordDTO);
        return locations > ProductConstants.VERIFY_LOCATION_THRESHOLD;
    }

    public boolean isOverUserScanLimit(VerifyRecordDTO verifyRecordDTO) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        Date newBeginTime = DateUtils.parseDate(oneHourAgo.format(DTF));
        Date OldBeginTime = DateUtils.parseDate(verifyRecordDTO.getParams().get("beginTime"));
        Date beginTime = DateUtils.getGreaterDate(OldBeginTime, newBeginTime);
        verifyRecordDTO.getParams().replace("beginTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, beginTime));
        users = verifyMapper.countLabelUsersScanned(verifyRecordDTO);
        return users > ProductConstants.VERIFY_USER_THRESHOLD;
    }

}
