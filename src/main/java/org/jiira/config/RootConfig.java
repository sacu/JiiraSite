package org.jiira.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
// 定义Spring 扫描的包
@ComponentScan(value = "org.jiira.*", includeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = { Service.class }) })
// 使用事务驱动管理器
@EnableTransactionManagement
// 实现接口TransactionManagementConfigurer，这样可以配置注解驱动事务
public class RootConfig implements TransactionManagementConfigurer {
	
	private DataSource dataSource = null;

	/**
	 * 配置数据库.
	 * 
	 * @return 数据连接池
	 */
	@Bean(name = "dataSource")
	public DataSource initDataSource() {
		if (dataSource != null) {
			return dataSource;
		}
		try {
			Properties prop = getProperties("jdbc.properties");
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	@Bean(name = "redisTemplate")
	@DependsOn("dataSource")
	public RedisTemplate<String, RedisTemplate<String, String>> initRedisTemplate() {
		Properties prop = getProperties("redis.properties");
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 最大空闲数
		poolConfig.setMaxIdle(Integer.valueOf(prop.getProperty("maxIdle")));
		// 最大连接数
		poolConfig.setMaxTotal(Integer.valueOf(prop.getProperty("maxTotal")));
		// 最大等待毫秒数
		poolConfig.setMaxWaitMillis(Integer.valueOf(prop.getProperty("maxWaitMillis")));
		// 创建Jedis链接工厂
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
		connectionFactory.setHostName(prop.getProperty("hostName"));
		connectionFactory.setPort(Integer.valueOf(prop.getProperty("port")));
		connectionFactory.setPassword(prop.getProperty("passWord"));
		// 调用后初始化方法，没有它将抛出异常
		connectionFactory.afterPropertiesSet();
		// 自定Redis序列化器
//		RedisSerializer<?> jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
		// 定义RedisTemplate，并设置连接工程[修改为：工厂]
		RedisTemplate<String, RedisTemplate<String, String>> redisTemplate = new RedisTemplate<String, RedisTemplate<String, String>>();
		redisTemplate.setConnectionFactory(connectionFactory);
		// 设置序列化器
		redisTemplate.setDefaultSerializer(stringRedisSerializer);
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		return redisTemplate;
	}
	/***
	 * 配置SqlSessionFactoryBean
	 * 
	 * @return SqlSessionFactoryBean
	 */
	@Bean(name = "sqlSessionFactory")
	@DependsOn("redisTemplate")
	public SqlSessionFactoryBean initSqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(initDataSource());
		// 配置MyBatis配置文件
		Resource resource = new ClassPathResource("mybatis-config.xml");
		sqlSessionFactory.setConfigLocation(resource);
		return sqlSessionFactory;
	}
	/***
	 * 通过自动扫描，发现MyBatis Mapper接口 总是出现加载过早的提示，使用static能解决一部分问题
	 * 
	 * @return Mapper扫描器
	 */
	@Bean
	@DependsOn("sqlSessionFactory")
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		msc.setBasePackage("org.jiira.*");
		msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
		msc.setAnnotationClass(Repository.class);
		msc.setProcessPropertyPlaceHolders(true);
		return msc;
	}
	/**
	 * 实现接口方法，注册注解事务，当@Transactional 使用的时候产生数据库事务
	 */
	@Override
	@Bean(name = "annotationDrivenTransactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(initDataSource());
		return transactionManager;
	}

	/**
	 * 我就想说！死吧@PropertySource
	 * 
	 * @return
	 */
	private static Properties getProperties(String path) {
		InputStream in;
		Properties props = null;
		try {
			in = Resources.getResourceAsStream(path);
			props = new Properties();
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return props;
	}
}