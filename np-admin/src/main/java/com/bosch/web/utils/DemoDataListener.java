package com.bosch.web.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.bosch.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// 有个很重要的点 TListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class DemoDataListener implements ReadListener<Object> {

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {

        if (context.readRowHolder().getRowIndex() == 0) {
            String[] headList = {"产品名称", "物料号", "订单号", "数量"};
            for (int i = 0; i < headList.length; i++) {
                if (!headMap.get(i).getStringValue().equals(headList[i])) {
                    throw new ServiceException("excel表头有误,请使用正确模板");
                }
            }
        }
    }

    @Override
    public void invoke(Object data, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}