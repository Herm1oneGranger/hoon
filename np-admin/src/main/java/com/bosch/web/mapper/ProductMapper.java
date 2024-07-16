package com.bosch.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosch.web.domain.Product;
import com.bosch.web.domain.dto.ProductDTO;
import com.bosch.web.domain.vo.ProductVO;
import java.util.List;

/**
* @author ZPO2SGH
* @description 针对表【p_product】的数据库操作Mapper
* @createDate 2023-10-18 15:45:39
* @Entity com.bosch.web.domain.Product
*/
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 查询产品列表
     *
     * @param productDTO 产品
     * @return 产品集合
     */
    public List<Product> selectProductList(ProductDTO productDTO);

    public List<Product> selectProductListNoMnr(ProductDTO productDTO);
    public List<Product> selectProductListData(ProductDTO productDTO);


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
     * @param productDTO 产品
     * @return 结果
     */
    public Product checkProductUnique(ProductDTO productDTO);

    /**
     * 新增产品
     *
     * @param product 产品信息
     * @return 结果
     */
    int insertProduct(Product product);

    int insertProducts(List<Product> products);
    /**
     * 修改产品信息
     *
     * @param product 产品信息
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 查询所有在用的产品
     *
     * @return 结果
     */
    public Long countActivatedProduct();
}