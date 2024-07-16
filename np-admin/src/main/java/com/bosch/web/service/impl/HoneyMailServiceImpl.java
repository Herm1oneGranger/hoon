package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.web.domain.HoneyMail;
import com.bosch.web.domain.HoneyVerify;
import com.bosch.web.domain.dto.HoneyMailDTO;
import com.bosch.web.domain.vo.HoneyVerifyResultVO;
import com.bosch.web.mapper.HoneyVerifyMapper;
import com.bosch.web.service.HoneyMailService;
import com.bosch.web.mapper.HoneyMailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author GUZ1CGD4
* @description 针对表【honey_mail】的数据库操作Service实现
* @createDate 2024-07-02 13:35:24
*/
@Service
public class HoneyMailServiceImpl extends ServiceImpl<HoneyMailMapper, HoneyMail>
    implements HoneyMailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private HoneyMailMapper mapper;

    public  void send(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("2424834965@qq.com"); //邮件发送者
        mailMessage.setTo("Zhijie.GUO@cn.bosch.com"); // 邮件发给的人
        mailMessage.setSubject("mail.getSubject()");  // 邮件主题
        mailMessage.setText("mail.getContent()");  // 邮件内容
        //mailMessage.copyTo(copyTo);

        mailSender.send(mailMessage);
    }
    @Override
    public int insertHoney(HoneyMailDTO dto) {
        HoneyMail mail = BeanConverUtil.conver(dto, HoneyMail.class);


        int insert = mapper.insert(mail);

        return insert;
    }

    @Override
    public int updateHoney(HoneyMailDTO dto) {
        HoneyMail mail = BeanConverUtil.conver(dto, HoneyMail.class);
        int i = mapper.updateById(mail);
        return i;
    }

    @Override
    public List<HoneyMail> getList(HoneyMailDTO dto) {
        List<HoneyMail> list = mapper.getList(dto);
        return list;
    }

    @Override
    public int deleteAreaByIds(Integer[] ids) {
        return mapper.deleteByIds(ids);
    }
}




