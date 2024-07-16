package com.bosch.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.annotation.DataScope;
import com.bosch.common.constant.PrintConstants;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.StringUtils;
import com.bosch.system.service.impl.SysUserServiceImpl;
import com.bosch.web.domain.Print;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.dto.PrintDTO;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.vo.PrintVO;
import com.bosch.web.domain.vo.RecordVo;
import com.bosch.web.mapper.PrintMapper;
import com.bosch.web.mapper.ProductMapper;
import com.bosch.web.service.IPrintService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author ZPO2SGH
* @description 针对表【p_print】的数据库操作Service实现
* @createDate 2023-10-18 15:44:51
*/
@Service
public class PrintServiceImpl extends ServiceImpl<PrintMapper, Print>
    implements IPrintService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private PrintMapper printMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询打印信息集合
     *
     * @param printDTO 打印信息
     * @return 打印信息集合
     */
    @DataScope(deptAlias = "d", userAlias = "s")
    public List<PrintVO> selectPrintList(PrintDTO printDTO){
        Print print = BeanConverUtil.conver(printDTO, Print.class);
        return printMapper.selectPrintListByData(print);
    }

    /**
     * 通过打印ID查询打印信息
     *
     * @param printId 打印ID
     * @return 打印对象信息
     */
    public PrintVO selectPrintById(Long printId)
    {
        return printMapper.selectPrintById(printId);
    }

    /**
     * 新增打印信息
     *
     * @param file 文件
     * @param operatorName 操作员名称
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertPrint(MultipartFile file, String operatorName) throws Exception {
        int count = 0;

        try (InputStream inputStream = file.getInputStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new ByteArrayInputStream(IOUtils.toByteArray(inputStream))));

            Print print = new Print();
            print.setName(file.getOriginalFilename());
            try (InputStream xmlInputStream = file.getInputStream()) {
                String xmlData = IOUtils.toString(xmlInputStream, StandardCharsets.UTF_8);
                print.setXml(xmlData);
            }
            List<Product> newProductList = parsePrintFile(document, print);
            print.setUpdateBy(operatorName);

            count += printMapper.insertPrint(print);
            if(print.getStatus().equals(PrintConstants.NORMAL)){
                count += generateProduct(newProductList, print);
            }
        } catch (IOException e) {
            log.error("IO Exception: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            log.error("Parser Configuration Exception: " + e.getMessage());
        } catch (SAXException e) {
            log.error("SAX Exception: " + e.getMessage());
        } catch (Exception e) {
            log.error("General Exception: " + e.getMessage());
            // 为了回滚抛出异常
            throw new Exception();
        }
        return count;
    }

    public int generateProduct(List<Product> newProductList, Print print){
        int count=0;
        for(Product newProduct: newProductList){
            newProduct.setPrintId(print.getId());
            ProductDTO exitProduct = new ProductDTO();
            exitProduct.setMnr(newProduct.getMnr());
            exitProduct.setFd(newProduct.getFd());
            exitProduct.setSn(newProduct.getSn());
        }
        count = productMapper.insertProducts(newProductList);
        return count;
    }

    public List<Product> parsePrintFile(Document document, Print print){
        List<Product> productList = new ArrayList<>();
        document.getDocumentElement().normalize();
        print.setCreateBy(document.getElementsByTagName("UNAME").item(0).getTextContent());
        NodeList auftraegeList = document.getElementsByTagName("Auftraege");

        if(!checkPrintSum(auftraegeList, print)){
            print.setStatus(PrintConstants.EXCEPTION);
            print.setRemark("SN号的数量与总数不匹配");
        }else {
            print.setStatus(PrintConstants.NORMAL);
        }

        for (int i = 0; i < auftraegeList.getLength(); i++) {
            Element auftrag = (Element) auftraegeList.item(i);

            NodeList serial = auftrag.getElementsByTagName("Serial");
            String mnr = auftrag.getElementsByTagName("MATNR").item(0).getTextContent();
            String maktx = auftrag.getElementsByTagName("MAKTX").item(0).getTextContent();
            String unprocessedFD = auftrag.getElementsByTagName("AUFNR").item(0).getTextContent();
            String fd = removeLeadingZeros(unprocessedFD);

            for (int j = 0; j < serial.getLength(); j++) {
                String sn = serial.item(j).getTextContent().trim().replaceAll("^0+", "");
                Product product = new Product();
                product.setName(maktx);
                product.setMnr(mnr);
                product.setFd(fd);
                product.setCreateBy(print.getCreateBy());
                product.setSn(sn);
                productList.add(product);
            }
        }

        return productList;
    }

    public String removeLeadingZeros(String str) {
        Pattern pattern = Pattern.compile("^0+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str = str.substring(matcher.end());
        }
        return str;
    }

    public boolean checkPrintSum(NodeList auftraegeList, Print print){
        int printSum = 0;
        print.setPrintNumber("0");
        for (int i = 0; i < auftraegeList.getLength(); i++) {
            Element auftrag = (Element) auftraegeList.item(i);

            String menge = auftrag.getElementsByTagName("MENGE").item(0).getTextContent();
            if(menge.contains(".")){
                menge = menge.replace(".", "");
            }
            int sum = Integer.parseInt(menge);
            if(sum!=auftrag.getElementsByTagName("Serial").getLength()){
                return false;
            }

            printSum+=sum;
        }

        print.setPrintNumber(String.valueOf(printSum));
        return true;
    }

    /**
     * 校验打印文件名称
     *
     * @param name 文件名称
     * @return 结果
     */
    public boolean checkPrintNameUnique(String name){
        PrintVO info = printMapper.checkPrintNameUnique(name);
        if (StringUtils.isNotNull(info))
        {
            return PrintConstants.NOT_UNIQUE;
        }
        return PrintConstants.UNIQUE;
    }

    /**
     * 修改用户信息
     *
     * @param printDTO 打印信息
     * @return 结果
     */
    public int updatePrint(PrintDTO printDTO){
        return printMapper.updatePrint(BeanConverUtil.conver(printDTO, Print.class));
    }

    /**
     * 查询一周激活的标签量
     *
     * @return 结果
     */
    public List<RecordVo> getWeekActivatedLabel(){
        return printMapper.selectWeekActivatedLabel();
    }

}