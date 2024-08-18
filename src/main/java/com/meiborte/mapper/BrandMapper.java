package com.meiborte.mapper;


import com.meiborte.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BrandMapper {
    /**
     * 查询所有品牌
     * @return
     */
    public List<Brand> selectAll();

    /**
     * 查询id为指定id的商品
     * @param id
     * @return
     */
    Brand selectById(Integer id);

    /**
     * 条件查询
     *   *参数接收
     *       1.散装参数:如果方法中有多个参数,需要使用@Param注解指定参数名("SQL参数占位符名称")
     *       2.对象参数:对象的属性名称要和参数占位符名称一致
     *       3.map集合参数
     * @param status
     * @param companyName
     * @param brandName
     * @return
     */

//    List<Brand> selectByCondition(@Param("status")int status,@Param("companyName")String companyName,@Param("brandName")String brandName);
//      List<Brand> selectByCondition(Brand brand);
        List<Brand> selectByCondition(Map map);


    /**
     * 单条件动态查询
     * @param brand
     * @return
     */
    List<Brand> selectByConditionSingle(Brand brand);


    /**
     * 添加品牌
     */
    void add(Brand brand);

    /**
     * 修改品牌
     */
    int update(Brand brand);

    /**
     * 根据id删除品牌
     */
    void deleteById(Integer id);

    /**
     * 批量删除品牌
     */
    void deleteByIds(@Param("ids") Integer[] ids);
}

