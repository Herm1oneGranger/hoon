package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.domain.HoneyType;
import com.bosch.web.domain.dto.HoneyTypeDTO;
import com.bosch.web.mapper.HoneyTypeMapper;
import com.bosch.web.service.HoneyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_type】的数据库操作Service实现
* @createDate 2024-06-11 10:56:39
*/
@Service

public class HoneyTypeServiceImpl extends ServiceImpl<HoneyTypeMapper, HoneyType>
    implements HoneyTypeService{

    @Autowired
    private HoneyTypeMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHoney(HoneyTypeDTO dto) {

        // 将文件路径赋值给 DTO 的 pic 字段
//        dto.setPic("未上传图片");
//        if (!file.isEmpty()){
//            String s = saveFile(file);
//            dto.setPic(s);
//        }
        HoneyType honeyType = BeanConverUtil.conver(dto, HoneyType.class);
        int insert = mapper.insert(honeyType);

        return insert;
    }

    @Override
    public int updateHoney(HoneyTypeDTO dto) {
        HoneyType honeyType = BeanConverUtil.conver(dto, HoneyType.class);



        int i = mapper.updateById(honeyType);
        return i;
    }

    public String valid(HoneyTypeDTO dto){
        LambdaQueryWrapper<HoneyType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HoneyType::getCode, dto.getCode())
                .eq(HoneyType::getDeleteFlag, "0");
        HoneyType one = this.getOne(queryWrapper);
        // 判断数据库中是否存在相同的记录
        if (one!=null && one.getId()!=dto.getId()){
            return "code:"+dto.getCode() ;
        }
        return null;
    }
    @Override
    public List<HoneyType> getList(HoneyTypeDTO dto) {
        List<HoneyType> list = mapper.getList(dto);
        return list;
    }

    //保存图片
    public String saveFile(MultipartFile file){

        String path=new String();
        try {
            // 创建上传目录路径
            Path uploadPath = Paths.get("C:\\Users\\guz1cgd4\\Downloads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件到本地磁盘
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            path=filePath.toString();

        }catch (Exception e){

        }

        return path;
    }

    @Override
    public int deleteAreaByIds(Integer[] ids) {
        return mapper.deleteAreaByIds(ids);
    }
}




