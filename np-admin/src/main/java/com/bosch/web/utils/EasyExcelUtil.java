package com.bosch.web.utils;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson2.JSON;
import com.bosch.web.domain.ProductExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * excel工具类
 * easyexcel使用的3.0.2版本，跟以前版本有很大区别，且不兼容1.x版本
 */
public class EasyExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelUtil.class);

    public static List<ProductExcel> read(InputStream inputStream) {
        List<ProductExcel> list=new ArrayList<>();
        //具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(inputStream, ProductExcel.class, new PageReadListener<ProductExcel>(dataList -> {
            list.addAll(dataList);}
        )).sheet().doRead();
        return list;
    }
    public static void headerRead(InputStream inputStream) {

        EasyExcel.read(inputStream, ProductExcel.class, new DemoDataListener ( ){}).sheet().doRead();

    }
}

