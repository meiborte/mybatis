package com.meiborte.test;


import com.meiborte.mapper.BrandMapper;
import com.meiborte.pojo.Brand;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

    @Test
    public void testSelectAll()throws Exception{
        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        List<Brand> brands = brandMapper.selectAll();
        System.out.println(brands);
        //5.释放资源
        sqlSession.close();
    }
//============================================================================================================
    @Test
    public void testselectById()throws Exception{
        //接受参数
        int id = 1;

        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        Brand brand = brandMapper.selectById(id);
        System.out.println(brand);

        //5.释放资源
        sqlSession.close();
    }

//============================================================================================================

    @Test
    public void testselectByCondition()throws Exception{
        //接受参数
        int status = 1;
        String companyName = "华为";
        String brandName = "华为";

        //处理参数
        companyName= "%"+companyName+"%";
        brandName= "%"+brandName+"%";

        //封装对象
//        Brand brand = new Brand();
//        brand.setStatus(status);
//        brand.setCompanyName(companyName);
//        brand.setBrandName(brandName);

        Map map = new HashMap();
        map.put("status", status);
        map.put("companyName", companyName);
        map.put("brandName", brandName);

        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        //List<Brand> brands = brandMapper.selectByCondition(status,companyName,brandName);
        //List<Brand> brands = brandMapper.selectByCondition(brand);
        List<Brand> brands = brandMapper.selectByCondition(map);
        System.out.println(brands);

        //5.释放资源
        sqlSession.close();
    }

//============================================================================================================
@Test
public void testselectByConditionSingle()throws Exception{
    //接受参数
    int status = 1;
    String companyName = "华为";
    String brandName = "华为";

    //处理参数
    companyName= "%"+companyName+"%";
    brandName= "%"+brandName+"%";

    //封装对象
        Brand brand = new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);


    //1.获取SessionFactory
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    //2.获取SqlSession对象
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //3.获取Mapper接口的代理对象
    BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

    //4.执行Mapper的方法
    //List<Brand> brands = brandMapper.selectByCondition(status,companyName,brandName);
    //List<Brand> brands = brandMapper.selectByCondition(brand);

    List<Brand> brands = brandMapper.selectByConditionSingle(brand);
    System.out.println(brands);

    //5.释放资源
    sqlSession.close();
    }

    //============================================================================================================

    @Test
    public void testAdd()throws Exception{
        //接受参数
        Integer status = 1;
        String companyName = "波导手机";
        String brandName = "波导";
        String description = "手机中的战斗机";
        Integer ordered = 100;

        //封装对象
        Brand brand = new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);
        brand.setDescription(description);
        brand.setOrdered(ordered);


        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        brandMapper.add(brand);

        //5.提交事务
        //sqlSession.commit();

        //5.释放资源
        sqlSession.close();
    }

    //============================================================================================================

    @Test
    public void testAdd2()throws Exception{
        //接受参数
        Integer status = 1;
        String companyName = "波导手机";
        String brandName = "波导";
        String description = "手机中的战斗机";
        Integer ordered = 100;

        //封装对象
        Brand brand = new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);
        brand.setDescription(description);
        brand.setOrdered(ordered);


        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        brandMapper.add(brand);
        Integer id = brand.getId();
        System.out.println(id);

        //5.提交事务
        sqlSession.commit();

        //5.释放资源
        sqlSession.close();
    }


//===================================================================================================================
@Test
public void testUpdate()throws Exception{
    //接受参数
    Integer status = 1;
    String companyName = "鸭梨手机";
    String brandName = "波导";
    String description = "波导手机,手机中的战斗机";
    Integer ordered = 200;
    Integer id = 6;

    //封装对象
    Brand brand = new Brand();
//    brand.setStatus(status);
    brand.setCompanyName(companyName);
//    brand.setBrandName(brandName);
//    brand.setDescription(description);
//    brand.setOrdered(ordered);
    brand.setId(id);

    //1.获取SessionFactory
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    //2.获取SqlSession对象
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //SqlSession sqlSession = sqlSessionFactory.openSession(true);

    //3.获取Mapper接口的代理对象
    BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

    //4.执行Mapper的方法
    int count = brandMapper.update(brand);
    System.out.println(count);

    //5.提交事务
    sqlSession.commit();

    //5.释放资源
    sqlSession.close();
}


    //===================================================================================================================
    @Test
    public void testDeleteById()throws Exception{
        //接受参数
        Integer id = 6;

        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        brandMapper.deleteById(id);
        //5.提交事务
        sqlSession.commit();

        //5.释放资源
        sqlSession.close();
    }


    //===================================================================================================================
    @Test
    public void testDeleteByIds()throws Exception{
        //接受参数
        Integer[] ids = {4,5,8};

        //1.获取SessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //3.获取Mapper接口的代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        //4.执行Mapper的方法
        brandMapper.deleteByIds(ids);
        //5.提交事务
        sqlSession.commit();

        //5.释放资源
        sqlSession.close();
    }
}
