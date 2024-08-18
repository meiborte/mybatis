# mybatis
## mybatis快速入门
#maven配置文件
```shell
  <dependencies>
<!--    mybatis依赖-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.14</version>
    </dependency>
<!--    mysql驱动-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.33</version>
    </dependency>
<!--    junit 单元测试-->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    <!-- 添加slf4j日志api -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.20</version>
    </dependency>
    <!-- 添加logback-classic依赖 -->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.3</version>
    </dependency>
    <!-- 添加logback-core依赖 -->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-core</artifactId>
      <version>1.2.3</version>
    </dependency>
  </dependencies>
```
#mybatis-config.xml配置
```shell
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <typeAliases>
        <package name="com.meiborte.pojo"/>
    </typeAliases>
    
    <!--
    environments：配置数据库连接环境信息。可以配置多个environment，通过default属性切换不同的environment
    -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--数据库连接信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://47.92.39.131/db01"/>
                <property name="username" value="root"/>
                <property name="password" value="******"/>
            </dataSource>
        </environment>

        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--数据库连接信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://47.92.39.131/db01"/>
                <property name="username" value="root"/>
                <property name="password" value="*****"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--加载sql映射文件-->
        <mapper resource="UserMapper.xml"/>

        <!--Mapper代理方式-->
<!--        <package name="com.meiborte.mapper"/>-->

    </mappers>
</configuration>
```
#sql配置UserMapper.xml
```shell
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--        namespace:名称空间          -->

<mapper namespace="test">
    <select id="selectAll" resultType="com.meiborte.pojo.User">
        select * from db01;
    </select>
</mapper>
```
#日志配置logback.xml
```shell
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--
        CONSOLE ：表示当前的日志信息是可以输出到控制台的。
    -->
    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%level]  %cyan([%thread]) %boldGreen(%logger{15}) - %msg %n</pattern>
        </encoder>
    </appender>

    <logger name="com.itheima" level="DEBUG" additivity="false">
        <appender-ref ref="Console"/>
    </logger>


    <!--

      level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF
     ， 默认debug
      <root>可以包含零个或多个<appender-ref>元素，标识这个输出位置将会被本日志级别控制。
      -->
    <root level="DEBUG">
        <appender-ref ref="Console"/>
    </root>
</configuration>
```
#启动类
```shell
package com.meiborte;

import com.meiborte.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MybatisDemo {
    public static void main(String[] args)throws Exception {
        //1.加载配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.执行sql语句
        List<User> users =sqlSession.selectList("test.selectAll");
        System.out.println(users);
        //4.释放资源
        sqlSession.close();
    }
}
=============================================================================================================
用户类
package com.meiborte.pojo;


// alt + 鼠标左键 整列编辑
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

```
## mapper代理开发
在mapper包下添加一个接口UserMapper
```shell
package com.meiborte.mapper;

import com.meiborte.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();
}
//========================================================================
#启动类
package com.meiborte;

import com.meiborte.mapper.UserMapper;
import com.meiborte.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * mybatis 代理开发
 */
public class MybatisDemo2 {
    public static void main(String[] args)throws Exception {
        //1.加载配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3.执行sql语句
        //List<User> users =sqlSession.selectList("test.selectAll");
        //3.获取UserMapper接口的代理对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        //4.释放资源
        sqlSession.close();
    }
}

```
#配置文件
```shell
#mybatis-config.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <typeAliases>
        <package name="com.meiborte.pojo"/>
    </typeAliases>
    
    <!--
    environments：配置数据库连接环境信息。可以配置多个environment，通过default属性切换不同的environment
    -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--数据库连接信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://47.92.39.131/db01"/>
                <property name="username" value="root"/>
                <property name="password" value="***"/>
            </dataSource>
        </environment>
+
        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--数据库连接信息-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://47.92.39.131/db01"/>
                <property name="username" value="root"/>
                <property name="password" value="***"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!--加载sql映射文件-->
<!--        <mapper resource="com/meiborte/mapper/UserMapper.xml"/>-->

        <!--Mapper代理方式-->
        <package name="com.meiborte.mapper"/>

    </mappers>


</configuration>
```
#com.meiborte.mapper.usermapper.xml
```shell
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--        namespace:名称空间          -->

<mapper namespace="com.meiborte.mapper.UserMapper">
    <select id="selectAll" resultType="com.meiborte.pojo.User">
        select * from db01;
    </select>
</mapper>
```

