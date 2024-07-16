package com.bosch.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.Print;
import com.bosch.web.domain.vo.PrintVO;
import com.bosch.web.domain.vo.RecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author ZPO2SGH
* @description 针对表【p_print】的数据库操作Mapper
* @createDate 2023-10-18 15:44:51
* @Entity com.bosch.web.domain.Print
*/
@Mapper
public interface PrintMapper extends BaseMapper<Print> {

    /**
     * 查询打印列表
     *
     * @param Print 打印
     * @return 打印集合
     */
    public List<PrintVO> selectPrintList(Print Print);

    public List<PrintVO> selectPrintListByData(Print Print);
    /**
     * 通过打印ID查询打印信息
     *
     * @param printId 打印ID
     * @return 打印信息
     */
    public PrintVO selectPrintById(Long printId);

    /**
     * 校验打印文件名称
     *
     * @param fileName 文件名称
     * @return 结果
     */
    public PrintVO checkPrintNameUnique(String fileName);

    /**
     * 新增打印文件
     *
     * @param print 打印信息
     * @return 结果
     */
    public int insertPrint(Print print);

    /**
     * 修改岗位信息
     *
     * @param print 打印信息
     * @return 结果
     */
    public int updatePrint(Print print);

    /**
     * 近一周每天激活标签数
     *
     * @return 结果
     */
    List<RecordVo> selectWeekActivatedLabel();
}