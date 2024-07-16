package com.bosch.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.Print;
import com.bosch.web.domain.dto.PrintDTO;
import com.bosch.web.domain.vo.PrintVO;
import com.bosch.web.domain.vo.RecordVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author ZPO2SGH
* @description 产品打印 业务层
* @createDate 2023-10-18 15:44:51
*/
public interface IPrintService extends IService<Print>{
    /**
     * 根据条件分页查询打印信息列表
     *
     * @param printDTO 打印信息
     * @return 打印信息集合信息
     */
    public List<PrintVO> selectPrintList(PrintDTO printDTO);


    /**
     * 通过打印ID查询打印信息
     *
     * @param printId 打印ID
     * @return 用户对象信息
     */
    public PrintVO selectPrintById(Long printId);

    /**
     * 新增打印信息
     *
     * @param file 文件
     * @param operatorName 操作员名称
     * @return 结果
     */
    public int insertPrint(MultipartFile file, String operatorName) throws Exception;

    /**
     * 校验打印文件名称
     *
     * @param name 文件名称
     * @return 结果
     */
    public boolean checkPrintNameUnique(String name);

    /**
     * 修改用户信息
     *
     * @param printDTO 打印信息
     * @return 结果
     */
    public int updatePrint(PrintDTO printDTO);

    /**
     * 查询一周激活的标签量
     *
     * @return 结果
     */
    public List<RecordVo> getWeekActivatedLabel();
}