### 映射
```shell
<!--
        id:唯一标识
        type:映射类型,支持别名
    -->
    <resultMap id="brandResultMap" type="brand">
        <!--
            id:完成主键字段的映射
                column:数据库字段
                property:实体类属性
            result:完成一般字段的映射
                column:数据库字段
                property:实体类属性
        -->
        <result column="brand_name" property="brandName"/>
        <result column="company_name" property="companyName"/>
    </resultMap>

    <select id="selectAll" resultMap="brandResultMap">
        select * from tb_brand;
    </select>
```
 ## 实现增删改查操作
### 文件路径
![文件路径](/static/images/目录结构2.png)
### 整体代码
#BrandMapper接口类
```shell
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
     *
     * @return
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
```
#Brand
```shell
package com.meiborte.pojo;

/**
 * 品牌
 *
 * alt + 鼠标左键：整列编辑
 *
 * 在实体类中，基本数据类型建议使用其对应的包装类型
 */

public class Brand {
    // id 主键
    private Integer id;
    // 品牌名称
    private String brandName;
    // 企业名称
    private String companyName;
    // 排序字段
    private Integer ordered;
    // 描述信息
    private String description;
    // 状态：0：禁用  1：启用
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
```
#BrandMapper.xml配置
```shell
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--        namespace:名称空间          -->

<mapper namespace="com.meiborte.mapper.BrandMapper">

    <!--
        id:唯一标识
        type:映射类型,支持别名
    -->
    <resultMap id="brandResultMap" type="brand">
        <!--
            id:完成主键字段的映射
                column:数据库字段
                property:实体类属性
            result:完成一般字段的映射
                column:数据库字段
                property:实体类属性
        -->
        <result column="brand_name" property="brandName"/>
        <result column="company_name" property="companyName"/>
    </resultMap>

    <select id="selectAll" resultMap="brandResultMap">
        select * from tb_brand;
    </select>

<!--
    *参数占位符:
    1. #{}:会将其替换成?,为了防止SQL注入
    2. ${}:会产生SQL注入问题,不建议使用
    3.使用时机:
        1.当参数传递的时候,使用#{}
        2.当查询条件是不固定的时候,使用${},会出现SQL注入问题
    *参数类型:paramenterType:可以省略
    *特殊字符处理
        1.转义字符: < = &lt;
        2.CDATA区:<![CDATA[ ]]>
-->
<!--    <select id="selectById" resultMap="brandResultMap">-->
<!--        select * from tb_brand where id = #{id};-->
<!--    </select>-->

    <select id="selectById" resultMap="brandResultMap">
        select *
        from tb_brand where id
        <![CDATA[
            <
        ]]>
        #{id}
    </select>


    <!--
        条件查询
    -->
<!--    <select id="selectByCondition" resultMap="brandResultMap">-->
<!--            select *-->
<!--            from tb_brand-->
<!--            where status = #{status}-->
<!--              and company_name like #{companyName}-->
<!--              and brand_name like #{brandName}-->
<!--    </select>-->


    <!--
            动态条件查询
               * if: 条件判断
                  * test: 逻辑表达式
               * 问题:
                   * 恒等式
                   * <where> 替换 where 关键字
    -->
    <select id="selectByCondition" resultMap="brandResultMap">
        select *
        from tb_brand
--         where 1=1
        <where>
          <if test="status != null">
                status = #{status}
          </if>
          <if test="companyName != null and companyName != '' ">
                and company_name like #{companyName}
          </if>
          <if test="brandName != null and brandName != '' ">
                and brand_name like #{brandName}
          </if>
        </where>
    </select>


<!--    <select id="selectByConditionSingle" resultMap="brandResultMap">-->
<!--        select *-->
<!--        from tb_brand-->
<!--        where-->
<!--            <choose>&lt;!&ndash;相当于switch&ndash;&gt;-->
<!--                <when test="status != null">&lt;!&ndash;相当于case&ndash;&gt;-->
<!--                    status = #{status}-->
<!--                </when>-->
<!--                <when test="companyName != null and companyName != ''">&lt;!&ndash;相当于case&ndash;&gt;-->
<!--                    company_name like #{companyName}-->
<!--                </when>-->
<!--                <when test="brandName != null and brandName != ''">&lt;!&ndash;相当于case&ndash;&gt;-->
<!--                    brand_name like #{brandName}-->
<!--                </when>-->
<!--                <otherwise>-->
<!--                    1=1-->
<!--                </otherwise>-->
<!--            </choose>-->
<!--    </select>-->
    <select id="selectByConditionSingle" resultMap="brandResultMap">
        select *
        from tb_brand
        <where>
           <choose><!--相当于switch-->
               <when test="status != null"><!--相当于case-->
                    status = #{status}
                </when>
               <when test="companyName != null and companyName != ''"><!--相当于case-->
                     company_name like #{companyName}
               </when>
               <when test="brandName != null and brandName != ''"><!--相当于case-->
                     brand_name like #{brandName}
               </when>
            </choose>
        </where>
    </select>

    <insert id="add" useGeneratedKeys="true" keyProperty="id">
        insert into tb_brand(brand_name,company_name,ordered,description, status)
        values(#{brandName}, #{companyName}, #{ordered}, #{description}, #{status})
    </insert>

    <update id="update">
        update tb_brand
        <set>
            <if test="brandName != null and brandName != ''">
                brand_name = #{brandName},
            </if>
            <if test="companyName != null and companyName != ''">
                company_name = #{companyName},
            </if>
            <if test="ordered != null">
                ordered = #{ordered},
            </if>
            <if test="description != null and description != ''">
                description = #{description},
            </if>
            <if test="status != null">
                status = #{status}
            </if>
        </set>
        where id = #{id};
    </update>


    <delete id="deleteById">
        delete from tb_brand where id = #{id};
    </delete>

    <!--
        mybatis会将数组参数,封装为一个Map集合.
            * 默认: array = 数组
            * 使用@Param注解改变map集合的默认key的名称
    -->
    <delete id="deleteByIds">
        delete from tb_brand where id
        in
            <foreach collection="ids" item="id" separator="," open="(" close=")">
                #{id}
            </foreach>
        ;
    </delete>
</mapper>
```
#test测试
```shell
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
```
### 查询
#查询
```shell
#BrandMapper页面
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
//=======================================================
#BrandMapper.xml
<!--
        id:唯一标识
        type:映射类型,支持别名
    -->
    <resultMap id="brandResultMap" type="brand">
        <!--
            id:完成主键字段的映射
                column:数据库字段
                property:实体类属性
            result:完成一般字段的映射
                column:数据库字段
                property:实体类属性
        -->
        <result column="brand_name" property="brandName"/>
        <result column="company_name" property="companyName"/>
    </resultMap>

    <select id="selectAll" resultMap="brandResultMap">
        select * from tb_brand;
    </select>

<!--
    *参数占位符:
    1. #{}:会将其替换成?,为了防止SQL注入
    2. ${}:会产生SQL注入问题,不建议使用
    3.使用时机:
        1.当参数传递的时候,使用#{}
        2.当查询条件是不固定的时候,使用${},会出现SQL注入问题
    *参数类型:paramenterType:可以省略
    *特殊字符处理
        1.转义字符: < = &lt;
        2.CDATA区:<![CDATA[ ]]>
-->
<!--    <select id="selectById" resultMap="brandResultMap">-->
<!--        select * from tb_brand where id = #{id};-->
<!--    </select>-->

    <select id="selectById" resultMap="brandResultMap">
        select *
        from tb_brand where id
        <![CDATA[
            <
        ]]>
        #{id}
    </select>


    <!--
        条件查询
    -->
<!--    <select id="selectByCondition" resultMap="brandResultMap">-->
<!--            select *-->
<!--            from tb_brand-->
<!--            where status = #{status}-->
<!--              and company_name like #{companyName}-->
<!--              and brand_name like #{brandName}-->
<!--    </select>-->


    <!--
            动态条件查询
               * if: 条件判断
                  * test: 逻辑表达式
               * 问题:
                   * 恒等式
                   * <where> 替换 where 关键字
    -->
    <select id="selectByCondition" resultMap="brandResultMap">
        select *
        from tb_brand
--         where 1=1
<where>
<if test="status != null">
status = #{status}
</if>
<if test="companyName != null and companyName != '' ">
and company_name like #{companyName}
</if>
<if test="brandName != null and brandName != '' ">
and brand_name like #{brandName}
</if>
</where>
</select>


<!--    <select id="selectByConditionSingle" resultMap="brandResultMap">-->
<!--        select *-->
<!--        from tb_brand-->
<!--        where-->
<!--            <choose>&lt;!&ndash;相当于switch&ndash;&gt;-->
<!--                <when test="status != null">&lt;!&ndash;相当于case&ndash;&gt;-->
<!--                    status = #{status}-->
<!--                </when>-->
<!--                <when test="companyName != null and companyName != ''">&lt;!&ndash;相当于case&ndash;&gt;-->
<!--                    company_name like #{companyName}-->
<!--                </when>-->
<!--                <when test="brandName != null and brandName != ''">&lt;!&ndash;相当于case&ndash;&gt;-->
<!--                    brand_name like #{brandName}-->
<!--                </when>-->
<!--                <otherwise>-->
<!--                    1=1-->
<!--                </otherwise>-->
<!--            </choose>-->
<!--    </select>-->
    <select id="selectByConditionSingle" resultMap="brandResultMap">
        select *
        from tb_brand
        <where>
           <choose><!--相当于switch-->
               <when test="status != null"><!--相当于case-->
                    status = #{status}
                </when>
               <when test="companyName != null and companyName != ''"><!--相当于case-->
                     company_name like #{companyName}
               </when>
               <when test="brandName != null and brandName != ''"><!--相当于case-->
                     brand_name like #{brandName}
               </when>
            </choose>
        </where>
    </select>
//====================================================================================
#MyBatisTest
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
```
### 添加
```shell
#BrandMapper
 /**
     * 添加品牌
     */
    void add(Brand brand);
//=========================================================================================================
#BrandMapper.xml
 <insert id="add" useGeneratedKeys="true" keyProperty="id">
        insert into tb_brand(brand_name,company_name,ordered,description, status)
        values(#{brandName}, #{companyName}, #{ordered}, #{description}, #{status})
</insert>
//===================================================================================================
#MyBatisTest
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
```
### 修改
```shell
#BrandMapper
/**
     * 修改品牌
     *
     * @return
     */
    int update(Brand brand);
//=============================================================================
#BrandMapper.xml
<update id="update">
        update tb_brand
        <set>
            <if test="brandName != null and brandName != ''">
                brand_name = #{brandName},
            </if>
            <if test="companyName != null and companyName != ''">
                company_name = #{companyName},
            </if>
            <if test="ordered != null">
                ordered = #{ordered},
            </if>
            <if test="description != null and description != ''">
                description = #{description},
            </if>
            <if test="status != null">
                status = #{status}
            </if>
        </set>
        where id = #{id};
</update>
//============================================================================================
#MyBatisTest
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
```
### 删除
```shell
#BrandMapper
/**
     * 根据id删除品牌
     */
    void deleteById(Integer id);

    /**
     * 批量删除品牌
     */
    void deleteByIds(@Param("ids") Integer[] ids);
}
//===========================================================================================================================================
#BrandMapper.xml
<delete id="deleteById">
        delete from tb_brand where id = #{id};
    </delete>

    <!--
        mybatis会将数组参数,封装为一个Map集合.
            * 默认: array = 数组
            * 使用@Param注解改变map集合的默认key的名称
    -->
    <delete id="deleteByIds">
        delete from tb_brand where id
        in
            <foreach collection="ids" item="id" separator="," open="(" close=")">
                #{id}
            </foreach>
        ;
    </delete>
//==========================================================================================================================
#MyBatisTest
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
```