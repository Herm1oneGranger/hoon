package com.bosch.web.service;

import com.bosch.web.domain.PImages;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author GUZ1CGD4
* @description 针对表【p_images(图片)】的数据库操作Service
* @createDate 2024-05-31 15:36:44
*/
public interface PImagesService extends IService<PImages> {


    PImages insertImg(PImages images);
}
