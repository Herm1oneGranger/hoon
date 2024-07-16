package com.bosch.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bosch.common.annotation.DataScope;
import com.bosch.common.constant.ProductConstants;
import com.bosch.common.exception.ServiceException;
import com.bosch.common.utils.BeanConverUtil;
import com.bosch.common.utils.MessageUtils;
import com.bosch.common.utils.StringUtils;
import com.bosch.common.utils.file.ImageUtils;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.ProductExcel;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.vo.ProductVO;
import com.bosch.web.mapper.ProductMapper;
import com.bosch.web.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.bosch.common.utils.SecurityUtils.getUsername;

/**
* @author ZPO2SGH
* @description 针对表【p_product】的数据库操作Service实现
* @createDate 2023-10-18 15:45:39
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements IProductService {

    @Autowired
    ProductMapper productMapper;

    /**
     * 查询产品信息集合
     *
     * @param productDTO 产品信息
     * @return 产品信息集合
     */
    @DataScope(deptAlias = "d", userAlias = "s")
    public List<Product> selectProductList(ProductDTO productDTO){
//        Map<String, Object> params=new HashMap<>();
//        params.put("dataScope","");
//        productDTO.setParams(params);
        return productMapper.selectProductListData(productDTO);
    }

    /**
     * 通过产品ID查询产品信息
     *
     * @param productId 产品ID
     * @return 产品信息ProductVO
     */
    public ProductVO selectProductVoById(Long productId){
        return productMapper.selectProductVoById(productId);
    }

    /**
     * 通过产品ID查询产品信息
     *
     * @param productId 产品ID
     * @return 产品信息Product
     */
    public Product selectProductById(Long productId){
        return productMapper.selectProductById(productId);
    }

    /**
     * 校验产品是否存在
     *
     * @param productDTO 产品信息
     * @return 结果
     */
    public boolean checkProductUnique(ProductDTO productDTO) {
        Product info = productMapper.checkProductUnique(productDTO);
        if (StringUtils.isNotNull(info))
        {
            return ProductConstants.NOT_UNIQUE;
        }
        return ProductConstants.UNIQUE;
    }

    /**
     * 新增产品信息
     *
     * @param productDTO 产品信息
     * @return 结果
     */
    public int insertProduct(ProductDTO productDTO) {
        return productMapper.insertProduct(BeanConverUtil.conver(productDTO, Product.class));
    }

    /**
     * 修改产品信息
     *
     * @param oldProduct 原产品信息
     * @param productDTO 产品信息
     * @return 结果
     */
    public int updateProduct(Product oldProduct, ProductDTO productDTO){
        String oldVerifyStatus = oldProduct.getVerifyStatus();
        String newVerifyStatus = productDTO.getVerifyStatus();
        String oldStatus = oldProduct.getStatus();
        String newStatus = productDTO.getStatus();

        if ((StringUtils.isNotEmpty(newVerifyStatus) && !newVerifyStatus.equals(oldVerifyStatus)) ||
                (StringUtils.isNotEmpty(newStatus) && !newStatus.equals(oldStatus))) {
            productDTO.setUpdateTime(new Date());
        }

        return productMapper.updateProduct(BeanConverUtil.conver(productDTO, Product.class));
    }

    /**
     * 查询所有在用的产品
     *
     * @return 结果
     */
    public Long allActivatedLabel(){
        return productMapper.countActivatedProduct();
    }

    /**
     * 获取产品图片
     *
     * @param imgPath 产品图片地址
     * @return 文件流
     */
    public String getProductPicture(String imgPath){
        byte[] image = ImageUtils.getImage(imgPath);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(image);
    }

    @Override
    public int insertProduct(List<ProductExcel> list) {

        List<Product> products=new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ProductExcel excel = list.get(i);
            int number ;
            int sn = 0;
            try {
                number = Integer.parseInt(excel.getNumber());
            }catch (Exception e){
                throw new ServiceException("excel中第"+String.valueOf(i+2)+"行数量有误");
            }
            products.addAll(IntStream.range(sn, sn + number)
                    .mapToObj(x ->  {
                        Product product = new Product();
                        product.setName(excel.getName());
                        product.setMnr(excel.getMnr());
                        product.setFd(excel.getFd());
                        product.setSn(String.valueOf(x+1));
                        product.setCreateBy(getUsername());
                        return product;
                    })
                    .collect(Collectors.toList()));

        }

        int i = productMapper.insertProducts(products);
        return i;
    }

}