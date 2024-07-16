package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.web.domain.PImages;
import com.bosch.web.domain.Print;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.service.PImagesService;
import com.bosch.web.mapper.PImagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【p_images(图片)】的数据库操作Service实现
* @createDate 2024-05-31 15:36:44
*/
@Service
public class PImagesServiceImpl extends ServiceImpl<PImagesMapper, PImages>
    implements PImagesService{

    @Autowired
    private PImagesMapper mapper;

    public PImages insertImg(PImages images){

        int insert = mapper.insert(images);
        return images;
    }
}




