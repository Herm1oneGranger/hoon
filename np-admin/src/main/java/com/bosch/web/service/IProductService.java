package com.bosch.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.ProductExcel;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.vo.ProductVO;
import java.util.List;

/**
* @author ZPO2SGH
* @description 产品管理 业务层
* @createDate 2023-10-18 15:45:39
*/
public interface IProductService extends IService<Product> {

    /**
     * 查询产品信息集合
     *
     * @param productDTO 产品信息
     * @return 产品信息集合
     */
    public List<Product> selectProductList(ProductDTO productDTO);

    /**
     * 通过产品ID查询产品信息
     *
     * @param productId 产品ID
     * @return 产品信息ProductVO
     */
    public ProductVO selectProductVoById(Long productId);

    /**
     * 通过产品ID查询产品信息
     *
     * @param productId 产品ID
     * @return 产品信息Product
     */
    public Product selectProductById(Long productId);

    /**
     * 校验产品是否存在
     *
     * @param productDTO 产品信息
     * @return 结果
     */
    public boolean checkProductUnique(ProductDTO productDTO);

    /**
     * 新增产品信息
     *
     * @param productDTO 产品信息
     * @return 结果
     */
    public int insertProduct(ProductDTO productDTO);

    /**
     * 修改产品信息
     *
     * @param oldProduct 原产品信息
     * @param productDTO 产品信息
     * @return 结果
     */
    public int updateProduct(Product oldProduct, ProductDTO productDTO);

    /**
     * 查询所有被激活的标签
     *
     * @return 结果
     */
    public Long allActivatedLabel();

    /**
     * 获取产品图片
     *
     * @param imgPath 产品图片地址
     * @return 文件流
     */
    public String getProductPicture(String imgPath);

    public int insertProduct(List<ProductExcel> list);
}